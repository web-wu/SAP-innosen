package com.tabwu.SAP.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tabwu.SAP.user.entity.Role;
import com.tabwu.SAP.user.entity.User;
import com.tabwu.SAP.user.mapper.UserMapper;
import com.tabwu.SAP.user.service.IUserRoleService;
import com.tabwu.SAP.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User findUserById(String id) {
        return baseMapper.findUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        boolean matches = passwordEncoder.matches("123456", user.getPassword());
        List<Role> roles = userRoleService.findRolesByUserId(user.getId());
        user.setRoles(roles);
        return user;
    }
}
