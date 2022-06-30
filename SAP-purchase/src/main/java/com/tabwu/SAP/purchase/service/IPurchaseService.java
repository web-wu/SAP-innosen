package com.tabwu.SAP.purchase.service;

import com.tabwu.SAP.purchase.entity.Purchase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tabwu.SAP.purchase.entity.vo.PurchaseQueryVo;
import com.tabwu.SAP.purchase.entity.vo.PurchaseVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-29
 */
public interface IPurchaseService extends IService<Purchase> {

    boolean addPurchase(PurchaseVo purchaseVo);

    boolean updatePurchase(PurchaseVo purchaseVo);

    PurchaseVo findOneById(String id);

    Map<String, Object> listPage(PurchaseQueryVo purchaseQueryVo);

    ArrayList<PurchaseVo> findList(PurchaseQueryVo purchaseQueryVo);

    HashMap<String, Object> findPurchaseOrder(String id) throws ExecutionException, InterruptedException;
}
