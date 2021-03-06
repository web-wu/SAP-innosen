package com.tabwu.SAP.purchase.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbitmq.client.Channel;
import com.tabwu.SAP.common.constant.RabbitStaticConstant;
import com.tabwu.SAP.common.entity.MqMsg;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.purchase.entity.*;
import com.tabwu.SAP.purchase.entity.To.CostomerSupplierTo;
import com.tabwu.SAP.purchase.entity.To.UserTo;
import com.tabwu.SAP.purchase.entity.vo.PurchaseQueryVo;
import com.tabwu.SAP.purchase.entity.vo.PurchaseVo;
import com.tabwu.SAP.purchase.feign.CostomerSupplierFeign;
import com.tabwu.SAP.purchase.feign.MaterialWareFrign;
import com.tabwu.SAP.purchase.feign.UserFeign;
import com.tabwu.SAP.purchase.mapper.PurchaseMapper;
import com.tabwu.SAP.purchase.service.IPurchaseItemService;
import com.tabwu.SAP.purchase.service.IPurchaseService;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * @author tabwu
 * @since 2022-06-29
 */
@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements IPurchaseService {

    @Autowired
    private IPurchaseItemService purchaseItemService;
    @Autowired
    private MaterialWareFrign materialWareFrign;
    @Autowired
    private CostomerSupplierFeign costomerSupplierFeign;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private Executor executor;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public boolean addPurchase(PurchaseVo purchaseVo) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseVo,purchase);
        int insert = baseMapper.insert(purchase);
        if (insert > 0) {
            List<PurchaseItem> purchaseItems = purchaseVo.getPurchaseItems();
            for (PurchaseItem purchaseItem : purchaseItems) {
                purchaseItem.setId("");
                purchaseItem.setPcode(purchase.getCode());
            }
            purchaseItemService.saveBatch(purchaseItems);
            // ???????????????????????????????????????????????????MQ?????????????????????????????????????????????
            if (purchaseVo.getType() == 4) {
                sendMsgNotifyGenerateReturnRefund(purchase);
                for (PurchaseItem purchaseItem : purchaseItems) {
                    //????????????????????????????????????????????????
                    operatePurchaseReture(purchase, purchaseItem);
                }
            }

            // ??????????????????????????????--2????????????????????????MQ???????????????????????????????????????????????????
            if (purchaseVo.getType() == 2) {
                sendMsgNotifyGeneratePayBills(purchase);
            }
            return true;
        }
        return false;
    }

    @RabbitListener(queues = RabbitStaticConstant.SALE_RECEIPT_SUCCESS_QUEUE)
    public void listenerReturnBillsReceiptStatus(MqMsg mqMsg,Channel channel,Message message) throws IOException {
        if (mqMsg.getPayStatus()) {
            // ?????????????????????????????????????????????????????????????????????????????????????????????--7
            Purchase purchase = baseMapper.selectById(mqMsg.getItemId());
            baseMapper.update(null,new UpdateWrapper<Purchase>().eq("id",mqMsg.getItemId()).set("status",7));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }
    }

    private void sendMsgNotifyGenerateReturnRefund(Purchase purchase) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(purchase.getCode());
        MqMsg mqMsg = new MqMsg();
        mqMsg.setItemId(purchase.getId());
        mqMsg.setCode(purchase.getCode());
        mqMsg.setTax(purchase.getTax());
        mqMsg.setAllTax(purchase.getTaxPrice());
        mqMsg.setAllPrice(purchase.getAllPrice());
        mqMsg.setTotalPrice(purchase.getTotalPrice());
        rabbitTemplate.convertAndSend(RabbitStaticConstant.PURCHASE_TOPIC_EXCHANGE,"purchase.receipt",mqMsg,correlationData);
    }

    private void sendMsgNotifyGeneratePayBills(Purchase purchase) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(purchase.getCode());
        MqMsg mqMsg = new MqMsg();
        mqMsg.setItemId(purchase.getId());
        mqMsg.setCode(purchase.getCode());
        mqMsg.setTax(purchase.getTax());
        mqMsg.setAllTax(purchase.getTaxPrice());
        mqMsg.setAllPrice(purchase.getAllPrice());
        mqMsg.setTotalPrice(purchase.getTotalPrice());
        rabbitTemplate.convertAndSend(RabbitStaticConstant.PURCHASE_TOPIC_EXCHANGE,"purchase.pay",mqMsg,correlationData);
    }

    // ??????????????????????????????????????????????????????????????????????????????
    @RabbitListener(queues = RabbitStaticConstant.PURCHASE_PAY_SUCCESS_QUEUE)
    private void listenerPayStatusAutoGenerateMaterialInputBills(MqMsg mqMsg, Message message, Channel channel) throws IOException {
        Purchase purchase = baseMapper.selectById(mqMsg.getItemId());
        if (mqMsg.getPayStatus()) {
            // ????????????????????????????????????
            baseMapper.update(null,new UpdateWrapper<Purchase>().eq("id",purchase.getId()).set("status",3));
            // ??????????????????????????????
            autoGeneratePurchaseInputBills(purchase);
        } else {
            // ???????????????????????????????????????
            baseMapper.update(null,new UpdateWrapper<Purchase>().eq("id",purchase.getId()).set("status",8));
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    private void autoGeneratePurchaseInputBills(Purchase purchase) {
        List<PurchaseItem> purchaseItems = purchaseItemService.list(new QueryWrapper<PurchaseItem>().eq("pcode", purchase.getCode()));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String code = "YLS-PURCHASE-" + format.format(new Date()) + "-" + new Random().nextInt(100);
        Purchase purchaseInput = new Purchase();
        purchaseInput.setId("");
        purchaseInput.setCode(code);
        purchaseInput.setType(3);
        purchaseInput.setStatus(4);
        purchaseInput.setRelationItem(purchase.getId());
        if (baseMapper.insert(purchase) > 0) {
            for (PurchaseItem purchaseItem : purchaseItems) {
                purchaseItem.setId("");
                purchaseItem.setPcode(purchaseInput.getCode());
            }
            purchaseItemService.saveBatch(purchaseItems);
        } else {
            throw new CostomException(20004,"??????????????????????????????????????????");
        }
    }


    private void operatePurchaseReture(Purchase purchase, PurchaseItem purchaseItem) {
        MaterialWare materialWare = new MaterialWare();
        materialWare.setMid(purchaseItem.getMcode());
        materialWare.setWid(purchaseItem.getWareId());
        materialWare.setLid(purchaseItem.getLocalStorageId());
        materialWare.setLot(purchaseItem.getLot());
        materialWare.setMtype(purchaseItem.getMtype());
        materialWare.setStock(purchaseItem.getNumber() * -1);
        materialWareFrign.addWare(materialWare);
        purchase.setStatus(4);
        baseMapper.updateById(purchase);
    }

    private void operatePurchaseInputWare(Purchase purchase, PurchaseItem purchaseItem) {
        MaterialWare materialWare = new MaterialWare();
        materialWare.setMid(purchaseItem.getMcode());
        materialWare.setWid(purchaseItem.getWareId());
        materialWare.setLid(purchaseItem.getLocalStorageId());
        materialWare.setLot(purchaseItem.getLot());
        materialWare.setMtype(purchaseItem.getMtype());
        materialWare.setStock(purchaseItem.getNumbered());
        materialWareFrign.addWare(materialWare);
        // ???????????????????????????????????????0???????????????????????????????????????????????????????????????????????????---4
        if (purchaseItem.getNumbering() == 0) {
            purchase.setStatus(4);
            baseMapper.updateById(purchase);
        }
    }

    @Override
    @Transactional
    public boolean updatePurchase(PurchaseVo purchaseVo) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseVo,purchase);
        int insert = baseMapper.updateById(purchase);
        if (insert > 0) {
            // ????????????????????????????????????????????????????????????????????????????????????
            boolean remove = purchaseItemService.remove(new QueryWrapper<PurchaseItem>().eq("pcode", purchase.getCode()));
            if (remove) {
                List<PurchaseItem> purchaseItems = purchaseVo.getPurchaseItems();
                for (PurchaseItem purchaseItem : purchaseItems) {
                    purchaseItem.setId("");
                    purchaseItem.setPcode(purchase.getCode());
                    purchaseItemService.save(purchaseItem);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public PurchaseVo findOneById(String id) {
        PurchaseVo purchaseVo = new PurchaseVo();
        Purchase purchase = baseMapper.selectById(id);
        BeanUtils.copyProperties(purchase,purchaseVo);
        List<PurchaseItem> purchaseItems = purchaseItemService.list(new QueryWrapper<PurchaseItem>().eq("pcode", purchase.getCode()));
        purchaseVo.setPurchaseItems(purchaseItems);
        return purchaseVo;
    }

    @Override
    public Map<String, Object> listPage(PurchaseQueryVo purchaseQueryVo) {
        Page<Purchase> page = new Page<>(purchaseQueryVo.getCurrent(), purchaseQueryVo.getSize());
        QueryWrapper<Purchase> wrapper = new QueryWrapper<>();
        wrapper.eq("type",purchaseQueryVo.getType());
        if (!StringUtils.isEmpty(purchaseQueryVo.getCode())) {
            wrapper.eq("code",purchaseQueryVo.getCode());
        }
        if (!StringUtils.isEmpty(purchaseQueryVo.getTxId())) {
            wrapper.eq("tx_id",purchaseQueryVo.getTxId());
        }
        if (purchaseQueryVo.getStatus() != null) {
            wrapper.eq("status",purchaseQueryVo.getStatus());
        }
        if (!StringUtils.isEmpty(purchaseQueryVo.getPurchaser())) {
            wrapper.eq("purchaser",purchaseQueryVo.getPurchaser());
        }
        if (!StringUtils.isEmpty(purchaseQueryVo.getSupplier())) {
            wrapper.like("supplier",purchaseQueryVo.getSupplier());
        }
        baseMapper.selectPage(page,wrapper);
        ArrayList<PurchaseVo> purchaseVos = new ArrayList<>();
        List<Purchase> records = page.getRecords();
        for (Purchase purchase : records) {
            PurchaseVo purchaseVo = new PurchaseVo();
            BeanUtils.copyProperties(purchase,purchaseVo);
            List<PurchaseItem> purchaseItems = purchaseItemService.list(new QueryWrapper<PurchaseItem>().eq("pcode", purchase.getCode()));
            purchaseVo.setPurchaseItems(purchaseItems);
            purchaseVos.add(purchaseVo);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("pages",page.getPages());
        map.put("current",page.getCurrent());
        map.put("size",page.getSize());
        map.put("total",page.getTotal());
        map.put("records",purchaseVos);
        map.put("hasPre",page.hasPrevious());
        map.put("hasNext",page.hasNext());
        return map;
    }

    @Override
    public ArrayList<PurchaseVo> findList(PurchaseQueryVo purchaseQueryVo) {
        List<Purchase> purchases = baseMapper.selectList(new QueryWrapper<Purchase>().eq("type", purchaseQueryVo.getType()).eq("status", purchaseQueryVo.getStatus()));
        ArrayList<PurchaseVo> purchaseVos = new ArrayList<>();
        for (Purchase purchase : purchases) {
            PurchaseVo purchaseVo = new PurchaseVo();
            BeanUtils.copyProperties(purchase,purchaseVo);
            List<PurchaseItem> purchaseItems = purchaseItemService.list(new QueryWrapper<PurchaseItem>().eq("pcode", purchase.getCode()));
            purchaseVo.setPurchaseItems(purchaseItems);
            purchaseVos.add(purchaseVo);
        }
        return purchaseVos;
    }

    @Override
    public HashMap<String, Object> findPurchaseOrder(String id) throws ExecutionException, InterruptedException {

        HashMap<String, Object> hashMap = new HashMap<>();

        CompletableFuture<Purchase> purchaseFuture = CompletableFuture.supplyAsync(() -> {
            return baseMapper.selectById(id);
        }, executor);

        CompletableFuture<Void> purchaseItemsFuture = purchaseFuture.thenAcceptAsync(purchase -> {
            handlerQueryPurchase(hashMap, purchase);
        }, executor);

        CompletableFuture<Void> customerSupplierFuture = purchaseFuture.thenAcceptAsync(purchase -> {
            handlerQuerySupplier(hashMap, purchase);
        }, executor);

        CompletableFuture<Void> userFuture = purchaseFuture.thenAcceptAsync(purchase -> {
            handlerQueryPurchaser(hashMap, purchase);
        }, executor);

        CompletableFuture.allOf(purchaseFuture,purchaseItemsFuture,customerSupplierFuture,userFuture).get();

        return hashMap;
    }

    @Override
    public boolean changePurchaseStatusById(String id,Integer status) {
        Purchase purchase = baseMapper.selectById(id);
        List<PurchaseItem> purchaseItems = purchaseItemService.list(new QueryWrapper<PurchaseItem>().eq("pcode", purchase.getCode()));
        // ??????????????????????????????????????????????????????????????????????????????
        if (purchase.getType() == 3) {
            baseMapper.update(null,new UpdateWrapper<Purchase>().eq("id",id).set("status",status));
            for (PurchaseItem purchaseItem : purchaseItems) {
                operatePurchaseInputWare(purchase, purchaseItem);
            }
            return true;
        }

        return false;
    }

    private void handlerQueryPurchaser(HashMap<String, Object> hashMap, Purchase purchase) {
        R userData = userFeign.findUserByUsername(purchase.getPurchaser());
        UserTo user = JSON.parseObject(JSON.toJSONString(userData.getData().get("user")), UserTo.class);
        hashMap.put("company","???????????????(??????)????????????");
        hashMap.put("contact",user.getUsername());
        hashMap.put("tel",user.getTel());
        hashMap.put("email",user.getEmail());
        hashMap.put("address",user.getAddress());
    }

    private void handlerQuerySupplier(HashMap<String, Object> hashMap, Purchase purchase) {
        R supplierDate = costomerSupplierFeign.findCostomerSupplierByCompanyName(purchase.getSupplier());
        CostomerSupplierTo customerSupplier = JSON.parseObject(JSON.toJSONString(supplierDate.getData().get("customerSupplier")), CostomerSupplierTo.class);
        hashMap.put("supplier_company",customerSupplier.getCompanyName());
        hashMap.put("supplier_contact",customerSupplier.getContact());
        hashMap.put("supplier_tel",customerSupplier.getTel());
        hashMap.put("supplier_email",customerSupplier.getEmail());
        hashMap.put("supplier_address",customerSupplier.getAddress());
    }

    private void handlerQueryPurchase(HashMap<String, Object> hashMap, Purchase purchase) {
        List<PurchaseItem> purchaseItems = purchaseItemService.list(new QueryWrapper<PurchaseItem>().eq("pcode", purchase.getCode()));
        ArrayList<Map<String, String>> dataList = new ArrayList<>();
        for (int i = 0; i < purchaseItems.size(); i++) {
            HashMap<String, String> mapItem = new HashMap<>();
            mapItem.put("index", i + 1 + "" );
            mapItem.put("mcode",purchaseItems.get(i).getMcode());
            mapItem.put("name",purchaseItems.get(i).getName());
            mapItem.put("lot",purchaseItems.get(i).getLot());
            mapItem.put("param",purchaseItems.get(i).getParam());
            mapItem.put("number",purchaseItems.get(i).getNumber().toString());
            mapItem.put("price",purchaseItems.get(i).getPrice().toString());
            mapItem.put("tax",purchaseItems.get(i).getTax());
            mapItem.put("allTax",purchaseItems.get(i).getAllTax().toString());
            mapItem.put("allPrice",purchaseItems.get(i).getAllPrice().toString());
            mapItem.put("totalPrice",purchaseItems.get(i).getTotalPrice().toString());
            dataList.add(mapItem);
        }
        hashMap.put("dataList",dataList);
        hashMap.put("code", purchase.getCode());
        hashMap.put("tx_id", purchase.getTxId());
        hashMap.put("tax", purchase.getTax());
        hashMap.put("taxPrice", purchase.getTaxPrice());
        hashMap.put("allPrice", purchase.getAllPrice());
        hashMap.put("totalPrice", purchase.getTotalPrice());
        hashMap.put("remark", purchase.getRemark());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        hashMap.put("data",format.format(new Date()));
    }
}
