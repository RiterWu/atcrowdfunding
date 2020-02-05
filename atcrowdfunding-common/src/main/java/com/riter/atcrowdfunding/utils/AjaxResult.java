package com.riter.atcrowdfunding.utils;

/**
 *  封装异步请求消息
 */
public class AjaxResult {

    private Boolean status;
    private String message;
    private Object objects;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObjects() {
        return objects;
    }

    public void setObjects(Object objects) {
        this.objects = objects;
    }

}
