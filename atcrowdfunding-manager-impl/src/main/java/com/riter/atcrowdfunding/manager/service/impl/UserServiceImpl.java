package com.riter.atcrowdfunding.manager.service.impl;

import com.riter.atcrowdfunding.bean.User;
import com.riter.atcrowdfunding.exception.LoginFailException;
import com.riter.atcrowdfunding.manager.dao.UserMapper;
import com.riter.atcrowdfunding.manager.service.UserService;
import com.riter.atcrowdfunding.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return userMapper.insert(user);
    }



}
