package com.tabwu.SAP.sale.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbitmq.client.Channel;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.sale.constant.RabbitStaticConstant;
import com.tabwu.SAP.sale.entity.MqMsg;
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
        createSaleOrder(saleVo, sale, saleItems);

        if (saleVo.getType() == 1) {
            // 3、销售订单添加成功后远程扣减物料库存
            reduceWareStockRemote(saleItems);

            // TODO 4、发送消息给MQ，财务服务根据销售订单自动生成付款订单
            CorrelationData correlationData = new CorrelationData();
            correlationData.setId(sale.getCode());
            MqMsg mqMsg = new MqMsg();
            mqMsg.setItemId(sale.getId());
            mqMsg.setCode(sale.getCode());
            mqMsg.setTax(sale.getTax());
            mqMsg.setAllTax(sale.getAllTax());
            mqMsg.setAllPrice(sale.getAllPrice());
            mqMsg.setTotalPrice(sale.getTotalPrice());
            rabbitTemplate.convertAndSend(RabbitStaticConstant.innosen_topic,"sale.add",mqMsg,correlationData);
        }

        return true;
    }


    @RabbitListener(queues = RabbitStaticConstant.saleOrderPayQueue)
    public void listenerPayStatus(MqMsg mqMsg, Channel channel, Message message) {
        // 5、监听MQ消息队列，查询付款状态, 返回销售订单的code码与付款状态
        if (mqMsg.getPayStatus()) {
            // 6、付款成功后，根据销售订单自动生成物料出库单，将销售、出库单状态改为待出库
            // 物料出库单code码生成
            // TODO 如付款成功，根据消息队列取出消息，拿到付款成功的订单编号code，根据code查出单据信息生成出库单信息
            /*SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String code = "yls-" + format.format(new Date()) + "-" + new Random(10);
            Sale saleOutput = new Sale();
            saleOutput.setType(2);
            saleOutput.setCode(code);
            createSaleOrder();*/
        }
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

    private void createSaleOrder(SaleVo saleVo, Sale sale,List<SaleItem> saleItems) {
        BeanUtils.copyProperties(saleVo,sale);
        int insert = baseMapper.insert(sale);
        if (insert == 0) {
            throw new CostomException(20004,"销售订单添加失败，或重复提交失败");
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
        // TODO 修改销售单信息,当物料数量也被修改时，需要同时修改仓库中扣减数量
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
        // 只能删除单据状态为0时，还未付款的单据
        int delete = baseMapper.delete(new UpdateWrapper<Sale>().eq("id",id).eq("status",0));
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
