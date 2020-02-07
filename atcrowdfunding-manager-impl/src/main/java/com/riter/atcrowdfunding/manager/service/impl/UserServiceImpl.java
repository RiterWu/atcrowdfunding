package com.riter.atcrowdfunding.manager.service.impl;

import com.riter.atcrowdfunding.bean.Role;
import com.riter.atcrowdfunding.bean.User;
import com.riter.atcrowdfunding.exception.LoginFailException;
import com.riter.atcrowdfunding.manager.dao.RoleMapper;
import com.riter.atcrowdfunding.manager.dao.UserMapper;
import com.riter.atcrowdfunding.manager.service.UserService;
import com.riter.atcrowdfunding.utils.Constant;
import com.riter.atcrowdfunding.utils.MD5Util;
import com.riter.atcrowdfunding.utils.Page;
import com.riter.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(Map<String, Object> paramMap) {

         User user = userMapper.login(paramMap);

        if (user==null){
            throw new LoginFailException("用户名或密码不正确！");
        }

        return user;
    }

    public Page queryPage(Map<String, Object> paraMap) {
        Page page = new Page((Integer)paraMap.get("pageno"), (Integer)paraMap.get("pagesize"));

        Integer startIndex = page.getStartIndex();
        paraMap.put("startIndex", startIndex);

        List<User> datas = userMapper.queryList(paraMap);
        Integer totalsize = userMapper.count(paraMap);
        page.setDatas(datas);
        page.setTotalsize(totalsize);
        return page;
    }

    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public int deleteById(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int deleteBatchByIds(Integer[] ids) {
        Integer totalCount = 0;
        for (Integer id : ids) {
            int count = userMapper.deleteByPrimaryKey(id);
            totalCount += count;
        }
        if (totalCount != ids.length) {
            throw new RuntimeException("批量删除失败！");
        }
        return totalCount;
    }

    public List<Role> queryAllRole() {
        return userMapper.queryAllRole();
    }

    public List<Integer> queryRoleByUserId(Integer id) {
        return userMapper.queryRoleByUserId(id);
    }

    public int saveUserRoleRelationship(Integer userid, Data data) {
        return userMapper.saveUserRoleRelationship(userid,data);
    }

    public int deleteUserRoleRelationship(Integer userid, Data data) {
        return userMapper.deleteUserRoleRelationship(userid,data);
    }

    /*public Page queryPage(Integer pageno, Integer pagesize) {
        Page page = new Page(pageno, pagesize);
        Integer startIndex = page.getStartIndex();
        List<User> datas = userMapper.queryList(startIndex, pagesize);
        Integer totalsize = userMapper.count();
        page.setDatas(datas);
        page.setTotalsize(totalsize);
        return page;
    }*/

    public Integer save(User user) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        user.setCreatetime(sdf.format(date));
        user.setUserpswd(MD5Util.digest(Constant.PASSWORD));
        return userMapper.insert(user);
    }



}
