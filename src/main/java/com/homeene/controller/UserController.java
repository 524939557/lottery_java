package com.homeene.controller;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.homeene.alibaba.auth.AuthHelper;
import com.homeene.model.AccessToken;
import com.homeene.model.PersonInfo;
import com.homeene.model.User;
import com.homeene.service.AccessTokenService;
import com.homeene.service.CookieService;
import com.homeene.service.PersistentLoginService;
import com.homeene.service.PersonInfoService;
import com.homeene.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Resource private UserService userService;

	@Resource private AccessTokenService AccessTokenService;
	@Resource private PersonInfoService PersonInfoService;
	
	@Resource private PersistentLoginService persistentLoginService;
	
	@Resource private CookieService cookieService;

	/**
	 * 获取用户信息
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public String getUser(@RequestBody Map<String, String> code, HttpServletRequest req, HttpServletResponse rsp)
			throws Exception {
		User user=cookieService.cookieToUser(req);
		if(user==null) {
			System.out.println(code.get("code"));
			String at_result=AccessTokenService.getAccessToken(code.toString());
			AccessToken at=new Gson().fromJson(at_result,AccessToken.class);
			AccessTokenService.insert(at);
			String person_result=AccessTokenService.getPersonInfoByAccessToken(at.getAccessToken(),at.getOpenid());
			PersonInfo pi=PersonInfoService.insertAccessPerson(person_result);
			user = new User();
			user.setCreateTime(new Date());
			user.setName(pi.getNickname());
			user.setUserid(pi.getOpenid());
			userService.insert(user);
		}else {
			String openid = user.getUserid();
			AccessToken at=AccessTokenService.selectByPrimaryKey(openid);
			boolean flag=AccessTokenService.checkToken(at.getAccessToken(), openid);
			if(!flag) {
				String result= AccessTokenService.getRefreshToken(at.getAccessToken());
				AccessToken at_new=new Gson().fromJson(result, AccessToken.class);
				AccessTokenService.updateByPrimaryKey(at_new);
			}
			String globalToken=AuthHelper.getAccessToken();
			String personInfo=AccessTokenService.getPersonInfoByGlobalAccessToken(globalToken, openid);
			PersonInfoService.insertPerson(personInfo);
		}
		String cookieValue = persistentLoginService.addCookie(user);
		return cookieValue;
	}
	
}
