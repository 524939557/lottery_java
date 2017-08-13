package com.homeene.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.homeene.model.Award;
import com.homeene.model.MyAward;
import com.homeene.model.User;
import com.homeene.service.AwardService;
import com.homeene.service.CookieService;
import com.homeene.service.MyAwardService;

@RestController
public class AwardController {

	@Resource
	private MyAwardService myAwardService;
	
	@Resource
	private AwardService awardService;
	
	@Resource
	private CookieService cookieservice;
	
	@RequestMapping(value="/myAward/{userId}",method=RequestMethod.GET)
	public List<MyAward> selectMyAward(@PathVariable String userId){
		
		return myAwardService.selectMyAward(userId);
	}
	
	public Award getAward(HttpServletRequest req) throws UnsupportedEncodingException {
		
		User u=cookieservice.cookieToUser(req);
		List<Award> awardList=awardService.getAward();
		List<MyAward> myward=myAwardService.selectMyAward(u.getUserid());
		Award award= awardService.lotter(awardList, myward);
		MyAward myAward=myAwardService.selectMyAwardById(award.getId(),u.getUserid());
		if(myAward==null) {
			myAward=new MyAward();
			myAward.setAwardId(award.getId());
			myAward.setUserId(u.getUserid());
			myAward.setTotal(1);
			myAwardService.insert(myAward);
		}else {
			myAward.setTotal(myAward.getTotal()+1);
			myAwardService.update(myAward);
		}
		//select *from (SELECT user_id,count(*) counts from my_award GROUP BY user_id) t where t.counts>3;
		return award;
	}
}
