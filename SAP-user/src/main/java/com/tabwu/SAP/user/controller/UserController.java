package com.tabwu.SAP.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tabwu.SAP.common.entity.LoginUser;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.utils.JwtUtils;
import com.tabwu.SAP.user.entity.Permission;
import com.tabwu.SAP.user.entity.To.UserTo;
import com.tabwu.SAP.user.entity.User;
import com.tabwu.SAP.user.entity.UserRole;
import com.tabwu.SAP.user.service.IRoleService;
import com.tabwu.SAP.user.service.IUserRoleService;
import com.tabwu.SAP.user.service.IUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/add")
    @ApiOperation(value = "添加用户")
    public R addUser(@ApiParam(name = "user",value = "user",required = true)
                     @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除用户")
    public R deleteById(@ApiParam(name = "id",value = "id",required = true)
                        @PathVariable("id") String id) {
        userService.removeById(id);
        userRoleService.remove(new QueryWrapper<UserRole>().eq("uid",id));
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改用户")
    public R updateById(@ApiParam(name = "user",value = "user",required = true)
                            @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateById(user);
        return R.ok();
    }

    @GetMapping("/findOne/{id}")
    @ApiOperation(value = "根据id查询用户")
    public R findOneById(@ApiParam(name = "id",value = "id",required = true)
                         @PathVariable("id") String id) {
        User user = userService.findUserById(id);
        return R.ok().data("user",user);
    }


    @GetMapping("/findUser/{username}")
    @ApiOperation(value = "根据用户名查询用户")
    public R findUserByUsername(@ApiParam(name = "username",value = "username",required = true)
                         @PathVariable("username") String username) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username",username));
        UserTo userTo = new UserTo();
        BeanUtils.copyProperties(user,userTo);
        return R.ok().data("user",userTo);
    }


    @GetMapping("/list")
    @ApiOperation(value = "查询所有用户")
    public R list(HttpServletRequest request) {
        List<User> list = userService.list();
        return R.ok().data("list",list);
    }


    @GetMapping("/distribute/{uid}/{rid}")
    @ApiOperation(value = "为用户分配角色")
    public R distributeRole(@PathVariable("uid") String uid,@PathVariable("rid") String rid) {
        UserRole userRole = new UserRole();
        userRole.setUid(uid);
        userRole.setRid(rid);
        userRoleService.saveOrUpdate(userRole,new UpdateWrapper<UserRole>().eq("uid",uid).eq("rid",rid));
        return R.ok();
    }


    @GetMapping("/findPermission/{uid}")
    @ApiOperation(value = "根据用户id查询菜单权限")
    public R findPermissionByUserId(@PathVariable("uid") String uid) {
        Permission permission = userRoleService.findPermissionByUserId(uid);
        return R.ok().data("permission",permission);
    }

}
