package com.riter.atcrowdfunding.manager.service;

import com.riter.atcrowdfunding.bean.User;
import com.riter.atcrowdfunding.utils.Page;

import java.util.Map;

public interface UserService {

    User login(Map<String, Object> paramMap);

    //Page queryPage(Integer pageno, Integer pagesize);

    Integer save(User user);

    Page queryPage(Map<String,Object> paraMap);
}
