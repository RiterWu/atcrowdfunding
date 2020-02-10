package com.riter.atcrowdfunding.manager.controller;

import com.riter.atcrowdfunding.bean.Permission;
import com.riter.atcrowdfunding.manager.service.PermissionService;
import com.riter.atcrowdfunding.manager.service.RoleService;
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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "role/index";
    }

    @RequestMapping("/toAssignPermission")
    public String toAssignPermission(){
        return "role/assign_permission";
    }

    @ResponseBody
    @RequestMapping("/assignPermission")
    public Object assignPermission(Integer roleid, Data data) {
        AjaxResult result = new AjaxResult();

        try {
            int count = roleService.saveRolePermissionRelationship(roleid, data);
            result.setStatus(count == data.getIds().size());
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("分配权限失败！");
        }

        return result;
    }


    @ResponseBody
    @RequestMapping("/loadDataAsync")
    public Object loadDataAsync(Integer roleid) {

        List<Permission> root = new ArrayList<Permission>();

        List<Permission> permissionList = permissionService.getAllPermission();

        List<Integer> permissionIds = permissionService.getPermissionIdByRoleid(roleid);

        Map<Integer, Permission> map = new HashMap<Integer, Permission>();

        for (Permission permission : permissionList) {
            map.put(permission.getId(), permission);
            if (permissionIds.contains(permission.getId())) {
                permission.setChecked(true);
            }
        }

        for (Permission permission : permissionList) {
            if (permission.getPid() == null) {
                root.add(permission);
            } else {
                Permission parent = map.get(permission.getPid());
                parent.getChildren().add(permission);
            }
        }

        return root;
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
