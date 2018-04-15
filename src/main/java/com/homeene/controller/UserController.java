package com.homeene.controller;

import java.util.Date;
import java.util.HashMap;
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
import com.homeene.model.Game;
import com.homeene.service.AccessTokenService;
import com.homeene.service.CookieService;
import com.homeene.service.GameService;
import com.homeene.service.PersistentLoginService;
import com.homeene.service.PersonInfoService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Resource private GameService gameService;

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
	public Map<String, Object> getUser(@RequestBody Map<String, String> code, HttpServletRequest req, HttpServletResponse rsp)
			throws Exception {
		Game game=cookieService.cookieToUser(req);
		PersonInfo pi=new PersonInfo();
		if(game==null) {
			String code2=code.get("code");
			System.out.println(code2);
			String at_result=AccessTokenService.getAccessToken(code2);
			AccessToken at=new Gson().fromJson(at_result,AccessToken.class);
			AccessTokenService.insert(at);
			String globalToken=AuthHelper.getAccessToken();
			System.out.println("login global token="+globalToken);
			String personInfo=AccessTokenService.getPersonInfoByGlobalAccessToken(globalToken, at.getOpenid());
			pi=PersonInfoService.insertPerson(personInfo);
		}else {
			String openid = game.getUserid();
			AccessToken at=AccessTokenService.selectByPrimaryKey(openid);
			boolean flag=AccessTokenService.checkToken(at.getAccess_token(), openid);
			if(!flag) {
				String result= AccessTokenService.getRefreshToken(at.getAccess_token());
				AccessToken at_new=new Gson().fromJson(result, AccessToken.class);
				AccessTokenService.updateByPrimaryKey(at_new);
			}
			String globalToken=AuthHelper.getAccessToken();
			String personInfo=AccessTokenService.getPersonInfoByGlobalAccessToken(globalToken, openid);
			PersonInfoService.insertPerson(personInfo);
		}
		if(game==null || game.getActive()==false) {
			game = new Game();
			game.setCreateTime(new Date());
			game.setName(pi.getNickname());
			game.setActive(true);//活动的
			game.setUserid(pi.getOpenid());
			gameService.insert(game);
		}
		String cookieValue = persistentLoginService.addCookie(game);
		Map<String,Object> result =new HashMap();
		result.put("subscribe", pi.getSubscribe()==1);
		result.put("token", cookieValue);
		return result;
	}
	
}
