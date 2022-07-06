package com.tabwu.SAP.sale.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.sale.entity.Sale;
import com.tabwu.SAP.sale.entity.SaleItem;
import com.tabwu.SAP.sale.entity.vo.SaleQueryVo;
import com.tabwu.SAP.sale.entity.vo.SaleVo;
import com.tabwu.SAP.sale.service.ISaleItemService;
import com.tabwu.SAP.sale.service.ISaleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * @author tabwu
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/sale/sale")
public class SaleController {
    @Autowired
    private ISaleService saleService;
    @Autowired
    private ISaleItemService saleItemService;


    @PostMapping("/add")
    @ApiOperation("添加销售单")
    public R addSale(@ApiParam(name = "saleVo",value = "saleVo",required = true)
                         @RequestBody SaleVo saleVo) {
        boolean flag = saleService.addSale(saleVo);
        return flag ? R.ok() : R.error();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除销售单")
    public R delete(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        Boolean flag = saleService.deleteSaleById(id);
        return flag ? R.ok() : R.error();
    }

    @PutMapping("/update")
    @ApiOperation("修改销售单")
    public R update(@ApiParam(name = "saleVo",value = "saleVo",required = true)
                        @RequestBody SaleVo saleVo) {
        boolean flag = saleService.updateSaleOrder(saleVo);
        return flag ? R.ok() : R.error();
    }


    @GetMapping("/findOne/{id}")
    @ApiOperation("根据id查询销售单")
    public R findOne(@ApiParam(name = "id",value = "id",required = true)
                    @PathVariable("id") String id) {
        Sale sale = saleService.getById(id);
        List<SaleItem> saleItems = saleItemService.list(new QueryWrapper<SaleItem>().eq("pcode", sale.getCode()));
        SaleVo saleVo = new SaleVo();
        BeanUtils.copyProperties(sale,saleVo);
        saleVo.setSaleItems(saleItems);
        return R.ok().data("saleVo",saleVo);
    }


    @PostMapping("/pageList")
    @ApiOperation("条件分页查询单据")
    public R pageList(@ApiParam(name = "saleQueryVo",value = "saleQueryVo",required = true)
                    @RequestBody SaleQueryVo saleQueryVo) {
        HashMap<String,Object> pageMap = saleService.pageList(saleQueryVo);
        return R.ok().data("pageMap",pageMap);
    }


    @GetMapping("/changeStatus")
    @ApiOperation("修改单据状态")
    public R changeStatusById(@RequestParam("id") String id,@RequestParam("status") Integer status) {
        boolean update = saleService.update(null, new UpdateWrapper<Sale>().eq("id", id).set("status", status));
        return update ? R.ok() : R.error();
    }


    @GetMapping("/comfirmedReturn/{id}")
    @ApiOperation("根据id确认退货成功修改单据状态")
    public R comfirmedReturnSuccessful(@ApiParam(name = "id",value = "退货单据id",required = true)
                                           @PathVariable("id") String id) {
        boolean flag = saleService.comfirmedReturnSuccess(id);
        return flag ? R.ok() : R.error();
    }



    @PostMapping("/exportBills/{id}/{type}")
    @ApiOperation("根据id确认退货成功修改单据状态")
    public void excelExportSaleBillsInformation(@ApiParam(name = "type",value = "单据类型，1-销售订单，2-销售出库单，3-销售退货单",required = true)
                                                 @PathVariable("id") String id, @PathVariable("type") Integer type, HttpServletResponse response) {
        try {
            String billsName = null;
            TemplateExportParams params = null;
            if (type == 1) {
                billsName = "销售订单";
                params = new TemplateExportParams("SAP-purchase/src/main/resources/static/purchaseOrder-template.xlsx");
            } else if (type == 2) {
                billsName = "销售物料出货单";
                params = new TemplateExportParams("SAP-purchase/src/main/resources/static/purchaseOrder-template.xlsx");
            } else {
                billsName = "销售物料退货单";
                params = new TemplateExportParams("SAP-purchase/src/main/resources/static/purchaseOrder-template.xlsx");
            }

            HashMap<String,Object> dataMap = saleService.excelExportSaleBillsInformation(id);
            Workbook workbook = ExcelExportUtil.exportExcel(params, dataMap);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode( billsName + ".xlsx", "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new CostomException(20004,"导出销售出现异常！！！" + e.getMessage());
        }
    }


}
