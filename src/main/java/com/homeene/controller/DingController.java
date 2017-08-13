package com.homeene.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dingtalk.open.client.api.model.corp.CorpUserBaseInfo;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.homeene.alibaba.auth.AuthHelper;
import com.homeene.alibaba.demo.Env;
import com.homeene.alibaba.user.UserHelper;
import com.homeene.model.User;
import com.homeene.service.PersistentLoginService;
import com.homeene.service.UserService;

@RestController
@RequestMapping(value="/user")
public class DingController {

	@Resource
	private UserService userService;
	
	@Resource
	private PersistentLoginService persistentLoginService;
	
	@RequestMapping(value="/getAuthCode",method=RequestMethod.GET)
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
		String url = "http://www.dingtalk.com";
		String signature = AuthHelper.sign(ticket, nonceStr, timeStamp, url);
		log("成功签名: ", signature);
		
		Map<String,Object> map=new HashMap<>();
		map.put("signature", signature);
		map.put("nonstr", "nonceStr");
		map.put("url", "");
		map.put("timestamp", new Date().getTime());
		map.put("CorpId",Env.CORP_ID);
		return map;
	}
	/**
	 * 获取用户信息
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public User getUser(String code,HttpServletRequest req,HttpServletResponse rsp) throws Exception {
		String accessToken = AuthHelper.getAccessToken();
		CorpUserBaseInfo userBaseInfo=UserHelper.getUserInfo(accessToken, code);
		User user=new User();
		user=userService.selectByUserId(userBaseInfo.getUserid());
		if(user==null) {
			CorpUserDetail userDetail=UserHelper.getUser(accessToken, userBaseInfo.getUserid());
			BeanUtils.copyProperties(userDetail, user);
			userService.insert(user);
		}
		persistentLoginService.addCookie(user);
		return user;
	}
	
	private static void log(Object... msgs) {
		StringBuilder sb = new StringBuilder();
		for (Object o : msgs) {
			if (o != null) {
				sb.append(o.toString());
			}
		}
		System.out.println(sb.toString());
	}
}
