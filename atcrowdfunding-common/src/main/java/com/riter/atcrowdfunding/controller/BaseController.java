package com.riter.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 改善代码-ThreadLocal
 * 其他controller继承BaseController
 * ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。
 */
public class BaseController {

    private ThreadLocal<Map<String, Object>> datas = new ThreadLocal<Map<String, Object>>();

    protected void start() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        datas.set(resultMap);
    }

    public Object end() {
        Map<String, Object> resultMap = datas.get();
        datas.remove();
        return resultMap;
    }

    public void success(Boolean flag) {
        Map<String, Object> resultMap = datas.get();
        resultMap.put("status", flag);
    }

    public void param(String key, Object value) {
        Map<String, Object> resultMap = datas.get();
        resultMap.put(key, value);
    }

    public void error(String message) {
        Map<String, Object> resultMap = datas.get();
        resultMap.put("message", message);
    }

}
