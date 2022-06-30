package com.tabwu.SAP.purchase.controller;



import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.purchase.entity.Purchase;
import com.tabwu.SAP.purchase.entity.PurchaseItem;
import com.tabwu.SAP.purchase.entity.vo.PurchaseQueryVo;
import com.tabwu.SAP.purchase.entity.vo.PurchaseVo;
import com.tabwu.SAP.purchase.service.IPurchaseItemService;
import com.tabwu.SAP.purchase.service.IPurchaseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private IPurchaseItemService purchaseItemService;

    @PostMapping("/add")
    @ApiOperation("添加采购单据")
    public R add(@ApiParam(name = "purchaseVo",value = "purchaseVo",required = true)
                     @RequestBody PurchaseVo purchaseVo) {
        boolean flag = purchaseService.addPurchase(purchaseVo);
        return flag ? R.ok() : R.error();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除采购单据")
    public R delete(@ApiParam(name = "id",value = "id",required = true)
                     @PathVariable("id") String id) {
        Purchase purchase = purchaseService.getById(id);
        if (purchase == null) {
            throw new CostomException(20004,"未找到id为：" + id + "的物料！！！");
        }
        purchaseService.remove(new UpdateWrapper<Purchase>().eq("id",id));
        purchaseItemService.remove(new UpdateWrapper<PurchaseItem>().eq("pcode",purchase.getCode()));
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



    @GetMapping("/approve/{id}/{status}/{purchaser}")
    @ApiOperation("审核采购需求订单时为采购订单分配采购员")
    public R approvePurchase(@PathVariable("id") String id,@PathVariable("status") Integer status,@PathVariable(value = "purchaser",required = false) String purchaser) {
        Purchase purchase = purchaseService.getById(id);
        if (purchase == null) {
            throw new CostomException(20004,"未找到id为" + id + "的单据");
        }
        purchase.setStatus(status);
        purchase.setPurchaser(purchaser);
        purchaseService.updateById(purchase);
        return R.ok();
    }


    @GetMapping("/exportOrder/{id}")
    @ApiOperation("以excel格式导出采购订单表")
    public void exportPurchaseOrder(@ApiParam(name = "id",value = "采购订单id",required = true)
                                        @PathVariable("id") String id, HttpServletResponse response) {
        try {
            TemplateExportParams params = new TemplateExportParams("SAP-purchase/src/main/resources/static/purchaseOrder-template.xlsx");
            HashMap<String,Object> dataMap = purchaseService.findPurchaseOrder(id);
            Workbook workbook = ExcelExportUtil.exportExcel(params, dataMap);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode( "采购订单.xlsx", "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new CostomException(20004,"导出采购订单出现异常！！！" + e.getMessage());
        }
    }
}
