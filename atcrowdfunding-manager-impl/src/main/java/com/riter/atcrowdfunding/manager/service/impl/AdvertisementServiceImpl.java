package com.riter.atcrowdfunding.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.riter.atcrowdfunding.bean.Advertisement;
import com.riter.atcrowdfunding.manager.dao.AdvertisementMapper;
import com.riter.atcrowdfunding.manager.service.AdvertisementService;
import com.riter.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementMapper advertisementMapper;

    public PageInfo<Advertisement> queryAdvertise(Map<String, Object> map) {

        //Page page = new Page((Integer)map.get("pageno"),(Integer)map.get("pagesize"));
        //Integer startIndex = page.getStartIndex();
        PageHelper.startPage((Integer)map.get("pageNo"),(Integer) map.get("pageSize"));
        List<Advertisement> advertisementList = advertisementMapper.queryAll();

        /*Integer totalsize = advertisementMapper.count(map);
        page.setDatas(advertisementList);
        page.setTotalsize(totalsize);*/
        return new PageInfo<Advertisement>(advertisementList);
    }

    public int insert(Advertisement advertisement) {
        return advertisementMapper.insert(advertisement);
    }

    public Advertisement getAdvertById(Integer id) {
        return advertisementMapper.selectByPrimaryKey(id);
    }

    public int update(Advertisement advertisement) {
        return advertisementMapper.updateByPrimaryKeySelective(advertisement);
    }

    public int deleteById(Integer id) {
        return advertisementMapper.deleteByPrimaryKey(id);
    }

    public Integer deleteBatch(Data data) {
        int totalCount = 0;
        for (Integer id : data.getIds()) {
            int count = advertisementMapper.deleteByPrimaryKey(id);
            totalCount += count;
        }
        return totalCount;
    }

}
