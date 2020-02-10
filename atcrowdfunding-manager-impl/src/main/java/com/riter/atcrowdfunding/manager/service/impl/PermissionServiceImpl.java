package com.riter.atcrowdfunding.manager.service.impl;

import com.riter.atcrowdfunding.bean.Permission;
import com.riter.atcrowdfunding.manager.dao.PermissionMapper;
import com.riter.atcrowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


    public Permission getRootPermission() {
        return permissionMapper.getRootPermission();
    }

    public List<Permission> getChildrenPermissionByPid(Integer id) {
        return permissionMapper.getChildrenPermissionByPid(id);
    }

    public List<Permission> getAllPermission() {
        return permissionMapper.getAllPermission();
    }

    public int savePermission(Permission permission) {
        return  permissionMapper.insert(permission);
    }

    public Permission getPermissionById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    public int updatePermission(Permission permission) {
        return permissionMapper.updateByPrimaryKeySelective(permission);
    }

    public int deletePermission(Integer id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    public List<Integer> getPermissionIdByRoleid(Integer roleid) {
        return permissionMapper.getPermissionIdByRoleid(roleid);
    }
}
