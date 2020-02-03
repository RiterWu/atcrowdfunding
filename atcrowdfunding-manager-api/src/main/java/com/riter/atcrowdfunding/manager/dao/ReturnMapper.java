package com.riter.atcrowdfunding.manager.dao;

import com.riter.atcrowdfunding.bean.Return;
import com.riter.atcrowdfunding.bean.ReturnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReturnMapper {
    long countByExample(ReturnExample example);

    int deleteByExample(ReturnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Return record);

    int insertSelective(Return record);

    List<Return> selectByExample(ReturnExample example);

    Return selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Return record, @Param("example") ReturnExample example);

    int updateByExample(@Param("record") Return record, @Param("example") ReturnExample example);

    int updateByPrimaryKeySelective(Return record);

    int updateByPrimaryKey(Return record);
}