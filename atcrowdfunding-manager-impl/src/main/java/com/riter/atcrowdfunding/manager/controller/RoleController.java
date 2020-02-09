package com.riter.atcrowdfunding.manager.controller;

import com.riter.atcrowdfunding.manager.service.RoleService;
import com.riter.atcrowdfunding.utils.AjaxResult;
import com.riter.atcrowdfunding.utils.Page;
import com.riter.atcrowdfunding.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "role/index";
    }

    @RequestMapping("/assignPermission")
    public String assignPermission(Integer id){
        return "role/assign_permission";
    }

    @ResponseBody
    @RequestMapping("/index")
    public Object index(@RequestParam(value = "pageno",defaultValue = "1") Integer pageno,
                        @RequestParam(value = "pagesize",defaultValue = "10") Integer pagesize,
                        String queryText){
        AjaxResult result = new AjaxResult();

        try {
            Map map = new HashMap();
            map.put("pageno",pageno);
            map.put("pagesize",pagesize);

            if(StringUtil.isNotEmpty(queryText)){
                if (queryText.contains("%")){
                    queryText = queryText.replaceAll("%","\\\\%");
                }
                map.put("queryText", queryText);
            }

            Page page = roleService.queryPage(map);

            result.setStatus(true);
            result.setObjects(page);

        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("查询数据失败!");
        }



        return result;
    }
}
