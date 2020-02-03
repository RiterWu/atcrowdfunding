package com.riter.atcrowdfunding.manager.service;

import com.riter.atcrowdfunding.bean.User;

import java.util.Map;

public interface UserService {

    User login(Map<String, Object> paramMap);
}
