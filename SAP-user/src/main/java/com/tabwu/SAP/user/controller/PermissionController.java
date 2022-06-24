package com.tabwu.SAP.user.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.user.entity.Permission;
import com.tabwu.SAP.user.service.IPermissionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;


/**
 * @author tabwu
 * @since 2022-06-23
 */
@RestController
@RequestMapping("/user/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private Executor executor;


    @GetMapping("/export")
    @ApiOperation(value = "excel批量导出菜单权限")
    public void exportExcel(HttpServletResponse response) {
        try {
            List<Permission> list = permissionService.list();
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("菜单权限报表", "permission"),Permission.class,list);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode( "菜单权限报表.xlsx", "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new CostomException(20004,"excel批量导出菜单权限发送异常");
        }
    }

    @PostMapping("/import")
    @ApiOperation(value = "excel批量导入菜单权限")
    public R importExcel(MultipartFile file) {
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<Permission> permissionList = ExcelImportUtil.importExcel(file.getInputStream(), Permission.class, params);
            CountDownLatch latch = new CountDownLatch(permissionList.size());
            for (Permission permission : permissionList) {
                executor.execute(() -> {
                    permissionService.saveOrUpdate(permission,new UpdateWrapper<Permission>().eq("name",permission.getName()));
                });
                latch.countDown();
            }
            latch.await();
            return R.ok();
        } catch (Exception e) {
            throw new CostomException(20004,"excel批量导入菜单权限发送异常");
        }
    }


    @PostMapping("/add")
    @ApiOperation(value = "添加菜单权限")
    public R addUser(@ApiParam(name = "permission",value = "permission",required = true)
                     @RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除菜单权限")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        permissionService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改菜单权限")
    public R updateById(@ApiParam(name = "permission",value = "permission",required = true)
                            @RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询菜单权限")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        Permission permission = permissionService.getById(id);
        return R.ok().data("permission",permission);
    }


    @GetMapping("/tree")
    @ApiOperation(value = "根据id查询菜单权限,以tree形式返回")
    public R findTree() {
        Permission permission = permissionService.findTree();
        return R.ok().data("permission",permission);
    }

}
