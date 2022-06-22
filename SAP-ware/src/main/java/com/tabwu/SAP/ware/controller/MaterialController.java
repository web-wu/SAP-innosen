package com.tabwu.SAP.ware.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.ware.entity.Material;
import com.tabwu.SAP.ware.entity.MaterialWare;
import com.tabwu.SAP.ware.entity.vo.MaterialVo;
import com.tabwu.SAP.ware.service.IMaterialService;
import com.tabwu.SAP.ware.service.IMaterialWareService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author tabwu
 * @since 2022-06-11
 */
@RestController
@RequestMapping("/ware/material")
public class MaterialController {
    @Autowired
    private IMaterialService materialService;
    @Autowired
    private IMaterialWareService materialWareService;

    @PostMapping("/add")
    @ApiOperation(value = "添加物料")
    public R addMaterial(@ApiParam(name = "Material",value = "Material",required = true)
                     @RequestBody Material Material) {
        materialService.save(Material);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除物料,如果仓库中有该物料，则不能被删除")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        long count = materialWareService.count(new QueryWrapper<MaterialWare>().eq("mid", id));
        if (count > 0) throw  new CostomException(2004,"当前物料在库存中存在，不能删除");
        materialService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改物料")
    public R updateById(@ApiParam(name = "Material",value = "Material",required = true)
                            @RequestBody Material Material) {
        materialService.updateById(Material);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询物料")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        Material material = materialService.getById(id);
        return R.ok().data("material",material);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询所有物料")
    public R list() {
        List<Material> list = materialService.list();
        return R.ok().data("list",list);
    }

    @PostMapping("/queryPage/{current}/{size}")
    @ApiOperation(value = "分页查询所有物料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "current",required = true),
            @ApiImplicitParam(name = "size",value = "size",required = true),
            @ApiImplicitParam(name = "materialVo",value = "materialVo")
    })
    public R queryPage(@PathVariable int current, @PathVariable int size, @RequestBody MaterialVo materialVo) {
        Map<String,Object> map = materialService.queryPage(current,size,materialVo);
        return R.ok().data(map);
    }
}
