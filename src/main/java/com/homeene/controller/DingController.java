package com.homeene.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.homeene.alibaba.auth.AuthHelper;
import com.homeene.alibaba.demo.Env;
import com.homeene.model.User;
import com.homeene.service.PersistentLoginService;
import com.homeene.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class DingController {

	@Resource
	private UserService userService;

	@Resource
	private PersistentLoginService persistentLoginService;

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
	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
	public String getUser(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		// String accessToken = AuthHelper.getAccessToken();
		// System.out.println(code.get("code"));
		// CorpUserBaseInfo userBaseInfo = UserHelper.getUserInfo(accessToken,
		// code.get("code"));
		// User user = userService.selectByUserId(userBaseInfo.getUserid());
		// if (user == null)
		// {
		// user = new User();
		// CorpUserDetail userDetail = UserHelper.getUser(accessToken,
		// userBaseInfo.getUserid());
		// user.setActive(userDetail.getActive());
		// user.setCreateTime(new Date());
		// user.setMobile(userDetail.getMobile());
		// user.setName(userDetail.getName());
		// user.setTel(userDetail.getTel());
		// user.setUserid(userDetail.getUserid());
		// userService.insert(user);
		// }@RequestBody Map<String, String> code,
		User u = userService.selectByUserId("0438175833697878");
		String cookieValue = persistentLoginService.addCookie(u);
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
}
