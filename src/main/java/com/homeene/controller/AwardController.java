package com.homeene.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.homeene.service.UserService;

@RestController
@RequestMapping(value = "/lottery")
public class AwardController {

	@Resource
	private MyAwardService myAwardService;

	@Resource
	private AwardService awardService;

	@Resource
	private CookieService cookieservice;

	@Resource
	private UserService userservice;

	@RequestMapping(value = "/myCard/{userId}", method = RequestMethod.GET)
	public List<MyAward> selectMyAward(@PathVariable String userId) {

		return myAwardService.selectMyAward(userId);
	}

	@RequestMapping(value = "/getCard", method = RequestMethod.GET)
	public Award getAward(HttpServletRequest req) throws UnsupportedEncodingException {

		User u = cookieservice.cookieToUser(req);
		List<Award> awardList = awardService.getAward();
		List<MyAward> myward = myAwardService.selectMyAward(u.getUserid());
		Award award = awardService.lotter(awardList, myward);// 将已抽到的卡片概率分给其它，抽取一张卡片
		HashMap<String,Object> map=new HashMap<>();
		map.put("awardId", award.getId());
		map.put("userId", u.getUserid());
		MyAward myAward = myAwardService.selectMyAwardById(map);// 查看该卡是否拥有
		if (myAward == null)
		{
			myAward = new MyAward();
			myAward.setAwardId(award.getId());
			myAward.setUserId(u.getUserid());
			myAward.setTotal(1);
			myAwardService.insert(myAward);
		} else
		{
			myAward.setTotal(myAward.getTotal() + 1);
			myAwardService.update(myAward);
		}
		Integer total = myAwardService.selectMyCollect(u.getUserid());// 是否集齐21张
		if (total == 21)
		{
			u.setCollect(1);
			u.setCollectTime(new Date());
			userservice.update(u);
		}
		return award;
	}

	@RequestMapping(value = "/mylottery", method = RequestMethod.GET)
	public User getLotteryUser(HttpServletRequest req) throws UnsupportedEncodingException {
		User u = cookieservice.cookieToUser(req);
		return userservice.selectLotteryByUserId(u.getUserid());
	}
}
