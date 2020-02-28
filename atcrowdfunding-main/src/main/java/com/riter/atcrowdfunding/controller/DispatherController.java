package com.riter.atcrowdfunding.controller;

import com.riter.atcrowdfunding.bean.Member;
import com.riter.atcrowdfunding.bean.Permission;
import com.riter.atcrowdfunding.bean.User;
import com.riter.atcrowdfunding.manager.service.UserService;
import com.riter.atcrowdfunding.potal.service.MemberService;
import com.riter.atcrowdfunding.utils.AjaxResult;
import com.riter.atcrowdfunding.utils.Constant;
import com.riter.atcrowdfunding.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.provider.MD5;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class DispatherController {

    @Autowired
    private UserService userService;

    @Autowired
    private MemberService memberService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {

        // 判断是否需要自动登录
        boolean needLogin = true;
        String loginType = null;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("loginCode".equals(cookie.getName())) {
                    String loginCode = cookie.getValue();

                    String[] split = loginCode.split("&");
                    if (split.length == 3) {
                        String loginacct = split[0].split("=")[1];
                        String userpswd = split[1].split("=")[1];
                        loginType = split[2].split("=")[1];

                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("loginacct", loginacct);
                        paramMap.put("userpswd", userpswd);

                        if ("member".equals(loginType)) {
                            Member member = memberService.MemberLogin(paramMap);
                            if (member != null) {
                                needLogin = false;
                                session.setAttribute(Constant.LOGIN_MEMBER, member);
                            }
                        } else if ("user".equals(loginType)) {
                            User user = userService.login(paramMap);
                            if (user != null) {
                                needLogin = false;
                                session.setAttribute(Constant.LOGIN_USER, user);
                            }
                        }
                    }
                }
            }
        }

        if (needLogin) {
            return "login";
        } else {
            if ("member".equals(loginType)) {
                return "redirect:member.htm";
            } else if ("user".equals(loginType)) {
                return "redirect:main.htm";
            }
        }

        return "login";
    }

    @RequestMapping("/member")
    public String member(HttpSession session) {
        return "member/member";
    }

    @RequestMapping("/main")
    public String main(HttpSession session) {
        return "main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        //销毁session对象或清理session域
        session.invalidate();
        return "redirect:/index.htm";
    }

    //异步请求
    @ResponseBody
    @RequestMapping("/doLogin")
    public Object doLogin(String loginacct, String userpswd, String type, Boolean isRememberMe,
                          HttpSession session, HttpServletResponse response) {

        AjaxResult result = new AjaxResult();

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("loginacct", loginacct);
            paramMap.put("userpswd", MD5Util.digest(userpswd));
            paramMap.put("type", type);

            if ("member".equals(type)) {
                Member member = memberService.MemberLogin(paramMap);
                session.setAttribute(Constant.LOGIN_MEMBER, member);

                if (isRememberMe) {
                    String loginCode = "\"loginacct=" + member.getLoginacct() + "&userpswd=" + member.getUserpswd() + "&type=member\"";

                    System.out.println("会员Cookies键值对：loginCode:" + loginCode);

                    Cookie cookie = new Cookie("loginCode", loginCode);
                    cookie.setMaxAge(60 * 60 * 24 * 14);
                    cookie.setPath("/");

                    response.addCookie(cookie);
                }

            } else if ("user".equals(type)) {
                User user = userService.login(paramMap);
                session.setAttribute(Constant.LOGIN_USER, user);

                if (isRememberMe) {
                    String loginCode = "\"loginacct=" + user.getLoginacct() + "&userpswd=" + user.getUserpswd() + "&type=user\"";

                    System.out.println("会员Cookies键值对：loginCode:" + loginCode);

                    Cookie cookie = new Cookie("loginCode", loginCode);
                    cookie.setMaxAge(60 * 60 * 24 * 14);
                    cookie.setPath("/");

                    response.addCookie(cookie);
                }

                // ------------------------------------------------------------
                //User user = (User) session.getAttribute(Constant.LOGIN_USER);

                // 当前用户所拥有的许可权限
                List<Permission> myPermissionList = userService.getPermissionByUserId(user.getId());

                Permission permissionRoot = null;

                Map<Integer, Permission> map = new HashMap<Integer, Permission>();

                Set<String> myUris = new HashSet<String>(); // 用于拦截器拦截许可权限

                for (Permission permission : myPermissionList) {
                    map.put(permission.getId(), permission);

                    myUris.add("/" + permission.getUrl());
                }

                session.setAttribute(Constant.MY_URIS, myUris);

                for (Permission permission : myPermissionList) {
                    if (permission.getPid() == null) {
                        permissionRoot = permission;
                    } else {
                        Permission parent = map.get(permission.getPid());
                        parent.getChildren().add(permission);
                    }
                }

                session.setAttribute("permissionRoot", permissionRoot);
                // -------------------------------------------------------
            }

            result.setObjects(type);
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
