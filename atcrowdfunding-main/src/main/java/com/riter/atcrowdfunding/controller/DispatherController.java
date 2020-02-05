package com.riter.atcrowdfunding.controller;

import com.riter.atcrowdfunding.bean.User;
import com.riter.atcrowdfunding.manager.service.UserService;
import com.riter.atcrowdfunding.utils.AjaxResult;
import com.riter.atcrowdfunding.utils.Constant;
import com.riter.atcrowdfunding.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.provider.MD5;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DispatherController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        //销毁session对象或清理session域
        session.invalidate();
        return "redirect:/index.htm";
    }

    //异步请求
    @ResponseBody
    @RequestMapping("/doLogin")
    public Object doLogin(String loginacct, String userpswd, String type, HttpSession session) {

        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("loginacct", loginacct);
            paramMap.put("userpswd", MD5Util.digest(userpswd));
            paramMap.put("type", type);

            User user = userService.login(paramMap);

            session.setAttribute(Constant.LOGIN_USER, user);
            result.setStatus(true);
        } catch (Exception e) {
            result.setMessage("登录失败！");
            e.printStackTrace();
            result.setStatus(false);
        }

        return result;
    }

    //同步请求
    /* @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String userpswd, String type, HttpSession session){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("loginacct",loginacct);
        paramMap.put("userpswd",userpswd);
        paramMap.put("type",type);

        User user = userService.login(paramMap);

        session.setAttribute(Constant.LOGIN_USER,user);
        return "redirect:/main.htm";
    }*/
}
