package com.tabwu.SAP.sale.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbitmq.client.Channel;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.common.constant.RabbitStaticConstant;
import com.tabwu.SAP.common.entity.MqMsg;
import com.tabwu.SAP.sale.entity.Sale;
import com.tabwu.SAP.sale.entity.SaleItem;
import com.tabwu.SAP.sale.entity.to.WareStockTo;
import com.tabwu.SAP.sale.entity.vo.SaleQueryVo;
import com.tabwu.SAP.sale.entity.vo.SaleVo;
import com.tabwu.SAP.sale.feign.MaterialStockFeign;
import com.tabwu.SAP.sale.mapper.SaleMapper;
import com.tabwu.SAP.sale.service.ISaleItemService;
import com.tabwu.SAP.sale.service.ISaleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author tabwu
 * @since 2022-07-04
 */
@Service
public class SaleServiceImpl extends ServiceImpl<SaleMapper, Sale> implements ISaleService {

    @Autowired
    private ISaleItemService saleItemService;
    @Autowired
    private MaterialStockFeign materialStockFeign;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    // TODO 需要分布式事务最终保证一致性
    public boolean addSale(SaleVo saleVo) {

        List<SaleItem> saleItems = saleVo.getSaleItems();

        if (saleVo.getType() == 1) {
            // 1、根据物料mcode和数量查询判断库存是否足够
            boolean hasStock = checkStockByMcode(saleItems);
            if (!hasStock) {
                throw new CostomException(20004,"改订单中的物料存在库存不足的情况，不能创建订单，请检查物料库存情况！！！");
            }
        }

        // 2、根据saleVo生成销售订单，幂等性添加订单，此处幂等性根据数据库字段code唯一索引检验
        Sale sale = new Sale();
        createSaleBills(saleVo, sale, saleItems);

        if (saleVo.getType() == 1) {
            // 3、销售订单添加成功后远程扣减物料库存
            reduceWareStockRemote(saleItems);

            // 4、发送消息给MQ，财务服务根据销售订单自动生成付款订单
            sendMsgNotifyGeneratePayBillsBySaleId(sale);
        }

        return true;
    }

    private void sendMsgNotifyGeneratePayBillsBySaleId(Sale sale) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(sale.getCode());
        MqMsg mqMsg = new MqMsg();
        mqMsg.setItemId(sale.getId());
        mqMsg.setCode(sale.getCode());
        mqMsg.setTax(sale.getTax());
        mqMsg.setAllTax(sale.getAllTax());
        mqMsg.setAllPrice(sale.getAllPrice());
        mqMsg.setTotalPrice(sale.getTotalPrice());
        rabbitTemplate.convertAndSend(RabbitStaticConstant.innosen_topic,"sale.receipt",mqMsg,correlationData);
    }


    // 5、监听MQ消息队列，查询付款状态, 返回销售订单的code码与付款状态
    @RabbitListener(queues = RabbitStaticConstant.saleOrderReceiptQueue)
    public void listenerPayStatus(MqMsg mqMsg, Channel channel, Message message) throws IOException {
        try {
            if (mqMsg.getPayStatus()) {
                // 6、付款成功后，根据销售订单自动生成物料出库单，将销售、出库单状态改为待出库
                Sale sale = baseMapper.selectById(mqMsg.getItemId());
                List<SaleItem> saleItems = saleItemService.list(new QueryWrapper<SaleItem>().eq("pcode", sale.getCode()));
                // 6.1 付款成功，修改销售订单状态为已付款--1
                baseMapper.update(null,new UpdateWrapper<Sale>().eq("id",mqMsg.getItemId()).set("status",1));
                // 6.2 根据销售订单自动生成物料出库单，状态为待出货--2，并关联销售订单的code码
                autoGenerateMaterialOutboundBills(sale, saleItems);
            } else {
                // 7、付款失败，取消订单，回退库存
                deleteSaleById(mqMsg.getItemId());
            }
            /**
             *deliveryTag:该消息的index
             * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (CostomException e) {
            /**
             * deliveryTag:该消息的index
             * requeue：被拒绝的是否重新入队列
             * channel.basicNack 与 channel.basicReject 的区别在于basicNack可以拒绝多条消息，
             * 而basicReject一次只能拒绝一条消息
             */
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
            throw e;
        }
    }

    private void autoGenerateMaterialOutboundBills(Sale sale, List<SaleItem> saleItems) {
        Sale saleOutput = new Sale();
        BeanUtils.copyProperties(sale,saleOutput);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String code = "YLS-" + format.format(new Date()) + "-" + new Random().nextInt(100);
        saleOutput.setId("");
        saleOutput.setType(2);
        saleOutput.setStatus(2);
        saleOutput.setCode(code);
        saleOutput.setRelationItem(sale.getCode());
        int insert = baseMapper.insert(saleOutput);
        if (insert <= 0) {
            throw new CostomException(20003,"id为：" + sale.getId() + "的销售订单自动生成物料出库单失败，请手动添加物料出库单据");
        }
        for (SaleItem saleItem : saleItems) {
            saleItem.setId("");
            saleItem.setPcode(saleOutput.getCode());
        }
        saleItemService.saveBatch(saleItems);
    }

    private void reduceWareStockRemote(List<SaleItem> saleItems) {
        List<WareStockTo> wareStockTos  = new ArrayList<>();
        for (SaleItem saleItem : saleItems) {
            saleItem.setNumber(saleItem.getNumber() * -1);
            WareStockTo wareStockTo = new WareStockTo();
            BeanUtils.copyProperties(saleItem,wareStockTo);
            wareStockTos.add(wareStockTo);
        }
        R r = materialStockFeign.reduceWareStockByCondition(wareStockTos);
        if (r.getCode() == 20004) {
            throw new CostomException(20004,"扣减库存失败!!!");
        }
    }

    private void createSaleBills(SaleVo saleVo, Sale sale,List<SaleItem> saleItems) {
        BeanUtils.copyProperties(saleVo,sale);
        int insert = baseMapper.insert(sale);
        if (insert <= 0) {
            throw new CostomException(20004,"销售单据添加失败，或重复提交失败");
        }
        for (SaleItem saleItem : saleItems) {
            saleItem.setPcode(sale.getCode());
            saleItemService.save(saleItem);
        }
    }

    private boolean checkStockByMcode(List<SaleItem> saleItems) {
        List<WareStockTo> wareStockTos = new ArrayList<>();
        for (SaleItem saleItem : saleItems) {
            WareStockTo wareStockTo = new WareStockTo();
            BeanUtils.copyProperties(saleItem,wareStockTo);
            wareStockTos.add(wareStockTo);
        }
        R r = materialStockFeign.checkStockByMcode(wareStockTos);
        if (r.getCode() != 20000) return false;

        return true;
    }


    @Override
    public boolean updateSaleOrder(SaleVo saleVo) {
        // TODO 修改销售单信息,当物料数量也被修改时，需要同时修改仓库中扣减数量   考虑方向：uodo-log操作日志表，记录操作前后数据
        Sale sale = new Sale();
        BeanUtils.copyProperties(saleVo,sale);
        int update = baseMapper.updateById(sale);
        if (update > 0) {
            List<SaleItem> saleItems = saleVo.getSaleItems();
            boolean remove = saleItemService.remove(new UpdateWrapper<SaleItem>().eq("pcode", sale.getCode()));
            if (remove) {
                for (SaleItem saleItem : saleItems) {
                    saleItem.setId("");
                    saleItem.setPcode(sale.getCode());
                }
                saleItemService.saveBatch(saleItems);
                return true;
            }
        }

        return false;
    }

    @Override
    @Transactional
    public Boolean deleteSaleById(String id) {
        Sale sale = baseMapper.selectById(id);
        if (sale == null) {
            throw new CostomException(20004,"id为：" + id + "的单据不存在!!!");
        }
        int delete = baseMapper.deleteById(id);
        if (delete > 0) {
            List<SaleItem> saleItems = saleItemService.list(new QueryWrapper<SaleItem>().eq("pcode", sale.getCode()));
            saleItemService.removeByIds(saleItems);
            // 订单删除成功后需要回退库存
            if (!returnWareStockByCondition(saleItems)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean comfirmedReturnSuccess(String id) {
        Sale sale = baseMapper.selectById(id);
        if (sale == null) {
            throw new CostomException(20004,"id为：" + id + "的单据不存在!!!");
        }
        int update = baseMapper.update(null, new UpdateWrapper<Sale>().eq("id", id).set("status", 3));
        if (update > 0) {
            List<SaleItem> saleItems = saleItemService.list(new QueryWrapper<SaleItem>().eq("pcode", sale.getCode()));
            // 回退库存
            if (!returnWareStockByCondition(saleItems)) {
                return false;
            }

            //同时发送消息通知财务服务生成退款订单完成退款流程
            sendMsgNotifyGenerateRefundBills(sale);
            return true;
        }
        return false;
    }

    private void sendMsgNotifyGenerateRefundBills(Sale sale) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(sale.getCode());
        MqMsg mqMsg = new MqMsg();
        mqMsg.setItemId(sale.getId());
        mqMsg.setCode(sale.getCode());
        mqMsg.setTax(sale.getTax());
        mqMsg.setAllTax(sale.getAllTax());
        mqMsg.setAllPrice(sale.getAllPrice());
        mqMsg.setTotalPrice(sale.getTotalPrice());
        rabbitTemplate.convertAndSend(RabbitStaticConstant.innosen_topic, "sale.refund", mqMsg, correlationData);
    }

    @Override
    public HashMap<String, Object> excelExportSaleBillsInformation(String id) {
        // TODO 根据id 查询数据渲染excel模板
        return null;
    }

    private Boolean returnWareStockByCondition(List<SaleItem> saleItems) {
        List<WareStockTo> wareStockTos = new ArrayList<>();
        for (SaleItem saleItem : saleItems) {
            WareStockTo wareStockTo = new WareStockTo();
            BeanUtils.copyProperties(saleItem,wareStockTo);
            wareStockTos.add(wareStockTo);
        }
        R r = materialStockFeign.reduceWareStockByCondition(wareStockTos);
        if (r.getCode() != 20000) {
            return false;
        }
        return true;
    }


    @Override
    public HashMap<String,Object> pageList(SaleQueryVo saleQueryVo) {
        Page<Sale> page = new Page<>(saleQueryVo.getCurrent(), saleQueryVo.getSize());
        QueryWrapper<Sale> wrapper = new QueryWrapper<>();

        wrapper.eq("type",saleQueryVo.getType());
        if (!StringUtils.isEmpty(saleQueryVo.getCode())) {
            wrapper.eq("code",saleQueryVo.getCode());
        }
        if (!StringUtils.isEmpty(saleQueryVo.getTxId())) {
            wrapper.eq("tx_id",saleQueryVo.getTxId());
        }
        if (saleQueryVo.getStatus() != null) {
            wrapper.eq("status",saleQueryVo.getStatus());
        }
        if (!StringUtils.isEmpty(saleQueryVo.getCostomer())) {
            wrapper.eq("costomer",saleQueryVo.getCostomer());
        }
        if (!StringUtils.isEmpty(saleQueryVo.getSaler())) {
            wrapper.like("saler",saleQueryVo.getSaler());
        }
        baseMapper.selectPage(page,wrapper);
        ArrayList<SaleVo> saleVos = new ArrayList<>();
        List<Sale> records = page.getRecords();
        for (Sale sale : records) {
            SaleVo saleVo = new SaleVo();
            BeanUtils.copyProperties(sale,saleVo);
            List<SaleItem> saleItems = saleItemService.list(new QueryWrapper<SaleItem>().eq("pcode", sale.getCode()));
            saleVo.setSaleItems(saleItems);
            saleVos.add(saleVo);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("pages",page.getPages());
        map.put("current",page.getCurrent());
        map.put("size",page.getSize());
        map.put("total",page.getTotal());
        map.put("records",saleVos);
        map.put("hasPre",page.hasPrevious());
        map.put("hasNext",page.hasNext());
        return map;
    }


}
