package com.tabwu.SAP.sale.controller;


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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

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

}
