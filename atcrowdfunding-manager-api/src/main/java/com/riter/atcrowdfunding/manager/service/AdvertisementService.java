package com.riter.atcrowdfunding.manager.service;

import com.github.pagehelper.PageInfo;
import com.riter.atcrowdfunding.bean.Advertisement;
import com.riter.atcrowdfunding.utils.Page;

import java.util.Map;

public interface AdvertisementService {

    PageInfo<Advertisement> queryAdvertise(Map<String, Object> map);

    int insert(Advertisement advertisement);

    Advertisement getAdvertById(Integer id);

    int update(Advertisement advertisement);

    int deleteById(Integer id);
}
