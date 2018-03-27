package com.homeene.controller;

import java.time.LocalDate;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.homeene.service.AwardService;
import com.homeene.service.TestServices;
import com.homeene.utils.RedisUtil;

@RestController
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
	}
}
