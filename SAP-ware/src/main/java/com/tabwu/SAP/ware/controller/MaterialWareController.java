package com.tabwu.SAP.ware.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.common.utils.PageUtil;
import com.tabwu.SAP.ware.entity.MaterialWare;
import com.tabwu.SAP.ware.entity.Ware;
import com.tabwu.SAP.ware.entity.WareStorage;
import com.tabwu.SAP.ware.entity.to.WareStockTo;
import com.tabwu.SAP.ware.entity.vo.MaterialWareVo;
import com.tabwu.SAP.ware.service.IMaterialWareService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * 物料入库
 * @author tabwu
 * @since 2022-06-19
 */
@RestController
@RequestMapping("/ware/material-ware")
public class MaterialWareController {
    @Autowired
    private IMaterialWareService materialWareService;
    @Autowired
    private Executor executor;

    @GetMapping("/excelExport")
    @ApiOperation(value = "excel批量导出库存物料")
    public void excelExportWare(HttpServletResponse response) {
        try {
            List<MaterialWare> list = materialWareService.list();
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("库存物料表", "wareMaterial"), MaterialWare.class, list);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode( "库存物料报表.xlsx", "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new CostomException(20004,"excel批量导出库存物料时出现异常！！！");
        }
    }

    @PostMapping("/excelImport")
    @ApiOperation(value = "excel批量导入物料入库")
    public R excelImportWare(@ApiParam(name = "file",value = "file",required = true)
                     @RequestBody MultipartFile file) {
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<MaterialWare> list = ExcelImportUtil.importExcel(file.getInputStream(), MaterialWare.class, params);
            CountDownLatch latch = new CountDownLatch(list.size());
            for (MaterialWare materialWare : list) {
                executor.execute(() -> {
                    materialImportWare(materialWare);
                });
                latch.countDown();
            }
            latch.await();
            return R.ok();
        } catch (Exception e) {
            throw new CostomException(20004,"excel批量导入物料入库时出现异常！！！");
        }
    }


    @PostMapping("/materialImport")
    @ApiOperation(value = "物料入库")
    public R addWare(@ApiParam(name = "materialWare",value = "materialWare",required = true)
                     @RequestBody MaterialWare materialWare) {
        materialImportWare(materialWare);
        return R.ok();
    }

    private void materialImportWare(MaterialWare materialWare) {
        //1、根据物料code、仓库id、库位id、物料批号查询库存中是否存在该物料
        MaterialWare materialStock = materialWareService.getOne(new QueryWrapper<MaterialWare>().eq("mid", materialWare.getMid()).eq("wid", materialWare.getWid()).eq("lid", materialWare.getLid()).eq("lot",materialWare.getLot()));
        if (materialStock == null) {
            // 2、库存中没有该物料时直接存入库存，注意：物料入库时必须精确到库位
            materialWareService.save(materialWare);
        } else {
            // 3、 但库存中有该物料时且批号相同，直接增加该物料的数量，注意：同一物料在同一库位中可以存在多个批号
            int num = materialStock.getStock() + materialWare.getStock();
            materialStock.setStock(num);
            materialWareService.updateById(materialStock);
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "当物料数量等于0时，根据库存id删除该物料在库存中的痕迹")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        MaterialWare materialWare = materialWareService.getById(id);
        if (materialWare.getStock() > 0) {
            throw new CostomException(20004,"该物料在库存中数量大于0，不能删除！！！");
        }
        materialWareService.removeById(id);
        return R.ok();
    }


    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询物料库存")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        MaterialWare materialWare = materialWareService.getById(id);
        return R.ok().data("materialWare",materialWare);
    }


    @GetMapping("/list")
    @ApiOperation(value = "查询所有物料库存")
    public R list() {
        List<MaterialWare> list = materialWareService.list();
        return R.ok().data("list",list);
    }


    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation(value = "分页查询物料库存")
    public R listPage(@PathVariable("current") Integer current, @PathVariable("size") Integer size, @RequestBody(required = false) MaterialWareVo materialWareVo) {
        Page<MaterialWare> page = new Page<MaterialWare>(current, size);

        QueryWrapper<MaterialWare> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(materialWareVo.getMid())) {
            wrapper.eq("mid",materialWareVo.getMid());
        }
        if (materialWareVo.getMtype() != null) {
            wrapper.eq("m_type",materialWareVo.getMtype());
        }
        if (!StringUtils.isEmpty(materialWareVo.getLot())) {
            wrapper.eq("lot",materialWareVo.getLot());
        }
        if (!StringUtils.isEmpty(materialWareVo.getWid())) {
            wrapper.eq("wid",materialWareVo.getWid());
        }
        if (!StringUtils.isEmpty(materialWareVo.getLid())) {
            wrapper.eq("lid",materialWareVo.getLid());
        }
        if (materialWareVo.getStockLocked() != null) {
            wrapper.eq("stock_locked",materialWareVo.getStockLocked());
        }
        materialWareService.page(page,wrapper);
        Map<String, Object> map = PageUtil.queryPage(page);
        return R.ok().data("page",map);
    }


    @PostMapping("/checkStock")
    @ApiOperation(value = "根据物料条码、类型、仓库、库位。批号检查仓库中的物料数量")
    public R checkStockByMcode(@ApiParam(name = "WareStockTo",value = "WareStockTo",required = true)
                                   @RequestBody WareStockTo wareStockTo) {
        Integer num = materialWareService.checkStockByMcode(wareStockTo);
        return R.ok().data("num",num);
    }


    @PostMapping("/reduceStock")
    @ApiOperation(value = "根据物料条码、类型、仓库、库位。批号扣减仓库中的物料数量")
    public R reduceWareStockByCondition(@ApiParam(name = "wareStockTos",value = "wareStockTos",required = true)
                                            @RequestBody List<WareStockTo> wareStockTos) {
        boolean flag = materialWareService.reduceWareStockByCondition(wareStockTos);
        return flag ? R.ok() : R.error();
    }
}

