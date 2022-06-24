package com.tabwu.SAP.user.service;

import com.tabwu.SAP.user.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-23
 */
public interface IPermissionService extends IService<Permission> {

    Permission findTree();
}
