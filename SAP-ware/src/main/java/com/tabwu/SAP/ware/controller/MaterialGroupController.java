package com.tabwu.SAP.ware.controller;


import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.ware.entity.MaterialGroup;
import com.tabwu.SAP.ware.service.IMaterialGroupService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tabwu
 * @since 2022-06-11
 */
@RestController
@RequestMapping("/ware/material-group")
public class MaterialGroupController {

    @Autowired
    private IMaterialGroupService materialGroupService;

    @PostMapping("/add")
    @ApiOperation(value = "添加物料组")
    public R addMeterialGroup(@ApiParam(name = "materialGroup",value = "materialGroup",required = true)
                                  @RequestBody MaterialGroup materialGroup) {
        materialGroupService.save(materialGroup);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除物料组")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                            @PathVariable("id") String id) {
        materialGroupService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改物料组")
    public R updateById(@ApiParam(name = "materialGroup",value = "materialGroup",required = true)
                            @RequestBody MaterialGroup materialGroup) {
        materialGroupService.updateById(materialGroup);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询物料组")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                             @PathVariable("id") String id) {
        MaterialGroup materialGroup = materialGroupService.getById(id);
        return R.ok().data("materialGroup",materialGroup);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询所有物料组列表")
    public R list() {
        List<MaterialGroup> list = materialGroupService.list();
        return R.ok().data("list",list);
    }
}
