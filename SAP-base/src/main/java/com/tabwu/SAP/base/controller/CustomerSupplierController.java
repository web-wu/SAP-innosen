package com.tabwu.SAP.base.controller;

import com.tabwu.SAP.base.entity.CustomerSupplier;
import com.tabwu.SAP.base.entity.vo.CustomerSupplierVo;
import com.tabwu.SAP.base.service.ICustomerSupplierService;
import com.tabwu.SAP.common.entity.R;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author tabwu
 * @since 2022-06-19
 */
@RestController
@RequestMapping("/base/customer-supplier")
public class CustomerSupplierController {
    @Autowired
    private ICustomerSupplierService customerSupplierService;


    @PostMapping("/add")
    @ApiOperation(value = "添加客户-供应商")
    public R addCustomerSupplier(@ApiParam(name = "customerSupplier",value = "customerSupplier",required = true)
                      @RequestBody CustomerSupplier customerSupplier) {
        customerSupplierService.save(customerSupplier);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除客户-供应商")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        customerSupplierService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改客户-供应商")
    public R updateById(@ApiParam(name = "customerSupplier",value = "customerSupplier",required = true)
                        @RequestBody CustomerSupplier customerSupplier) {
        customerSupplierService.updateById(customerSupplier);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询客户-供应商")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        CustomerSupplier customerSupplier = customerSupplierService.getById(id);
        return R.ok().data("customerSupplier",customerSupplier);
    }


    @PostMapping("/queryPage/{current}/{size}")
    @ApiOperation(value = "分页查询所有客户-供应商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "current",required = true),
            @ApiImplicitParam(name = "size",value = "size",required = true),
            @ApiImplicitParam(name = "customerSupplierVo",value = "customerSupplierVo")
    })
    public R queryPage(@PathVariable int current, @PathVariable int size, @RequestBody CustomerSupplierVo customerSupplierVo) {
        Map<String,Object> map = customerSupplierService.queryPage(current,size,customerSupplierVo);
        return R.ok().data(map);
    }
}
