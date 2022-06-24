package com.tabwu.SAP.user.service.impl;

import com.tabwu.SAP.user.entity.User;
import com.tabwu.SAP.user.mapper.UserMapper;
import com.tabwu.SAP.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User findUserById(String id) {
        return baseMapper.findUserById(id);
    }
}
