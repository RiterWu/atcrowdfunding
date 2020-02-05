package com.riter.atcrowdfunding.manager.controller;

import com.riter.atcrowdfunding.manager.service.UserService;
import com.riter.atcrowdfunding.utils.AjaxResult;
import com.riter.atcrowdfunding.utils.Page;
import com.riter.atcrowdfunding.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "user/index";
    }

    @ResponseBody
    @RequestMapping("/index")
    public Object index(@RequestParam(value = "pageno", defaultValue = "1") Integer pageno,
                        @RequestParam(value = "pagesize", defaultValue = "10") Integer pagesize,
                        String queryText) {
        AjaxResult result = new AjaxResult();

        try {

            Map paraMap = new HashMap();
            paraMap.put("pageno", pageno);
            paraMap.put("pagesize", pagesize);

            if(StringUtil.isNotEmpty(queryText)){
                if (queryText.contains("%")){
                    queryText = queryText.replaceAll("%","\\\\%");
                }
                paraMap.put("queryText", queryText);
            }
            Page page = userService.queryPage(paraMap);
            result.setStatus(true);
            result.setObjects(page);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("查询数据失败！");
        }

        return result;
    }

    //异步请求
    /*@ResponseBody
    @RequestMapping("/index")
    public Object index(@RequestParam(value = "pageno", defaultValue = "1") Integer pageno,
                        @RequestParam(value = "pagesize", defaultValue = "10") Integer pagesize) {
        AjaxResult result = new AjaxResult();

        try {
            Page page = userService.queryPage(pageno, pagesize);
            result.setStatus(true);
            result.setObjects(page);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("查询数据失败！");
        }

        return result;
    }*/

    //同步请求
    /*@RequestMapping("/index")
    public String index(@RequestParam(value = "pageno", defaultValue = "1") Integer pageno,
                        @RequestParam(value = "pagesize", defaultValue = "10") Integer pagesize, Map map) {

        Page page = userService.queryPage(pageno, pagesize);
        map.put("page", page);

        return "user/index";
    }*/
}
