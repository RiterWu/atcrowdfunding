package com.riter.atcrowdfunding.manager.controller;

import com.github.pagehelper.PageInfo;
import com.riter.atcrowdfunding.bean.Advertisement;
import com.riter.atcrowdfunding.bean.User;
import com.riter.atcrowdfunding.controller.BaseController;
import com.riter.atcrowdfunding.manager.service.AdvertisementService;
import com.riter.atcrowdfunding.utils.AlgorithmUtil;
import com.riter.atcrowdfunding.utils.Constant;
import com.riter.atcrowdfunding.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/advert")
public class AdvertisementController extends BaseController {

    @Autowired
    private AdvertisementService advertisementService;

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "advert/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "advert/add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id,Map map){
        Advertisement advertisement = advertisementService.getAdvertById(id);
        map.put("advertisement", advertisement);
        return "advert/update";
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(Integer id){
        start();

        try {
            int count = advertisementService.deleteById(id);
            success(count == 1);
        } catch (Exception e) {
            success(false);
            error("删除失败！");
            e.printStackTrace();
        }

        return end();
    }

    @ResponseBody
    @RequestMapping("/update")
    public Object update(Advertisement advertisement){
        start();

        try {
            int count = advertisementService.update(advertisement);
            success(count == 1);
        } catch (Exception e) {
            success(false);
            error("更新失败！");
            e.printStackTrace();

        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/add")
    public Object add(HttpServletRequest request, Advertisement advertisement, HttpSession session) {
        start();

        try {
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
            MultipartFile mFile = mreq.getFile("advpic");

            String originalFilename = mFile.getOriginalFilename();
            String suffName = originalFilename.substring(originalFilename.lastIndexOf(".")); //.jpg

            String iconPath = UUID.randomUUID().toString() + suffName;

            String realPath = session.getServletContext().getRealPath("/WEB-INF/upload");

            File realPathDir = new File(realPath);
            if (!realPathDir.exists()) {
                realPathDir.mkdir();
            }

            // 使用hash算法产生当前上传图片的随机目录
            String path = AlgorithmUtil.makePath(realPath, iconPath);

            mFile.transferTo(new File(path));

            User user = (User) session.getAttribute(Constant.LOGIN_USER);


            advertisement.setUserid(user.getId());
            advertisement.setIconpath(iconPath);
            advertisement.setStatus("1");

            int count = advertisementService.insert(advertisement);
            success(count == 1);

        } catch (IOException e) {
            success(false);
            error("广告数据保存失败");
            e.printStackTrace();
        }

        return end();
    }

    @ResponseBody
    @RequestMapping("/index")
    public Object index(@RequestParam(value = "pageno",defaultValue = "1") Integer pageno,
                        @RequestParam(value = "pagesize",defaultValue = "2") Integer pagesize){
        start();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("pageNo",pageno);
        map.put("pageSize",pagesize);
        /*Integer startIndex = (pageno-1)*pagesize;
        map.put("startIndex",startIndex);*/

        try {
            PageInfo<Advertisement> page = advertisementService.queryAdvertise(map);
            success(true);
            param("pageInfo",page);
        } catch (Exception e) {
            success(false);
            e.printStackTrace();
            error("查询失败！");
        }

        return end();
    }
}
