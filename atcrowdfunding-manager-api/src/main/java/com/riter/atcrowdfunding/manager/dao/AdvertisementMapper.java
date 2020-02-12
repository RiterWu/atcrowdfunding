package com.riter.atcrowdfunding.manager.dao;

import com.riter.atcrowdfunding.bean.Advertisement;
import com.riter.atcrowdfunding.bean.AdvertisementExample;
import java.util.List;
import java.util.Map;

import com.riter.atcrowdfunding.utils.Page;
import org.apache.ibatis.annotations.Param;

public interface AdvertisementMapper {
    long countByExample(AdvertisementExample example);

    int deleteByExample(AdvertisementExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    int insertSelective(Advertisement record);

    List<Advertisement> selectByExample(AdvertisementExample example);

    Advertisement selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Advertisement record, @Param("example") AdvertisementExample example);

    int updateByExample(@Param("record") Advertisement record, @Param("example") AdvertisementExample example);

    int updateByPrimaryKeySelective(Advertisement record);

    int updateByPrimaryKey(Advertisement record);

    List<Advertisement> queryAdvertise(Map<String, Object> map);

    Integer count(Map<String, Object> map);

    List<Advertisement> queryAll();
}