package com.homeene.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homeene.service.TestServices;

@RestController
public class TestController {
    @Resource
    private TestServices testServices;

    @RequestMapping(value = "/show")
    public String show() {
        return testServices.show();
    }

    @RequestMapping(value = "/showDao")
    public Object showDao(int age) {
        return testServices.showDao(age);
    }
    
}