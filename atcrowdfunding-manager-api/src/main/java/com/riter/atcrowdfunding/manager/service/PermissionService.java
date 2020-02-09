package com.riter.atcrowdfunding.manager.service;

import com.riter.atcrowdfunding.bean.Permission;

import java.util.List;

public interface PermissionService {

    Permission getRootPermission();

    List<Permission> getChildrenPermissionByPid(Integer id);

    List<Permission> getAllPermission();

    int savePermission(Permission permission);

    Permission getPermissionById(Integer id);

    int updatePermission(Permission permission);

    int deletePermission(Integer id);
}
