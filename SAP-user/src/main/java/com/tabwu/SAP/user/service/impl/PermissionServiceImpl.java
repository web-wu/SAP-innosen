package com.tabwu.SAP.user.service.impl;

import com.tabwu.SAP.user.entity.Permission;
import com.tabwu.SAP.user.mapper.PermissionMapper;
import com.tabwu.SAP.user.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tabwu
 * @since 2022-06-23
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    public Permission findTree() {
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
