package com.homeene.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dingtalk.open.client.api.model.corp.CorpUserBaseInfo;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.MessageBody;
import com.dingtalk.open.client.api.model.corp.MessageType;
import com.google.gson.Gson;
import com.homeene.alibaba.auth.AuthHelper;
import com.homeene.alibaba.demo.Env;
import com.homeene.alibaba.demo.Vars;
import com.homeene.alibaba.message.LightAppMessageDelivery;
import com.homeene.alibaba.message.MessageHelper;
import com.homeene.alibaba.user.UserHelper;
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
public class DingController {

	@Resource private UserService userService;

	@Resource private AccessTokenService AccessTokenService;
	@Resource private PersonInfoService PersonInfoService;
	
	@Resource private PersistentLoginService persistentLoginService;
	
	@Resource private CookieService cookieService;

	@RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
	public Map<String, Object> getAuthCode() throws Exception {
		// 获取access token
		String accessToken = AuthHelper.getAccessToken();
		log("成功获取access token: ", accessToken);

		// 获取jsapi ticket
		String ticket = AuthHelper.getJsapiTicket(accessToken);
		log("成功获取jsapi ticket: ", ticket);

		// 获取签名
		String nonceStr = "nonceStr";
		long timeStamp = System.currentTimeMillis();
		String url = "http://test.homeene.com/";
		String signature = AuthHelper.sign(ticket, nonceStr, timeStamp, url);
		log("成功签名: ", signature);

		Map<String, Object> map = new HashMap<>();
		map.put("signature", signature);
		map.put("nonceStr", nonceStr);
		map.put("url", url);
		map.put("timeStamp", timeStamp);
		map.put("corpId", Env.CORP_ID);
		return map;
	}

	
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

	private static void log(Object... msgs) {
		StringBuilder sb = new StringBuilder();
		for (Object o : msgs)
		{
			if (o != null)
			{
				sb.append(o.toString());
			}
		}
		// System.out.println(sb.toString());
	}
	
	@RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
	public void sendmessage(HttpServletRequest req) throws Exception {
		User u=cookieService.cookieToUser(req);
		if(u.getCollect()==1 &&u.getChangeStatus()==0) {
			String toUsers = u.getUserid();
			String toParties = Vars.TO_PARTY;
			String agentId = Vars.AGENT_ID;
			MessageBody.TextBody textBody = new MessageBody.TextBody();
			textBody.setContent("恭喜你集齐21张文化卡，请于活动截止日期后联系总裁办兑换奖金！");
			LightAppMessageDelivery lightAppMessageDelivery = new LightAppMessageDelivery(toUsers, toParties, agentId);
			lightAppMessageDelivery.withMessage(MessageType.TEXT, textBody);
			String accessToken = AuthHelper.getAccessToken();
			MessageHelper.send(accessToken, lightAppMessageDelivery);
			System.out.println("成功发送 微应用文本消息");
			u.setChangeStatus(1);
			userService.update(u);
		}
		
	}
	
}
