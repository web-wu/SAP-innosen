package com.tabwu.SAP.purchase.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.purchase.entity.Purchase;
import com.tabwu.SAP.purchase.entity.PurchaseItem;
import com.tabwu.SAP.purchase.entity.vo.PurchaseQueryVo;
import com.tabwu.SAP.purchase.entity.vo.PurchaseVo;
import com.tabwu.SAP.purchase.service.IPurchaseItemService;
import com.tabwu.SAP.purchase.service.IPurchaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tabwu
 * @since 2022-06-29
 */
@RestController
@RequestMapping("/purchase/purchase")
public class PurchaseController {
    @Autowired
    private IPurchaseService purchaseService;


    @PostMapping("/add")
    @ApiOperation("添加采购单据")
    public R add(@ApiParam(name = "purchaseVo",value = "purchaseVo",required = true)
                     @RequestBody PurchaseVo purchaseVo) {
        boolean flag = purchaseService.addPurchase(purchaseVo);
        return flag ? R.ok() : R.error();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除采购单据")
    public R delete(@ApiParam(name = "type",value = "单据类型",required = true)
                     @PathVariable("id") String id) {
        purchaseService.remove(new UpdateWrapper<Purchase>().eq("id",id));
        // TODO 删除字表记录
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation("根据id修改采购单据")
    public R updateByid(@ApiParam(name = "type",value = "单据类型",required = true)
                 @RequestBody PurchaseVo purchaseVo) {
        boolean flag = purchaseService.updatePurchase(purchaseVo);
        return flag ? R.ok() : R.error();
    }


    @GetMapping("/findOne/{id}")
    @ApiOperation("根据id查询采购单据")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                             @PathVariable("id") String id) {
        PurchaseVo purchaseVo = purchaseService.findOneById(id);
        return R.ok().data("purchaseVo",purchaseVo);
    }

    @PostMapping("/list")
    @ApiOperation("查询所有采购单据")
    public R list(@RequestBody PurchaseQueryVo purchaseQueryVo) {
        ArrayList<PurchaseVo> purchaseVos = purchaseService.findList(purchaseQueryVo);
        return R.ok().data("purchaseVos",purchaseVos);
    }


    @PostMapping("/listPage")
    @ApiOperation("分页查询采购单据")
    public R listPage(@RequestBody PurchaseQueryVo purchaseQueryVo) {
        Map<String,Object> map = purchaseService.listPage(purchaseQueryVo);
        return R.ok().data("pageMap",map);
    }


    @GetMapping("/approve/{id}/{status}")
    @ApiOperation("审核采购需求订单")
    public R approvePurchase(@PathVariable("id") String id,@PathVariable("status") Integer status) {
        Purchase purchase = purchaseService.getById(id);
        if (purchase == null) {
            throw new CostomException(20004,"未找到id为" + id + "的单据");
        }
        purchase.setStatus(status);
        purchaseService.updateById(purchase);
        return R.ok();
    }
}
