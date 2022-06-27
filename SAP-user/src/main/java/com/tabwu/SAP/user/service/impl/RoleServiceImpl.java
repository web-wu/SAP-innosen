package com.tabwu.SAP.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tabwu.SAP.user.entity.Role;
import com.tabwu.SAP.user.entity.RolePermission;
import com.tabwu.SAP.user.mapper.RoleMapper;
import com.tabwu.SAP.user.service.IRolePermissionService;
import com.tabwu.SAP.user.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Override
    public void distributePermissionforUser(String rid, String[] pids) {
        for (String pid : pids) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRid(rid);
            rolePermission.setPid(pid);
            rolePermissionService.saveOrUpdate(rolePermission,new UpdateWrapper<RolePermission>().eq("rid",rid).eq("pid",pid));
        }
    }
}
