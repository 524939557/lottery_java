package com.homeene.controller;

import java.time.LocalDate;
<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> 8-27-21

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dingtalk.open.client.api.model.corp.MessageBody;
import com.dingtalk.open.client.api.model.corp.MessageType;
import com.homeene.alibaba.auth.AuthHelper;
import com.homeene.alibaba.demo.Vars;
import com.homeene.alibaba.message.LightAppMessageDelivery;
import com.homeene.alibaba.message.MessageHelper;
import com.homeene.service.AwardService;
import com.homeene.service.TestServices;
import com.homeene.utils.RedisUtil;

@RestController
<<<<<<< HEAD
public class TestController extends RedisUtil{
    @Resource
    private TestServices testServices;
    
    @Resource
    private AwardService awardService;
    
    @RequestMapping(value = "/show",method =RequestMethod.GET)
    public String show() {
        return testServices.show();
    }

    @RequestMapping(value = "/showDao")
    public Object showDao(int age) {
        return testServices.showDao(age);
    }
    
    @RequestMapping("/encache")
    public String EhcacheTest(){
     System.out.println("第一次");
     System.out.println(awardService.getAward());
     System.out.println("第二次");
     System.out.println(awardService.getAward());
     return "success";
    }
    
    public static void main(String[] args) {
    	LocalDate date=LocalDate.now();
    	System.out.println(date.toString());
=======
public class TestController extends RedisUtil {

	@Resource
	private TestServices testServices;

	@Resource
	private AwardService awardService;

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return testServices.show();
	}

	@RequestMapping(value = "/showDao")
	public Object showDao(int age) {
		return testServices.showDao(age);
	}

	@RequestMapping("/encache")
	public String EhcacheTest() {
		System.out.println("第一次");
		System.out.println(awardService.getAward());
		System.out.println("第二次");
		System.out.println(awardService.getAward());
		return "success";
	}


	public static void main(String[] args) {
		LocalDate date = LocalDate.now();
		System.out.println(date.toString());
>>>>>>> 8-27-21
	}
}
