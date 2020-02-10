package com.riter.atcrowdfunding.interceptor;

import com.riter.atcrowdfunding.bean.User;
import com.riter.atcrowdfunding.utils.Constant;
import javafx.scene.effect.SepiaTone;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.定义那些路径是不需要拦截的（将这些路径成为白名单）
        Set<String> uri = new HashSet<String>();
        uri.add("/user/reg.htm");
        uri.add("/user/doReg.do");
        uri.add("/login.htm");
        uri.add("/doLogin.do");
        uri.add("/logout.do");
        uri.add("/index.htm");

        // 获取请求路径
        String servletPath = request.getServletPath();
        if (uri.contains(servletPath)) {
            return true;
        }

        // 2.判断用户是否登录，如果登录就放行
        User user = (User) request.getSession().getAttribute(Constant.LOGIN_USER);
        if (user != null) {
            return true;
        } else {
            response.sendRedirect(request.getContextPath() + "/login.htm");
            return false;
        }
    }

}
