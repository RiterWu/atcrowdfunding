package com.riter.atcrowdfunding.manager.controller;

import com.riter.atcrowdfunding.manager.service.UserService;
import com.riter.atcrowdfunding.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(@RequestParam(value = "pageno", defaultValue = "1") Integer pageno,
                        @RequestParam(value = "pagesize", defaultValue = "10") Integer pagesize, Map map) {

        Page page = userService.queryPage(pageno, pagesize);
        map.put("page", page);

        return "user/index";
    }
}
