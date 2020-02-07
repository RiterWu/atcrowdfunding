package com.riter.atcrowdfunding.manager.controller;

import com.riter.atcrowdfunding.bean.Role;
import com.riter.atcrowdfunding.bean.User;
import com.riter.atcrowdfunding.manager.service.UserService;
import com.riter.atcrowdfunding.utils.AjaxResult;
import com.riter.atcrowdfunding.utils.Page;
import com.riter.atcrowdfunding.utils.StringUtil;
import com.riter.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "user/add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id,Map map){
        User user = userService.getUserById(id);
        map.put("user",user);
        return "user/update";
    }

    @RequestMapping("/assignRole")
    public String assignRole(Integer id,Map map){

         List<Role> allList = userService.queryAllRole();

         List<Integer> roleIds = userService.queryRoleByUserId(id);

         List<Role> leftRoleList = new ArrayList<Role>();       // 未分配角色
         List<Role> rightRoleList = new ArrayList<Role>();      // 已分配角色

        for (Role role : allList) {
            if (roleIds.contains(role.getId())) {
                rightRoleList.add(role);
            } else {
                leftRoleList.add(role);
            }
        }

        map.put("leftRoleList", leftRoleList);
        map.put("rightRoleList", rightRoleList);

        return "user/assign_role";
    }

    //分配角色
    @ResponseBody
    @RequestMapping("/assignUserRole")
    public Object assignUserRole(Integer userid, Data data) {
        AjaxResult result = new AjaxResult();

        try {
            int count = userService.saveUserRoleRelationship(userid,data);
            result.setStatus(true);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("分配失败！");
        }

        return result;
    }

    //取消角色
    @ResponseBody
    @RequestMapping("/UnAssignUserRole")
    public Object UnAssignUserRole(Integer userid, Data data) {
        AjaxResult result = new AjaxResult();

        try {
            int count = userService.deleteUserRoleRelationship(userid,data);
            result.setStatus(true);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("取消失败！");
        }

        return result;
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

    @ResponseBody
    @RequestMapping("/add")
    public Object add(User user) {
        AjaxResult result = new AjaxResult();

        try {
            int count = userService.save(user);
            result.setStatus(count == 1);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("保存用户失败！");
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/update")
    public Object update(User user) {

        AjaxResult result = new AjaxResult();

        try {
            int count = userService.updateUser(user);
            result.setStatus(count == 1);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("修改用户失败！");
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(Integer id) {

        AjaxResult result = new AjaxResult();

        try {
            int count = userService.deleteById(id);
            result.setStatus(count == 1);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("删除用户失败！");
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteBatch")
    public Object deleteBatch(Integer[] ids) {

        AjaxResult result = new AjaxResult();

        try {
            int count = userService.deleteBatchByIds(ids);
            result.setStatus(count == ids.length);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("删除用户失败！");
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
