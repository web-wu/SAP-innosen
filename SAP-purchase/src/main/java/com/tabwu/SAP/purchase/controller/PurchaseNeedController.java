package com.tabwu.SAP.purchase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.purchase.entity.PurchaseNeed;
import com.tabwu.SAP.purchase.service.IPurchaseNeedService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@RestController
@RequestMapping("/purchase/purchase-need")
public class PurchaseNeedController {
    @Autowired
    private IPurchaseNeedService purchaseNeedService;


    @PostMapping("/add")
    @ApiOperation(value = "添加采购需求订单")
    public R addPurchaseNeed(@ApiParam(name = "purchaseNeed",value = "purchaseNeed",required = true)
                     @RequestBody PurchaseNeed purchaseNeed) {
        purchaseNeedService.save(purchaseNeed);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除采购需求订单")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        purchaseNeedService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改采购需求订单")
    public R updateById(@ApiParam(name = "purchaseNeed",value = "purchaseNeed",required = true)
                            @RequestBody PurchaseNeed purchaseNeed) {
        purchaseNeedService.updateById(purchaseNeed);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询采购需求订单")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        PurchaseNeed purchaseNeed = purchaseNeedService.getById(id);
        return R.ok().data("purchaseNeed",purchaseNeed);
    }

    @GetMapping("/list/{status}")
    @ApiOperation(value = "查询所有采购需求订单")
    public R list(@ApiParam(name = "status",value = "采购需求订单状态，默认0",required = true)
                      @PathVariable("status") Integer status) {
        List<PurchaseNeed> list = purchaseNeedService.list(new QueryWrapper<PurchaseNeed>().eq("status", status));
        return R.ok().data("list",list);
    }

    @GetMapping("/approve/{id}/{status}")
    @ApiOperation(value = "审批采购需求订单状态")
    public R approveNeed(@ApiParam(name = "status",value = "采购需求订单状态，1-通过，-1-不通过",required = true)
                  @PathVariable("id") String id,@PathVariable("status") Integer status) {
        PurchaseNeed purchaseNeed = purchaseNeedService.getById(id);
        if (purchaseNeed == null) {
            throw new CostomException(20004,"改采购需求订单不存在");
        }
        purchaseNeed.setStatus(status);
        purchaseNeedService.updateById(purchaseNeed);
        return R.ok();
    }
}
