package com.riter.atcrowdfunding.potal.service;

import com.riter.atcrowdfunding.bean.Member;

import java.util.Map;

public interface MemberService {
    Member MemberLogin(Map<String, Object> paramMap);
}
