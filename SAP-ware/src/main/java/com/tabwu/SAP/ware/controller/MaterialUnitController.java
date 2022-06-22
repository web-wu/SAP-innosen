package com.tabwu.SAP.ware.controller;


import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.ware.entity.MaterialType;
import com.tabwu.SAP.ware.entity.MaterialUnit;
import com.tabwu.SAP.ware.service.IMaterialUnitService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tabwu
 * @since 2022-06-11
 */
@RestController
@RequestMapping("/ware/material-unit")
public class MaterialUnitController {

    @Autowired
    private IMaterialUnitService materialUnitService;

    @PostMapping("/add")
    @ApiOperation(value = "添加物料单位")
    public R add(@ApiParam(name = "materialUnit",value = "materialUnit",required = true)
                             @RequestBody MaterialUnit materialUnit) {
        materialUnitService.save(materialUnit);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除物料单位")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        materialUnitService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改物料单位")
    public R updateById(@ApiParam(name = "materialUnit",value = "materialUnit",required = true)
                            @RequestBody MaterialUnit materialUnit) {
        materialUnitService.updateById(materialUnit);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询物料单位")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        MaterialUnit materialUnit = materialUnitService.getById(id);
        return R.ok().data("materialUnit",materialUnit);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询所有物料单位")
    public R list() {
        List<MaterialUnit> list = materialUnitService.list();
        return R.ok().data("list",list);
    }
}
