package com.riter.atcrowdfunding.listener;

import com.riter.atcrowdfunding.bean.Permission;
import com.riter.atcrowdfunding.manager.service.PermissionService;
import com.riter.atcrowdfunding.utils.Constant;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartSystemListener implements ServletContextListener {

    // 在服务器启动时，创建application对象需要执行的方法。
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 1.将项目上下文路径(request.getContextPath())放置到application域中
        ServletContext application = servletContextEvent.getServletContext();
        String contextPath = application.getContextPath();
        application.setAttribute(Constant.APP_PATH, contextPath);
        System.out.println("APP_PATH...");

        // 2.将所有权限放入到application域中
        ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(application);
        PermissionService permissionService = ioc.getBean(PermissionService.class);
        List<Permission> allPermission = permissionService.getAllPermission();

        Set<String> allRUIs = new HashSet<String>();

        for (Permission permission : allPermission) {
            allRUIs.add("/"+permission.getUrl());
        }
        application.setAttribute(Constant.ALL_PERMISSION_URIS,allRUIs);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
