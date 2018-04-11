package com.homeene.service;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.homeene.common.Constants;
import com.homeene.dao.AccessTokenMapper;
import com.homeene.model.AccessToken;
import com.homeene.utils.HttpUtils;

@Service
public class AccessTokenService {

	@Resource
	private AccessTokenMapper accessTokenMapper;

	public int deleteByPrimaryKey(String openid) {
		return accessTokenMapper.deleteByPrimaryKey(openid);
	}

	public int insert(AccessToken record) {
		return accessTokenMapper.insert(record);
	}

	public AccessToken selectByPrimaryKey(String openid) {
		return accessTokenMapper.selectByPrimaryKey(openid);
	}

	public List<AccessToken> selectAll() {
		return accessTokenMapper.selectAll();
	}

	public int updateByPrimaryKey(AccessToken record) {
		return accessTokenMapper.updateByPrimaryKey(record);
	}

	/**
	 * 通过openid和code获取的accesstoken 得到用户信息
	 * 
	 * @param token
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public String getPersonInfoByAccessToken(String token, String openid) throws Exception {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid
				+ "&lang=zh_CN";
		String result = HttpUtils.get(url);
		return result;

	}

	public String getGlobalAccessToken() throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constants.APP_ID
				+ "&secret=" + Constants.AppSecret;
		String result = HttpUtils.get(url);
		JSONObject json = new JSONObject(result);
		return json.getString("access_token");
	}

	public String getAccessToken(String code) throws Exception {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.APP_ID + "&secret="
				+ Constants.AppSecret + "&code=" + code + "&grant_type=authorization_code";
		String result = HttpUtils.get(url);
		return result;
	}

	public String getTicket(String token) throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
		String result = HttpUtils.get(url);
		JSONObject json = new JSONObject(result);
		return json.getString("ticket");
	}

	public Boolean checkToken(String token, String openid) throws Exception {
		String url = "https://api.weixin.qq.com/sns/auth?access_token=" + token + "&openid=" + openid;
		String result = HttpUtils.get(url);
		JSONObject json = new JSONObject(result);
		String errcode = json.getString("errcode");
		return errcode == "0";
	}

	public String getRefreshToken(String token) throws Exception {
		String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + Constants.APP_ID
				+ "&grant_type=refresh_token&refresh_token=" + token;
		return HttpUtils.get(url);
	}

	public String getPersonInfoByGlobalAccessToken(String record, String openid) throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + record + "&openid=" + openid
				+ "&lang=zh_CN";
		return HttpUtils.get(url);
	}

}
