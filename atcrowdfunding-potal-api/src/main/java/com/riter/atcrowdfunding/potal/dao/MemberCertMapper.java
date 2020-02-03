package com.riter.atcrowdfunding.potal.dao;

import com.riter.atcrowdfunding.bean.MemberCert;
import com.riter.atcrowdfunding.bean.MemberCertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberCertMapper {
    long countByExample(MemberCertExample example);

    int deleteByExample(MemberCertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberCert record);

    int insertSelective(MemberCert record);

    List<MemberCert> selectByExample(MemberCertExample example);

    MemberCert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberCert record, @Param("example") MemberCertExample example);

    int updateByExample(@Param("record") MemberCert record, @Param("example") MemberCertExample example);

    int updateByPrimaryKeySelective(MemberCert record);

    int updateByPrimaryKey(MemberCert record);
}