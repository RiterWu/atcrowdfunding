package com.riter.atcrowdfunding.manager.service.impl;

import com.riter.atcrowdfunding.bean.Role;
import com.riter.atcrowdfunding.manager.dao.RoleMapper;
import com.riter.atcrowdfunding.manager.service.RoleService;
import com.riter.atcrowdfunding.utils.Page;
import com.riter.atcrowdfunding.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    public Page queryPage(Map map) {
        Page page = new Page((Integer) map.get("pageno"),(Integer) map.get("pagesize"));

        Integer startIndex = page.getStartIndex();
        map.put("startIndex",startIndex);

        List<Role> roleList = roleMapper.queryList(map);
        Integer totalsize = roleMapper.count(map);

        page.setTotalsize(totalsize);
        page.setDatas(roleList);
        return page;
    }
}
