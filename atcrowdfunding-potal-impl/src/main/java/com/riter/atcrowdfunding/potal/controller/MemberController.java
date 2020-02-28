package com.riter.atcrowdfunding.potal.controller;

import com.riter.atcrowdfunding.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/acctType")
    public String acctType() {
        return "member/acct_type";
    }
}
