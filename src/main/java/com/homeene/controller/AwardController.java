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

	/**
	 * 我的所有卡
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/myCard", method = RequestMethod.GET)
	public List<MyAward> selectMyAward(HttpServletRequest req) throws UnsupportedEncodingException {
		User u=cookieservice.cookieToUser(req);
		List<MyAward> result=myAwardService.selectMyAward(u.getUserid());
		result.forEach(ma->System.out.println(ma.getAwardId()+":"+ma.getTotal()));
		return result;
	}

	/**
	 * 我是否集齐21张卡
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/mylottery", method = RequestMethod.GET)
	public User getLotteryUser(HttpServletRequest req) throws UnsupportedEncodingException {
		User u = cookieservice.cookieToUser(req);
		return userservice.selectLotteryByUserId(u.getUserid());
	}
	/**
	 * 集齐21张卡的人
	 * @return
	 */
	@RequestMapping(value="/lotteryUsers",method=RequestMethod.GET)
	public List<User> getLotteryUsers(){
		
		return userservice.selectByCollect(1);
	}
}
