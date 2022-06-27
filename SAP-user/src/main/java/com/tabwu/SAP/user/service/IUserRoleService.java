package com.tabwu.SAP.user.service;

import com.tabwu.SAP.user.entity.Permission;
import com.tabwu.SAP.user.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author tabwu
 * @since 2022-06-23
 */
public interface IUserRoleService extends IService<UserRole> {

    Permission findPermissionByUserId(String uid);
}
