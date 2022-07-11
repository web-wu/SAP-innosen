package com.tabwu.SAP.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tabwu.SAP.user.entity.Permission;
import com.tabwu.SAP.user.entity.RolePermission;
import com.tabwu.SAP.user.entity.UserRole;
import com.tabwu.SAP.user.mapper.UserRoleMapper;
import com.tabwu.SAP.user.service.IRolePermissionService;
import com.tabwu.SAP.user.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    private PermissionServiceImpl permissionService;
    @Autowired
    private IRolePermissionService rolePermissionService;
    @Autowired
    private Executor executor;

    @Override
    @Cacheable(cacheNames = "permission",key = "#uid")
    public Permission findPermissionByUserId(String uid) {

        List<Permission> permissionList = permissionService.list();
        UserRole userRole = baseMapper.selectOne(new QueryWrapper<UserRole>().eq("uid", uid));
        List<RolePermission> list = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("rid", userRole.getRid()));
        for (Permission permission : permissionList) {
            for (RolePermission rolePermission : list) {
                if (rolePermission.getPid().equals(permission.getId())) {
                    permission.setSelected(1);
                }
            }
        }
        return permissionService.findChildrens(permissionList);
    }
}
