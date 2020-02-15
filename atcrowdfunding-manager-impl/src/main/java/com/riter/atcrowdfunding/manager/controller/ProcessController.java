package com.riter.atcrowdfunding.manager.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.riter.atcrowdfunding.controller.BaseController;
import com.riter.atcrowdfunding.utils.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/process")
public class ProcessController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "process/index";
    }

    @RequestMapping("/toShowImg")
    public String toShowImg(){
        return "process/show_img";
    }

    @ResponseBody
    @RequestMapping("/index")
    public Object index(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {
        start();

        try {
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
            //Page page = new Page(pageNo,pageSize);

            // 查询流程定义集合数据，可能出现了自关联，导致Jackson组件无法将集合序列化为JSON串
            //List<ProcessDefinition> listPage = processDefinitionQuery.listPage(page.getStartIndex(), pageSize);

            PageHelper.startPage(pageNo, pageSize);
            List<ProcessDefinition> list = processDefinitionQuery.list();


            List<Map<String, Object>> myListPage = new ArrayList<Map<String, Object>>();

            for (ProcessDefinition processDefinition : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", processDefinition.getId());
                map.put("name", processDefinition.getName());
                map.put("key", processDefinition.getKey());
                map.put("version", processDefinition.getVersion());

                myListPage.add(map);
            }

            /*Long count = processDefinitionQuery.count();

            page.setDatas(myListPage);
            page.setTotalsize(count.intValue());*/

            param("pageInfo", new PageInfo<Map<String, Object>>(myListPage));
            success(true);
        } catch (Exception e) {
            success(false);
            error("查询失败！");
            e.printStackTrace();
        }

        return end();
    }

    @ResponseBody
    @RequestMapping("/deployProc")
    public Object deployProc(MultipartHttpServletRequest request){
        start();

        MultipartFile deployProcFile = request.getFile("deployProcFile");

        try {
            repositoryService.createDeployment()
                    .addInputStream(deployProcFile.getOriginalFilename(), deployProcFile.getInputStream())
                    .deploy();
            success(true);
        } catch (IOException e) {
            success(false);
            error("部署流程定义失败！");
            e.printStackTrace();
        }

        return end();
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object deployProc(String id){
        start();

        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();

            // true表示级联删除
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(),true);

            success(true);
        } catch (Exception e) {
            success(false);
            error("删除流程定义失败！");
            e.printStackTrace();
        }

        return end();
    }

    @ResponseBody
    @RequestMapping("/showImgProcDef")
    public void showImgProcDef(String id, HttpServletResponse response){
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();

        try {
            InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());

            ServletOutputStream outputStream = response.getOutputStream();

            IOUtils.copy(inputStream, outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
