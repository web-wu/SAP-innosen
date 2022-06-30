package com.tabwu.SAP.purchase.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.purchase.entity.*;
import com.tabwu.SAP.purchase.entity.vo.PurchaseQueryVo;
import com.tabwu.SAP.purchase.entity.vo.PurchaseVo;
import com.tabwu.SAP.purchase.feign.CostomerSupplierFeign;
import com.tabwu.SAP.purchase.feign.MaterialWareFrign;
import com.tabwu.SAP.purchase.feign.UserFeign;
import com.tabwu.SAP.purchase.mapper.PurchaseMapper;
import com.tabwu.SAP.purchase.service.IPurchaseItemService;
import com.tabwu.SAP.purchase.service.IPurchaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
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

    @Override
    @Transactional
    public boolean addPurchase(PurchaseVo purchaseVo) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseVo,purchase);
        int insert = baseMapper.insert(purchase);
        if (insert > 0) {
            List<PurchaseItem> purchaseItems = purchaseVo.getPurchaseItems();
            for (PurchaseItem purchaseItem : purchaseItems) {
                PurchaseItem item = new PurchaseItem();
                BeanUtils.copyProperties(purchaseItem,item);
//                item.setId(purchase.getId());
                item.setPcode(purchase.getCode());
                purchaseItemService.save(item);
                // 如果单据类型是收货单时，应该给该物料做相应的入库操作  TODO 远程添加库存时需要分布式事务保证数据一致性
                if (purchaseVo.getType() == 3) {
                    operatePurchaseInputWare(purchase, purchaseItem);
                }

                // 如果单据类型是退货单时，应该给该物料做相应的回退库存操作   TODO
                if (purchaseVo.getType() == 4) {
                    operatePurchaseReture(purchase, purchaseItem);
                }
            }
            return true;
        }
        return false;
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
        // 当采购收货单的待收货数量为0时，添加库存操作完成是需自动改变入库单的状态为完成---4
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
            // 当主表修改成功后，先删除字表相应记录，再重新保存相应记录
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
            List<PurchaseItem> purchaseItems = purchaseItemService.list(new QueryWrapper<PurchaseItem>().eq("pcode", purchase.getCode()));
            hashMap.put("dataList",purchaseItems);
            hashMap.put("code",purchase.getCode());
            hashMap.put("tx_id",purchase.getTxId());
            hashMap.put("tax",purchase.getTax());
            hashMap.put("taxPrice",purchase.getTaxPrice());
            hashMap.put("allPrice",purchase.getAllPrice());
            hashMap.put("totalPrice",purchase.getTotalPrice());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            hashMap.put("data",format.format(new Date()));
        }, executor);

        CompletableFuture<Void> customerSupplierFuture = purchaseFuture.thenAcceptAsync(purchase -> {
            R supplierDate = costomerSupplierFeign.findCostomerSupplierByCompanyName(purchase.getSupplier());
            CustomerSupplier customerSupplier = JSON.parseObject(JSON.toJSONString(supplierDate.getData().get("customerSupplier")),CustomerSupplier.class);
            hashMap.put("supplier_company",customerSupplier.getCompanyName());
            hashMap.put("supplier_contact",customerSupplier.getContact());
            hashMap.put("supplier_tel",customerSupplier.getTel());
            hashMap.put("supplier_email",customerSupplier.getEmail());
            hashMap.put("supplier_address",customerSupplier.getAddress());
        }, executor);

        CompletableFuture<Void> userFuture = purchaseFuture.thenAcceptAsync(purchase -> {
            R userData = userFeign.findUserByUsername(purchase.getPurchaser());
            User user = JSON.parseObject(JSON.toJSONString(userData.getData().get("user")), User.class);
            hashMap.put("company","易良盛科技(天津)有限公司");
            hashMap.put("contact",user.getUsername());
            hashMap.put("tel",user.getTel());
            hashMap.put("email",user.getEmail());
            hashMap.put("address",user.getAddress());
        }, executor);

        CompletableFuture.allOf(purchaseFuture,purchaseItemsFuture,customerSupplierFuture,userFuture).get();

        return hashMap;
    }
}
