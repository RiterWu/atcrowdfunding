package com.riter.atcrowdfunding.interceptor;

import com.riter.atcrowdfunding.bean.Permission;
import com.riter.atcrowdfunding.manager.service.PermissionService;
import com.riter.atcrowdfunding.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.查询所有许可
        Set<String> allRUIs = (Set<String>) request.getSession().getServletContext().getAttribute(Constant.ALL_PERMISSION_URIS);

        // 2.判断请求路径是否在所有许可范围内
        String servletPath = request.getServletPath();
        if (allRUIs.contains(servletPath)) {
            // 3.判断请求路径是否在用户所拥有的权限之内
            Set<String> myUris = (Set<String>) request.getSession().getAttribute(Constant.MY_URIS);
            if (myUris.contains(servletPath)) {
                return true;
            } else {
                response.sendRedirect(request.getContextPath() + "/login.htm");
                return false;
            }
        } else {     // 不在拦截范围之内，放行
            return true;
        }

    }
}
