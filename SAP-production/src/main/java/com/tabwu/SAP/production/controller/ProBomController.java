package com.tabwu.SAP.production.controller;


import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.production.entity.ProBom;
import com.tabwu.SAP.production.entity.vo.BomVo;
import com.tabwu.SAP.production.service.IProBomService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@RestController
@RequestMapping("/production/pro-bom")
public class ProBomController {
    @Autowired
    private IProBomService proBomService;

    @PostMapping("/add")
    @ApiOperation(value = "添加物料BOM")
    public R add(@ApiParam(name = "bomVo",value = "bomVo",required = true)
                     @RequestBody BomVo bomVo) {
        boolean flag = proBomService.addBom(bomVo);
        return flag ? R.ok() : R.error();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除物料BOM")
    public R delete(@ApiParam(name = "id",value = "id",required = true)
                 @PathVariable("id") String id) {
        proBomService.removeById(id);
        return R.ok();
    }


    @PostMapping("/deleteBatch")
    @ApiOperation(value = "根据id批量删除物料BOM")
    public R deleteBatch(@ApiParam(name = "ids",value = "ids",required = true)
                    @RequestParam("ids") List<String> ids) {
        proBomService.removeBatchByIds(ids);
        return R.ok();
    }


    @PutMapping("/update")
    @ApiOperation(value = "根据id修改物料BOM")
    public R update(@ApiParam(name = "bomVo",value = "bomVo",required = true)
                        @RequestBody BomVo bomVo) {
        boolean flag =  proBomService.updateBom(bomVo);
        return flag ? R.ok() : R.error();
    }


    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查找物料BOM")
    public R findOne(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        BomVo bomVo =  proBomService.findOne(id);
        return R.ok().data("bomVo",bomVo);
    }


    @GetMapping("/findList")
    @ApiOperation(value = "查找所有物料BOM")
    public R findList() {
        List<ProBom> proBoms =  proBomService.findList();
        return R.ok().data("proBoms",proBoms);
    }
}
