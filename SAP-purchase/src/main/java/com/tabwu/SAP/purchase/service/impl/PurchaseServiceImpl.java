package com.tabwu.SAP.purchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.common.utils.PageUtil;
import com.tabwu.SAP.purchase.entity.MaterialWare;
import com.tabwu.SAP.purchase.entity.Purchase;
import com.tabwu.SAP.purchase.entity.PurchaseItem;
import com.tabwu.SAP.purchase.entity.vo.PurchaseQueryVo;
import com.tabwu.SAP.purchase.entity.vo.PurchaseVo;
import com.tabwu.SAP.purchase.feign.MaterialWareFrign;
import com.tabwu.SAP.purchase.mapper.PurchaseMapper;
import com.tabwu.SAP.purchase.service.IPurchaseItemService;
import com.tabwu.SAP.purchase.service.IPurchaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    MaterialWare materialWare = new MaterialWare();
                    materialWare.setMid(purchaseItem.getMcode());
                    materialWare.setWid(purchaseItem.getWareId());
                    materialWare.setLid(purchaseItem.getLocalStorageId());
                    materialWare.setLot(purchaseItem.getLot());
                    materialWare.setMtype(purchaseItem.getMtype());
                    materialWare.setStock(purchaseItem.getNumbered());
                    materialWareFrign.addWare(materialWare);
                }

                // 如果单据类型是退货单时，应该给该物料做相应的回退库存操作   TODO
                if (purchaseVo.getType() == 4) {

                }
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updatePurchase(PurchaseVo purchaseVo) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseVo,purchase);
        int insert = baseMapper.updateById(purchase);
        if (insert > 0) {
            List<PurchaseItem> purchaseItems = purchaseVo.getPurchaseItems();
            for (PurchaseItem purchaseItem : purchaseItems) {
                PurchaseItem item = new PurchaseItem();
                BeanUtils.copyProperties(purchaseItem,item);
                /*item.setId(purchase.getId());
                item.setPcode(purchase.getCode());*/
                purchaseItemService.saveOrUpdate(item,new QueryWrapper<PurchaseItem>().eq("pcode",item.getPcode()));
            }
            return true;
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
            wrapper.like("tx_id",purchaseQueryVo.getTxId());
        }
        if (purchaseQueryVo.getStatus() != null) {
            wrapper.like("status",purchaseQueryVo.getStatus());
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
}
