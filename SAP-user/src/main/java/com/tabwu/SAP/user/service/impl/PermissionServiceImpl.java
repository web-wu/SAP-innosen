package com.tabwu.SAP.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.tabwu.SAP.user.entity.Permission;
import com.tabwu.SAP.user.mapper.PermissionMapper;
import com.tabwu.SAP.user.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Cacheable(cacheNames = "permission")
    public Permission findTree() {

       /* Permission permission = (Permission) redisTemplate.opsForValue().get("permission");

        if (permission == null) {
            List<Permission> permissionList = baseMapper.selectList(null);
            permission = findChildrens(permissionList);
            redisTemplate.opsForValue().set("permission",permission,1, TimeUnit.MINUTES);
        }

        return permission;*/

        List<Permission> permissionList = baseMapper.selectList(null);

        return findChildrens(permissionList);
    }

    public Permission findChildrens(List<Permission> permissionList) {
        Permission first = null;
        for (Permission permission : permissionList) {
            if ("0".equals(permission.getPid())) {
                first = findChildren(permission,permissionList);
            }
        }
        return first;
    }

    private Permission findChildren(Permission permission, List<Permission> permissionList) {
        permission.setChildren(new ArrayList<Permission>());

        for (Permission child : permissionList) {
            if (child.getPid().equals(permission.getId())) {
                permission.getChildren().add(findChildren(child,permissionList));
            }
        }

        return permission;
    }
}
