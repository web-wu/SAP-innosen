package com.tabwu.SAP.production.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.production.entity.ProBom;
import com.tabwu.SAP.production.entity.vo.BomVo;
import com.tabwu.SAP.production.service.IProBomService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * @author tabwu
 * @since 2022-07-11
 */
@RestController
@RequestMapping("/production/pro-bom")
public class ProBomController {
    @Autowired
    private IProBomService proBomService;
    @Autowired
    private Executor executor;


    @PostMapping("/importBom")
    @ApiOperation(value = "excel批量导入BOM关系表")
    public R excelImportBom(@ApiParam(name = "file",value = "excel文件",required = true)
                                    MultipartFile file) {
        try {
            ImportParams params = new ImportParams();
            params.setHeadRows(1);
            params.setTitleRows(1);
            List<ProBom> bomList = ExcelImportUtil.importExcel(file.getInputStream(), ProBom.class, params);
            CountDownLatch latch = new CountDownLatch(bomList.size());
            for (ProBom bom : bomList) {
                executor.execute(() -> {
                    proBomService.save(bom);
                });
                latch.countDown();
            }
            latch.await();
            return R.ok();
        } catch (Exception e) {
            throw new CostomException(20004,"excel批量导入BOM关系表时发生异常");
        }

    }


    @GetMapping("/exportBom")
    @ApiOperation(value = "excel批量导出BOM关系表")
    public void excelExportBom(HttpServletResponse response) {
        try {
            List<ProBom> list = proBomService.list();
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("物料生产BOM关系表", "peoBom"), ProBom.class, list);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode( "物料生产BOM关系表.xlsx", "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new CostomException(20004,e.getMessage());
        }
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加物料BOM")
    public R add(@ApiParam(name = "proBom",value = "proBom",required = true)
                     @RequestBody ProBom proBom) {
        proBomService.save(proBom);
        return R.ok();
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
    public R update(@ApiParam(name = "proBom",value = "proBom",required = true)
                        @RequestBody ProBom proBom) {
        boolean flag =  proBomService.updateById(proBom);
        return flag ? R.ok() : R.error();
    }

    @GetMapping("/findBom/{code}")
    @ApiOperation(value = "根据子物料code查找物料BOM")
    public R findBomByCode(@ApiParam(name = "code",value = "code",required = true)
                     @PathVariable("code") String code) {
        BomVo bomVo =  proBomService.findBomByCode(code);
        return R.ok().data("bomVo",bomVo);
    }



    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查找物料BOM")
    public R findOne(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        ProBom proBom = proBomService.getById(id);
        return R.ok().data("proBom",proBom);
    }


    @GetMapping("/findList")
    @ApiOperation(value = "查找所有物料BOM")
    public R findList() {
        List<ProBom> proBoms =  proBomService.list(null);;
        return R.ok().data("proBoms",proBoms);
    }
}
