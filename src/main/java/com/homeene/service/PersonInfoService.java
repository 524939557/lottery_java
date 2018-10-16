package com.homeene.service;

import com.homeene.dao.PersonInfoMapper;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.homeene.model.PersonInfo;

@Service
public class PersonInfoService {

	@Resource
	private PersonInfoMapper personInfoMapper;

	public int insert(PersonInfo record) {
		return personInfoMapper.insert(record);
	}

	public int deleteByPrimaryKey(String openid) {
		return personInfoMapper.deleteByPrimaryKey(openid);
	}

	public PersonInfo selectByPrimaryKey(String openid) {
		return personInfoMapper.selectByPrimaryKey(openid);
	}

	public List<PersonInfo> selectAll() {
		return personInfoMapper.selectAll();
	}

	public int updateByPrimaryKey(PersonInfo record) {
		return personInfoMapper.updateByPrimaryKey(record);
	}

	public PersonInfo insertAccessPerson(String person) throws JSONException {
		JSONObject jsonObject = new JSONObject(person);
		try
		{
			String nickname = jsonObject.getString("nickname");
			nickname = URLEncoder.encode(nickname, "utf-8");
			PersonInfo pi = new PersonInfo();
			pi.setOpenid(jsonObject.getString("openid"));
			pi.setNickname(nickname);
			pi.setSex(jsonObject.getInt("sex"));
			pi.setLanguage(jsonObject.getString("language"));
			pi.setProvince(jsonObject.getString("province"));
			pi.setCountry(jsonObject.getString("country"));
			pi.setHeadimgurl(jsonObject.getString("headimgurl"));
			pi.setUnionid(jsonObject.getString("unionid"));
			insert(pi);
			return pi;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public PersonInfo insertPerson(String person) throws JSONException {
		JSONObject jsonObject = new JSONObject(person);
		try
		{
			String nickname = jsonObject.getString("nickname");
			nickname = URLEncoder.encode(nickname, "utf-8");
			JSONArray tagid_list=jsonObject.getJSONArray("tagid_list");
			String tagid=tagid_list.join(",");
			PersonInfo pi = new PersonInfo();
			pi.setSubscribe(jsonObject.getInt("subscribe"));
			pi.setOpenid(jsonObject.getString("openid"));
			pi.setNickname(nickname);
			pi.setSex(jsonObject.getInt("sex"));
			pi.setLanguage(jsonObject.getString("language"));
			pi.setProvince(jsonObject.getString("province"));
			pi.setCity(jsonObject.getString("city"));
			pi.setCountry(jsonObject.getString("country"));
			pi.setHeadimgurl(jsonObject.getString("headimgurl"));
			pi.setSubscribeTime(jsonObject.getLong("subscribe_time"));
			pi.setRemark(jsonObject.getString("remark"));
			pi.setGroupid(jsonObject.getInt("groupid"));
			pi.setTagidList(tagid);
			PersonInfo pi_old=this.selectByPrimaryKey(pi.getOpenid());
			if(pi_old==null) {
				insert(pi);
			}else {
				updateByPrimaryKey(pi);
			}
			return pi;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
