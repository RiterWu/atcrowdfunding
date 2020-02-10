package com.riter.atcrowdfunding.manager.service;

import com.riter.atcrowdfunding.utils.Page;
import com.riter.atcrowdfunding.vo.Data;

import java.util.Map;

public interface RoleService {

    Page queryPage(Map map);

    int saveRolePermissionRelationship(Integer roleid, Data data);
}
