package com.homeene.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.homeene.dao.TestDao;
import com.homeene.model.TestPOJO;

@Service
public class TestServices {
	 @Resource
    private TestDao testDao;

    public String show() {
        return "hello world!";
    }

    public List<TestPOJO> showDao(int age) {
        return testDao.get(age);
    }
}
