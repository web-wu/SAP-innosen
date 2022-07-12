package com.tabwu.SAP.production.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.production.entity.Production;
import com.tabwu.SAP.production.entity.ProductionItem;
import com.tabwu.SAP.production.entity.vo.ProductionVo;
import com.tabwu.SAP.production.service.IProductionItemService;
import com.tabwu.SAP.production.service.IProductionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@RestController
@RequestMapping("/production/production")
public class ProductionController {
    @Autowired
    private IProductionService productionService;
    @Autowired
    private IProductionItemService productionItemService;

    @PostMapping("/add")
    @ApiOperation("添加生产单据")
    public R add(@ApiParam(name = "production",value = "production",required = true)
                     @RequestBody Production production) {
        productionService.addProductionBills(production);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除生产单据")
    public R delete(@ApiParam(name = "id",value = "id",required = true)
                 @PathVariable("id") String id) {
        Production production = productionService.getById(id);
        if (production == null) {
            throw new CostomException(20004,"id为：" + id + "的单据不存在");
        }
        productionService.removeById(id);
        productionItemService.removeBatchByIds(production.getProductionItems());
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation("根据id查询单据")
    public R findOne(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        Production production = productionService.getById(id);
        List<ProductionItem> productionItems = productionItemService.list(new QueryWrapper<ProductionItem>().eq("pcode", production.getCode()));
        production.setProductionItems(productionItems);
        return R.ok().data("production",production);
    }

    @PostMapping("/findPage")
    @ApiOperation("分页查询单据")
    public R findPage(@RequestBody ProductionVo productionVo) {
        HashMap<String,Object> page = productionService.queryPage(productionVo);
        return R.ok().data("page",page);
    }


    @GetMapping("/changeStatus/{id}/{status}")
    @ApiOperation("根据取料单id确认取料成功状态和修改生产订单状态")
    public R changeProductionBillsStatusById(@PathVariable("id") String id,@PathVariable("status") Integer status) {
        productionService.changeProductionBillsStatus(id,status);
        return R.ok();
    }


    @GetMapping("/modifyStatus/{id}")
    @ApiOperation("根据收货单id确认收货成功状态和修改生产订单状态")
    public R changeMaterialInputBillsStatusById(@PathVariable("id") String id,@PathVariable("status") Integer status) {
        productionService.changeMaterialInputBillsStatus(id,status);
        return R.ok();
    }

}
