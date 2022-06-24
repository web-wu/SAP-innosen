package com.tabwu.SAP.user.mapper;

import com.tabwu.SAP.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author tabwu
 * @since 2022-06-23
 */
public interface UserMapper extends BaseMapper<User> {

    User findUserById(String id);
}
