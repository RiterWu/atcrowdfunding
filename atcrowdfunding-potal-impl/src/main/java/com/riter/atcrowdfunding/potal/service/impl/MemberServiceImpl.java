package com.riter.atcrowdfunding.potal.service.impl;

import com.riter.atcrowdfunding.bean.Member;
import com.riter.atcrowdfunding.potal.dao.MemberMapper;
import com.riter.atcrowdfunding.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public Member MemberLogin(Map<String, Object> paramMap) {
        return memberMapper.MemberLogin(paramMap);
    }
}
