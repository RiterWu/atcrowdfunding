package com.riter.atcrowdfunding.manager.service;

import com.riter.atcrowdfunding.bean.Permission;
import com.riter.atcrowdfunding.bean.Role;
import com.riter.atcrowdfunding.bean.User;
import com.riter.atcrowdfunding.utils.Page;
import com.riter.atcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface UserService {

    User login(Map<String, Object> paramMap);

    //Page queryPage(Integer pageno, Integer pagesize);

    Integer save(User user);

    Page queryPage(Map<String,Object> paraMap);

    User getUserById(Integer id);

    int updateUser(User user);

    int deleteById(Integer id);

    int deleteBatchByIds(Integer[] ids);

    List<Role> queryAllRole();

    List<Integer> queryRoleByUserId(Integer id);

    int saveUserRoleRelationship(Integer userid, Data data);

    int deleteUserRoleRelationship(Integer userid, Data data);

    List<Permission> getPermissionByUserId(Integer id);
}
