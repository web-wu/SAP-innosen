package com.tabwu.SAP.base.controller;


import com.tabwu.SAP.base.entity.Department;
import com.tabwu.SAP.base.service.IDepartmentService;
import com.tabwu.SAP.common.entity.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@RestController
@RequestMapping("/base/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @PostMapping("/add")
    @ApiOperation(value = "添加部门")
    public R addDepartment(@ApiParam(name = "department",value = "department",required = true)
                     @RequestBody Department department) {
        departmentService.save(department);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除部门")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        departmentService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改部门")
    public R updateById(@ApiParam(name = "department",value = "department",required = true)
                            @RequestBody Department department) {
        departmentService.updateById(department);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询部门")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        Department department = departmentService.getById(id);
        return R.ok().data("department",department);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询所有部门")
    public R list() {
        List<Department> list = departmentService.list();
        return R.ok().data("list",list);
    }

}
