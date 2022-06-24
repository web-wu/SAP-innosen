package com.tabwu.SAP.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.user.entity.Role;
import com.tabwu.SAP.user.entity.UserRole;
import com.tabwu.SAP.user.service.IRoleService;
import com.tabwu.SAP.user.service.IUserRoleService;
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
@RequestMapping("/user/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserRoleService userRoleService;

    @PostMapping("/add")
    @ApiOperation(value = "添加角色")
    public R addRole(@ApiParam(name = "role",value = "role",required = true)
                     @RequestBody Role role) {
        roleService.save(role);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除角色,该角色被用户关联时不能删除")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        long count = userRoleService.count(new QueryWrapper<UserRole>().eq("rid",id));
        if (count > 0) {
            throw new CostomException(20004,"该角色被已用户关联，不能删除");
        }
        roleService.removeById(id);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改角色")
    public R updateById(@ApiParam(name = "role",value = "role",required = true)
                        @RequestBody Role role) {
        roleService.updateById(role);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询角色")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        Role role = roleService.getById(id);
        return R.ok().data("role",role);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询所有角色")
    public R list() {
        List<Role> list = roleService.list();
        return R.ok().data("list",list);
    }
}
