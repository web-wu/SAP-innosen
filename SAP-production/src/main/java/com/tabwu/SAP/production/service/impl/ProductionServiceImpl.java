package com.tabwu.SAP.production.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.common.constant.RabbitStaticConstant;
import com.tabwu.SAP.common.entity.MqMsg;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.production.entity.Production;
import com.tabwu.SAP.production.entity.ProductionItem;
import com.tabwu.SAP.production.entity.to.WareStockTo;
import com.tabwu.SAP.production.entity.vo.ProductionVo;
import com.tabwu.SAP.production.feign.MaterialStockFeign;
import com.tabwu.SAP.production.mapper.ProductionMapper;
import com.tabwu.SAP.production.service.IProductionItemService;
import com.tabwu.SAP.production.service.IProductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@Service
public class ProductionServiceImpl extends ServiceImpl<ProductionMapper, Production> implements IProductionService {

    @Autowired
    private MaterialStockFeign materialStockFeign;
    @Autowired
    private IProductionItemService productionItemService;
    @Autowired
    protected RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public void addProductionBills(Production production) {
        // 添加生产订单
        if (production.getType() == 1) {
            generateProductionOrderBills(production);
        }
        // 添加生产取料单
        if (production.getType() == 2) {
            generateProductionMaterialOutputBills(production);
        }
        // 添加生产入库单
        if (production.getType() == 3) {
            generateProductionMaterialInputBills(production);
        }
    }

    private void generateProductionMaterialInputBills(Production production) {
        addProductionOrderToDb(production,production.getProductionItems());
    }

    private void generateProductionMaterialOutputBills(Production production) {
        if (!addProductionOrderToDb(production,production.getProductionItems())) {
            throw new CostomException(20004,"添加生产物料取货单发生异常");
        }
        // 5、 发送消息给MQ通知财务服务生成加工费付款单据
        sendMsgToMqAndNotifyAutoGeneratePayBills(production);
    }

    private void generateProductionOrderBills(Production production) {
        List<ProductionItem> productionItems = production.getProductionItems();
        List<WareStockTo> wareStockToList = new ArrayList<>();
        for (ProductionItem productionItem : productionItems) {
            if (productionItem.getSubType() == 1) {
                WareStockTo wareStockTo = new WareStockTo();
                BeanUtils.copyProperties(productionItem,wareStockTo);
                wareStockToList.add(wareStockTo);
            }
        }
        // 1、 根据子物料code码审查物料库存是否足够
        if (!checkSubMaterialStock(wareStockToList)) {
            throw new CostomException(20004,"子物料库存不足，不能生成生产订单");
        }
        // 2、 根据子物料扣减库存
        if (!reduceWareStockByCondition(wareStockToList)) {
            throw new CostomException(20004,"扣减库存失败，不能生成生产订单");
        }
        // 3、 向数据库中插入订单
        if (!addProductionOrderToDb(production, productionItems)) {
            throw new CostomException(20004,"添加生产订单发生异常");
        }
        // 4、 根据生成订单自动生成物料出货单据
        autoGenerateMaterialOutputBillsByProductionOrder(production);

    }

    private void sendMsgToMqAndNotifyAutoGeneratePayBills(Production production) {
        List<ProductionItem> productionItems = production.getProductionItems();
        BigDecimal decimal = new BigDecimal(0);
        for (ProductionItem productionItem : productionItems) {
            if (productionItem.getSubType() != 1) {
                decimal = decimal.add(productionItem.getAllPrice());
            }
        }
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(production.getCode());
        MqMsg mqMsg = new MqMsg();
        mqMsg.setItemId(production.getId());
        mqMsg.setCode(production.getCode());
        mqMsg.setTotalPrice(decimal);
        rabbitTemplate.convertAndSend(RabbitStaticConstant.PRODUCTION_TOPIC_EXCHANGE, "production.pay", mqMsg, correlationData);
    }

    private void autoGenerateMaterialOutputBillsByProductionOrder(Production production) {
        List<ProductionItem> productionItems = production.getProductionItems();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String code = "YLS-PRODUCTION-" + format.format(new Date()) + "-" + new Random().nextInt(100);
        production.setType(2);
        production.setRelationItem(production.getId());
        production.setId("");
        production.setCode(code);
        production.setStatus(0);
        int insert = baseMapper.insert(production);
        if (insert > 0) {
            List<ProductionItem> productionItemList = productionItems.stream().filter(item -> {
                return item.getSubType() == 1;
            }).map(item -> {
                item.setPcode(code);
                item.setId("");
                return item;
            }).collect(Collectors.toList());
            productionItemService.saveBatch(productionItemList);
            // 5、 发送消息给MQ通知财务服务生成加工费付款单据
            sendMsgToMqAndNotifyAutoGeneratePayBills(production);
        }
    }


    private Boolean addProductionOrderToDb(Production production, List<ProductionItem> productionItems) {
        int insert = baseMapper.insert(production);
        if (insert > 0) {
            for (ProductionItem productionItem : productionItems) {
                productionItem.setPcode(production.getCode());
            }
            productionItemService.saveBatch(productionItems);
            return true;
        }
        return false;
    }

    private boolean reduceWareStockByCondition(List<WareStockTo> wareStockToList) {
        R r = materialStockFeign.reduceWareStockByCondition(wareStockToList);
        if (r.getCode() == 20000) {
            return true;
        }
        return false;
    }

    private Boolean checkSubMaterialStock(List<WareStockTo> wareStockToList) {
        R r = materialStockFeign.checkStockByMcode(wareStockToList);
        if (r.getCode() == 20000) {
            return true;
        }
        return false;
    }

    @Override
    public void changeMaterialInputBillsStatus(String id,Integer status) {
        Production order = baseMapper.selectById(id);
        order.setStatus(status);
        int update = baseMapper.updateById(order);
        if (update > 1) {
            Production production = baseMapper.selectOne(new QueryWrapper<Production>().eq("relation_item", order.getRelationItem()));
            production.setStatus(status);
            baseMapper.updateById(production);
        }
    }


    @Override
    public void changeProductionBillsStatus(String id,Integer status) {
        Production order = baseMapper.selectById(id);
        order.setStatus(status);
        int update = baseMapper.updateById(order);
        if (update > 1) {
            Production production = baseMapper.selectOne(new QueryWrapper<Production>().eq("relation_item", order.getRelationItem()));
            production.setStatus(status);
            baseMapper.updateById(production);
            // 确定生产取料完成之后根据生产订单自动生成生产收货单据
            autoGenerateMaterialInputBillsByMaterialOutputBills(production);
        }
    }

    private void autoGenerateMaterialInputBillsByMaterialOutputBills(Production production) {
        List<ProductionItem> productionItems = production.getProductionItems();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String code = "YLS-PRODUCTION-" + format.format(new Date()) + "-" + new Random().nextInt(100);
        production.setType(3);
        production.setRelationItem(production.getId());
        production.setId("");
        production.setCode(code);
        production.setStatus(2);
        int insert = baseMapper.insert(production);
        if (insert > 0) {
            List<ProductionItem> productionItemList = productionItems.stream().filter(item -> {
                return item.getSubType() == 2;
            }).map(item -> {
                item.setPcode(code);
                item.setId("");
                return item;
            }).collect(Collectors.toList());
            productionItemService.saveBatch(productionItemList);
        }
    }


    @Override
    public HashMap<String, Object> queryPage(ProductionVo productionVo) {
        Page<Production> page = new Page<>(productionVo.getCurrent(), productionVo.getSize());
        QueryWrapper<Production> wrapper = new QueryWrapper<>();

        wrapper.eq("type",productionVo.getType());
        if (!StringUtils.isEmpty(productionVo.getCode())) {
            wrapper.eq("code",productionVo.getCode());
        }
        if (productionVo.getPType() != null) {
            wrapper.eq("p_type",productionVo.getPType());
        }
        if (productionVo.getStatus() != null) {
            wrapper.eq("status",productionVo.getStatus());
        }
        if (!StringUtils.isEmpty(productionVo.getClerk())) {
            wrapper.eq("clerk",productionVo.getClerk());
        }
        if (!StringUtils.isEmpty(productionVo.getCompany())) {
            wrapper.like("company",productionVo.getCompany());
        }
        baseMapper.selectPage(page,wrapper);

        ArrayList<Production> productions = new ArrayList<>();
        List<Production> records = page.getRecords();
        for (Production production : records) {
            List<ProductionItem> productionItems = productionItemService.list(new QueryWrapper<ProductionItem>().eq("pcode", production.getCode()));
            production.setProductionItems(productionItems);
            productions.add(production);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("pages",page.getPages());
        map.put("current",page.getCurrent());
        map.put("size",page.getSize());
        map.put("total",page.getTotal());
        map.put("records",productions);
        map.put("hasPre",page.hasPrevious());
        map.put("hasNext",page.hasNext());
        return map;
    }
}
