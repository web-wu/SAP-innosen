package com.tabwu.SAP.ware.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.ware.entity.MaterialType;
import com.tabwu.SAP.ware.entity.Ware;
import com.tabwu.SAP.ware.entity.WareStorage;
import com.tabwu.SAP.ware.service.IWareService;
import com.tabwu.SAP.ware.service.IWareStorageService;
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
@RequestMapping("/ware/ware")
public class WareController {
    @Autowired
    private IWareService wareService;
    @Autowired
    private IWareStorageService wareStorageService;

    @PostMapping("/add")
    @ApiOperation(value = "添加仓库")
    public R addWare(@ApiParam(name = "ware",value = "ware",required = true)
                             @RequestBody Ware ware) {
        wareService.save(ware);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除仓库,同时将删除该仓库下关联的所有库位")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        boolean save = wareService.removeById(id);
        if (save) {
            wareStorageService.remove(new QueryWrapper<WareStorage>().eq("wid",id));
        }
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改仓库")
    public R updateById(@ApiParam(name = "ware",value = "ware",required = true)
                            @RequestBody Ware ware) {
        wareService.updateById(ware);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询仓库")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        Ware ware = wareService.getById(id);
        return R.ok().data("ware",ware);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询所有仓库")
    public R list() {
        List<Ware> list = wareService.list();
        return R.ok().data("list",list);
    }

}
