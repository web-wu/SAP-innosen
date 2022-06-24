package com.tabwu.SAP.user.service;

import com.tabwu.SAP.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-23
 */
public interface IUserService extends IService<User> {

    User findUserById(String id);
}
