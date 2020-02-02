package com.riter.atcrowdfunding.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.riter.atcrowdfunding.manager.service.TestService;

@Controller
public class TestController {
	
	@Autowired
	private TestService testService;

	@RequestMapping("/text")
	public String test() {
		System.out.println("TestController");
		testService.insert();
		return "success";
	}
}
