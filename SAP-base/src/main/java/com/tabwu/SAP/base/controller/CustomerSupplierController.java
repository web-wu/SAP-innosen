package com.tabwu.SAP.base.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tabwu.SAP.base.entity.CustomerSupplier;
import com.tabwu.SAP.base.entity.To.CostomerSupplierTo;
import com.tabwu.SAP.base.entity.vo.CustomerSupplierVo;
import com.tabwu.SAP.base.service.ICustomerSupplierService;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * @author tabwu
 * @since 2022-06-19
 */
@RestController
@RequestMapping("/base/customer-supplier")
public class CustomerSupplierController {
    @Autowired
    private ICustomerSupplierService customerSupplierService;
    @Autowired
    private Executor executor;

    @GetMapping("/excel")
    @ApiOperation(value = "excel批量导出客户-供应商")
    public void exportExcel(HttpServletResponse response) {
        try {
            List<CustomerSupplier> list = customerSupplierService.list();
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("客户-供应商报表", "customerSupplier"), CustomerSupplier.class, list);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode( "customer-supplier.xlsx", "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new CostomException(20004,"excel批量导出客户-供应商发送异常");
        }
    }

    @PostMapping("/import")
    @ApiOperation(value = "excel批量导入客户-供应商")
    public R importExcel(@ApiParam(name = "file",value = "excel文件",required = true)
                         MultipartFile file) {
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<CustomerSupplier> list = ExcelImportUtil.importExcel(file.getInputStream(), CustomerSupplier.class, params);
            CountDownLatch latch = new CountDownLatch(list.size());
            for (CustomerSupplier customerSupplier : list) {
                executor.execute(() -> {
                    customerSupplierService.save(customerSupplier);
                });
                latch.countDown();
            }
            latch.await();
            return R.ok();
        } catch (Exception e) {
            throw new CostomException(20004,"excel批量添加客户-供应商发送异常");
        }
    }

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

    @GetMapping("/findCompany/{company}")
    @ApiOperation(value = "根据公司名称查询客户-供应商")
    public R findCostomerSupplierByCompanyName(@ApiParam(name = "company",value = "公司名称",required = true)
                         @PathVariable("company") String company) {
        CustomerSupplier customerSupplier = customerSupplierService.getOne(new QueryWrapper<CustomerSupplier>().eq("company_name",company));
        CostomerSupplierTo costomerSupplierTo = new CostomerSupplierTo();
        BeanUtils.copyProperties(customerSupplier,costomerSupplierTo);
        return R.ok().data("customerSupplier",costomerSupplierTo);
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
