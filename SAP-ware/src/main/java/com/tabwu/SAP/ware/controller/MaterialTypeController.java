package com.tabwu.SAP.ware.controller;


import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.ware.entity.MaterialGroup;
import com.tabwu.SAP.ware.entity.MaterialType;
import com.tabwu.SAP.ware.service.IMaterialTypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author tabwu
 * @since 2022-06-11
 */
@RestController
@RequestMapping("/ware/material-type")
public class MaterialTypeController {
    @Autowired
    private IMaterialTypeService materialTypeService;

    @PostMapping("/add")
    @ApiOperation(value = "添加物料类型")
    public R addMeterialType(@ApiParam(name = "materialType",value = "materialType",required = true)
                              @RequestBody MaterialType materialType) {
        materialTypeService.save(materialType);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除物料类型")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        materialTypeService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改物料类型")
    public R updateById(@ApiParam(name = "materialType",value = "materialType",required = true)
                        @RequestBody MaterialType materialType) {
        materialTypeService.updateById(materialType);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询物料类型")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        MaterialType materialType = materialTypeService.getById(id);
        return R.ok().data("materialType",materialType);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询所有物料物料类型")
    public R list() {
        List<MaterialType> list = materialTypeService.list();
        return R.ok().data("list",list);
    }
}
