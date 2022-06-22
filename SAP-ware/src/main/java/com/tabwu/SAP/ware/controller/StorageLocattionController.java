package com.tabwu.SAP.ware.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.ware.entity.MaterialUnit;
import com.tabwu.SAP.ware.entity.StorageLocattion;
import com.tabwu.SAP.ware.entity.WareStorage;
import com.tabwu.SAP.ware.entity.vo.StorageLocationVo;
import com.tabwu.SAP.ware.service.IStorageLocattionService;
import com.tabwu.SAP.ware.service.IWareStorageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tabwu
 * @since 2022-06-11
 */
@RestController
@RequestMapping("/ware/storage-locattion")
public class StorageLocattionController {
    @Autowired
    private IStorageLocattionService storageLocattionService;
    @Autowired
    private IWareStorageService wareStorageService;

    @PostMapping("/add")
    @ApiOperation(value = "添加库位")
    public R add(@ApiParam(name = "storageLocattionVo",value = "storageLocattionVo",required = true)
                     @RequestBody StorageLocationVo storageLocattionVo) {
        StorageLocattion storageLocattion = new StorageLocattion();
        storageLocattion.setCode(storageLocattionVo.getCode());
        boolean save = storageLocattionService.save(storageLocattion);
        if (save) {
            WareStorage wareStorage = new WareStorage();
            wareStorage.setWid(storageLocattionVo.getWid());
            wareStorage.setLid(storageLocattion.getId());
            wareStorageService.save(wareStorage);
        }
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除库位")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        boolean save = storageLocattionService.removeById(id);
        if (save) {
            wareStorageService.remove(new QueryWrapper<WareStorage>().eq("lid",id));
        }
        return R.ok();
    }

    @PostMapping("/deleteBatchByIds")
    @ApiOperation(value = "根据id批量删除库位")
    public R deleteBatchByIds(@ApiParam(name = "ids",value = "ids",required = true)
                                  @RequestBody List<String> ids) {
        boolean save = storageLocattionService.removeBatchByIds(ids);
        if (save) {
            for (String id : ids) {
                wareStorageService.remove(new QueryWrapper<WareStorage>().eq("lid",id));
            }
        }
        return R.ok();
    }


    @PutMapping("/update")
    @ApiOperation(value = "修改库位")
    public R updateById(@ApiParam(name = "StorageLocattion",value = "StorageLocattion",required = true)
                            @RequestBody StorageLocattion StorageLocattion) {
        storageLocattionService.updateById(StorageLocattion);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询库位")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        StorageLocattion storageLocattion = storageLocattionService.getById(id);
        return R.ok().data("storageLocattion",storageLocattion);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询所有库位")
    public R list() {
        List<StorageLocattion> list = storageLocattionService.list();
        return R.ok().data("list",list);
    }


    @GetMapping("/findLocations/{wid}")
    @ApiOperation(value = "根据仓库id查询所有库位")
    public R findStorageLocationList(@ApiParam(name = "wid",value = "wid",required = true)
                                     @PathVariable("wid") String wid) {
        List<WareStorage> list = wareStorageService.list(new QueryWrapper<WareStorage>().eq("wid", wid));
        List<String> lids = list.stream().map(item -> item.getLid()).collect(Collectors.toList());
        List<StorageLocattion> storageLocattions = storageLocattionService.listByIds(lids);
        return R.ok().data("storageLocattions",storageLocattions);
    }
}
