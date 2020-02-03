package com.riter.atcrowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartSystemListener implements ServletContextListener {

    // 在服务器启动时，创建application对象是需要执行的方法。
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 1.将项目上下文路径(request.getContextPath())放置到application域中
        ServletContext application = servletContextEvent.getServletContext();
        String contextPath = application.getContextPath();
        application.setAttribute("APP_PATH", contextPath);
        System.out.println("APP_PATH...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
