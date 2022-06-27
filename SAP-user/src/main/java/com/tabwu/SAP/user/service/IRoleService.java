package com.tabwu.SAP.user.service;

import com.tabwu.SAP.user.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author tabwu
 * @since 2022-06-23
 */
public interface IRoleService extends IService<Role> {

    void distributePermissionforUser(String rid, String[] pids);
}

