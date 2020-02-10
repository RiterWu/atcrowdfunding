package com.riter.atcrowdfunding.manager.controller;

import com.riter.atcrowdfunding.bean.Permission;
import com.riter.atcrowdfunding.manager.service.PermissionService;
import com.riter.atcrowdfunding.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "permission/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "permission/add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id,Map map){
        Permission permission = permissionService.getPermissionById(id);
        map.put("permission",permission);
        return "permission/update";
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(Integer id){
        AjaxResult result = new AjaxResult();

        try {
            int count = permissionService.deletePermission(id);
            result.setStatus(count == 1);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("删除许可树数据失败！");
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/update")
    public Object update(Permission permission){
        AjaxResult result = new AjaxResult();

        try {
            int count = permissionService.updatePermission(permission);
            result.setStatus(count == 1);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("更新许可树数据失败！");
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/add")
    public Object add(Permission permission){
        AjaxResult result = new AjaxResult();

        try {
            int count = permissionService.savePermission(permission);
            result.setStatus(count == 1);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("保存许可树数据失败！");
        }

        return result;
    }

    // Demo5 -- Map 查找父，组合父子关系。减少循环次数，提高性能。
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        AjaxResult result = new AjaxResult();

        try {
            List<Permission> root = new ArrayList<Permission>();

            List<Permission> permissionList = permissionService.getAllPermission();

            Map<Integer, Permission> map = new HashMap<Integer, Permission>();

            for (Permission permission : permissionList) {
                map.put(permission.getId(), permission);
            }

            for (Permission permission : permissionList) {
                if (permission.getPid() == null) {
                    root.add(permission);
                } else {
                    Permission parent = map.get(permission.getPid());
                    parent.getChildren().add(permission);
                }
            }

            result.setStatus(true);
            result.setObjects(root);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("加载许可树数据失败！");
        }

        return result;
    }

    // Demo4 -- 一次从数据库中查询所有数据，减少与数据库的交互次数
   /* @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult result = new AjaxResult();

        try {
            List<Permission> root = new ArrayList<Permission>();

            List<Permission> permissionList = permissionService.getAllPermission();

            for (Permission permission:permissionList) {
                if (permission.getPid() == null){
                    permission.setOpen(true);
                    root.add(permission);
                }else {
                    for(Permission parentPermission:permissionList){
                        if(permission.getPid() == parentPermission.getId()){
                            parentPermission.getChildren().add(permission);
                            parentPermission.setOpen(true);
                            break;
                        }
                    }
                }
            }

            result.setStatus(true);
            result.setObjects(root);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("加载许可树数据失败！");
        }

        return result;
    }*/


    // Demo3 -- 递归
    /*@ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult result = new AjaxResult();

        try {
            List<Permission> root = new ArrayList<Permission>();

            Permission permission = permissionService.getRootPermission();
            permission.setOpen(true);

            queryChildPermissions(permission);

            root.add(permission);

            result.setStatus(true);
            result.setObjects(root);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("加载许可树数据失败！");
        }

        return result;
    }

    private void queryChildPermissions(Permission permission){
        List<Permission> childrens = permissionService.getChildrenPermissionByPid(permission.getId());
        permission.setChildren(childrens);

        for (Permission children:childrens) {
            children.setOpen(true);
            queryChildPermissions(children);
        }
    }*/

    // Demo2 -- 从数据库查找
    /*@ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult result = new AjaxResult();

        try {
            List<Permission> root = new ArrayList<Permission>();

            Permission permission = permissionService.getRootPermission();
            permission.setOpen(true);

            List<Permission> childrens = permissionService.getChildrenPermissionByPid(permission.getId());

            permission.setChildren(childrens);

            for (Permission children:childrens) {
                children.setOpen(true);
                List<Permission> innerChildren = permissionService.getChildrenPermissionByPid(children.getId());
                children.setChildren(innerChildren);
            }

            root.add(permission);

            result.setStatus(true);
            result.setObjects(root);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("加载许可树数据失败！");
        }

        return result;
    }*/

    // Demo1 -- 模拟数据生成树
    /*@ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult result = new AjaxResult();

        try {
            List<Permission> root = new ArrayList<Permission>();

            Permission permission = new Permission();
            permission.setName("系统权限菜单");
            permission.setOpen(true);

            List<Permission> children = new ArrayList<Permission>();
            Permission permission1 = new Permission();
            permission1.setName("控制面板");

            Permission permission2 = new Permission();
            permission2.setName("权限管理");

            children.add(permission1);
            children.add(permission2);

            permission.setChildren(children);

            root.add(permission);

            result.setStatus(true);
            result.setObjects(root);
        } catch (Exception e) {
            result.setStatus(false);
            e.printStackTrace();
            result.setMessage("加载许可树数据失败！");
        }

        return result;
    }*/
}
