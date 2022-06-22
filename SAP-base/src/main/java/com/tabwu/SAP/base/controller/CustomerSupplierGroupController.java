package com.tabwu.SAP.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tabwu.SAP.base.entity.CustomerSupplier;
import com.tabwu.SAP.base.entity.CustomerSupplierGroup;
import com.tabwu.SAP.base.service.ICustomerSupplierGroupService;
import com.tabwu.SAP.base.service.ICustomerSupplierService;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tabwu
 * @since 2022-06-19
 */
@RestController
@RequestMapping("/base/customer-supplier-group")
public class CustomerSupplierGroupController {
    @Autowired
    private ICustomerSupplierGroupService customerSupplierGroupService;
    @Autowired
    private ICustomerSupplierService customerSupplierService;


    @PostMapping("/add")
    @ApiOperation(value = "添加客户-供应商组")
    public R addGroup(@ApiParam(name = "customerSupplierGroup",value = "customerSupplierGroup",required = true)
                     @RequestBody CustomerSupplierGroup customerSupplierGroup) {
        customerSupplierGroupService.save(customerSupplierGroup);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除客户-供应商组,组下存在客户-供应商时，不能删除")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        long count = customerSupplierService.count(new QueryWrapper<CustomerSupplier>().eq("group_id", id));
        if (count > 0) {
            throw new CostomException(20004,"该组下存在维护的数据，不能删除");
        }
        customerSupplierGroupService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改客户-供应商组")
    public R updateById(@ApiParam(name = "customerSupplierGroup",value = "customerSupplierGroup",required = true)
                        @RequestBody CustomerSupplierGroup customerSupplierGroup) {
        customerSupplierGroupService.updateById(customerSupplierGroup);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询客户-供应商组")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        CustomerSupplierGroup customerSupplierGroup = customerSupplierGroupService.getById(id);
        return R.ok().data("customerSupplierGroup",customerSupplierGroup);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询所有客户-供应商组")
    public R list() {
        List<CustomerSupplierGroup> list = customerSupplierGroupService.list();
        return R.ok().data("list",list);
    }
}
