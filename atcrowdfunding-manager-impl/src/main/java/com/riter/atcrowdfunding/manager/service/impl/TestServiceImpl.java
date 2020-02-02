package com.riter.atcrowdfunding.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riter.atcrowdfunding.manager.dao.TestDao;
import com.riter.atcrowdfunding.manager.service.TestService;

import java.util.HashMap;
import java.util.Map;


@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestDao testDao;
	
	public void insert() {
		Map map = new HashMap();
		map.put("name", "zhang3");
		testDao.insert(map);
		System.out.println("你好！。。。。");
	}

}
