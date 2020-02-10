package com.riter.atcrowdfunding.manager.dao;

import com.riter.atcrowdfunding.bean.Permission;
import com.riter.atcrowdfunding.bean.Role;
import com.riter.atcrowdfunding.bean.User;
import com.riter.atcrowdfunding.bean.UserExample;
import java.util.List;
import java.util.Map;

import com.riter.atcrowdfunding.vo.Data;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User login(Map<String, Object> paramMap);

    //List<User> queryList(@Param("startIndex") Integer startIndex,@Param("pagesize") Integer pagesize);

    //Integer count();

    List<User> queryList(Map<String, Object> paraMap);

    Integer count(Map<String, Object> paraMap);

    List<Role> queryAllRole();

    List<Integer> queryRoleByUserId(Integer id);

    int saveUserRoleRelationship(@Param("userid") Integer userid, @Param("data") Data data);

    int deleteUserRoleRelationship(@Param("userid")  Integer userid, @Param("data") Data data);

    List<Permission> getPermissionByUserId(Integer id);
}