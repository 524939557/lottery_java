package com.homeene.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.homeene.model.MyAward;
import com.homeene.service.MyAwardService;

@RestController
public class AwardController {

	@Resource
	private MyAwardService myAwardService;
	
	@RequestMapping(value="/myAward/{userId}",method=RequestMethod.GET)
	public List<MyAward> selectMyAward(@PathVariable String userId){
		return myAwardService.selectMyAward(userId);
	}
}
