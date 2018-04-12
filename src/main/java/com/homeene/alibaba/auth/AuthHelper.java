package com.homeene.alibaba.auth;

import java.text.SimpleDateFormat;
import java.util.Timer;
import javax.annotation.Resource;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.common.SdkInitException;
import com.dingtalk.open.client.common.ServiceException;
import com.dingtalk.open.client.common.ServiceNotExistException;
import com.homeene.alibaba.utils.FileUtils;
import com.homeene.common.Constants;
import com.homeene.service.AccessTokenService;

public class AuthHelper {

	// public static String jsapiTicket = null;
	// public static String accessToken = null;
	public static Timer timer = null;
	// 调整到1小时50分钟
	public static final long cacheTime = 1000 * 60 * 55 * 2;
	public static long currentTime = 0 + cacheTime + 1;
	public static long lastTime = 0;
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Resource private static AccessTokenService accessTokenService;

	/*
	 * 在此方法中，为了避免频繁获取access_token，
	 * 在距离上一次获取access_token时间在两个小时之内的情况，
	 * 将直接从持久化存储中读取access_token
	 * 
	 * 因为access_token和jsapi_ticket的过期时间都是7200秒
	 * 所以在获取access_token的同时也去获取了jsapi_ticket
	 * 注：jsapi_ticket是在前端页面JSAPI做权限验证配置的时候需要使用的
	 * 具体信息请查看开发者文档--权限验证配置
	 */
	public static String getAccessToken() throws Exception {
		long curTime = System.currentTimeMillis();
		JSONObject accessTokenValue = (JSONObject) FileUtils.getValue("accesstoken", Constants.GobleToken);
		String accToken = "";
		String jsTicket = "";
		JSONObject jsontemp = new JSONObject();
		if (accessTokenValue == null || curTime - accessTokenValue.getLong("begin_time") >= cacheTime) {
			try
			{
	        accToken = accessTokenService.getGlobalAccessToken();
			// save accessToken
			JSONObject jsonAccess = new JSONObject();
			jsontemp.clear();
			jsontemp.put("access_token", accToken);
			jsontemp.put("begin_time", curTime);
			jsonAccess.put(Constants.GobleToken, jsontemp);
			FileUtils.write2File(jsonAccess, "accesstoken");
			
			if(accToken.length() > 0){
				jsTicket = accessTokenService.getTicket(accToken);
				JSONObject jsonTicket = new JSONObject();
				jsontemp.clear();
				jsontemp.put("ticket", jsTicket);
				jsontemp.put("begin_time", curTime);
				jsonTicket.put(Constants.GobleToken, jsontemp);
				FileUtils.write2File(jsonTicket, "jsticket");
			}
		} catch (SdkInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} else {
			return accessTokenValue.getString("access_token");
		}

		return accToken;
	}

	// 正常的情况下，jsapi_ticket的有效期为7200秒，所以开发者需要在某个地方设计一个定时器，定期去更新jsapi_ticket
	public static String getJsapiTicket(String accessToken) throws Exception {
		JSONObject jsTicketValue = (JSONObject) FileUtils.getValue("jsticket", Constants.GobleToken);
		long curTime = System.currentTimeMillis();
		String jsTicket = "";
		 if (jsTicketValue == null || curTime - jsTicketValue.getLong("begin_time") >= cacheTime) {
			try {
				jsTicket = accessTokenService.getTicket(accessToken);
				JSONObject jsonTicket = new JSONObject();
				JSONObject jsontemp = new JSONObject();
				jsontemp.clear();
				jsontemp.put("ticket", jsTicket);
				jsontemp.put("begin_time", curTime);
				jsonTicket.put(Constants.GobleToken, jsontemp);
				FileUtils.write2File(jsonTicket, "jsticket");
			} catch (SdkInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsTicket;
		 } else {
			 return jsTicketValue.getString("ticket");
		 }
	}

	
}
