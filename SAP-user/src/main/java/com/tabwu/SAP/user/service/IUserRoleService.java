package com.tabwu.SAP.user.service;

import com.tabwu.SAP.user.entity.Permission;
import com.tabwu.SAP.user.entity.Role;
import com.tabwu.SAP.user.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author tabwu
 * @since 2022-06-23
 */
public interface IUserRoleService extends IService<UserRole> {

    Permission findPermissionByUserId(String uid);

    List<Role> findRolesByUserId(String id);
}
