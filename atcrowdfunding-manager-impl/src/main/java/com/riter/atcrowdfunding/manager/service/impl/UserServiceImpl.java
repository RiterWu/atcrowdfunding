package com.riter.atcrowdfunding.manager.service.impl;

import com.riter.atcrowdfunding.bean.User;
import com.riter.atcrowdfunding.exception.LoginFailException;
import com.riter.atcrowdfunding.manager.dao.UserMapper;
import com.riter.atcrowdfunding.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
