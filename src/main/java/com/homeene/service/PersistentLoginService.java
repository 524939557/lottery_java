package com.homeene.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.homeene.common.Constants;
import com.homeene.dao.PersistentLoginsMapper;
import com.homeene.model.PersistentLogins;
import com.homeene.model.Game;
import com.homeene.utils.EncryptionUtil;

@Service
public class PersistentLoginService {

	@Resource
	private PersistentLoginsMapper persistentLogins;

	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return persistentLogins.deleteByPrimaryKey(id);
	}

	public int insert(PersistentLogins record) {
		// TODO Auto-generated method stub
		return persistentLogins.insert(record);
	}

	public PersistentLogins selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return persistentLogins.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(PersistentLogins record) {
		// TODO Auto-generated method stub
		return persistentLogins.updateByPrimaryKey(record);
	}

	public PersistentLogins selectByUsernameAndSeries(String userId, String series) {
		// TODO Auto-generated method stub
		HashMap<String,String> map=new HashMap<>();
		map.put("userId", userId);
		map.put("series", series);
		return persistentLogins.selectByUsernameAndSeries(map);
	}

	public PersistentLogins selectByUserId(String userId) {
		// TODO Auto-generated method stub
		return persistentLogins.selectByUserId(userId);
	}

	public String addCookie(Game user) {
		// 有效期
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1); // 一个月
		Date validTime = calendar.getTime();
		// 精确到分的时间字符串
		String timeString = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.HOUR_OF_DAY) + "-"
				+ calendar.get(Calendar.MINUTE);

		// sha256加密用户信息
		String userInfoBySha256 = EncryptionUtil
				.sha256Hex(user.getUserid() + "_"  + timeString + "_" + Constants.GobleToken);

		// UUID值
		String uuidString = UUID.randomUUID().toString();
		// Cookie值
		String cookieValue = EncryptionUtil.base64Encode(user.getUserid() + ":" + uuidString);
		// 在数据库中保存自动登录记录（如果已有该用户的记录则更新记录）
		PersistentLogins pLogin = this.selectByUserId(user.getUserid());
		if (pLogin == null)
		{
			pLogin = new PersistentLogins();
			pLogin.setUsername(user.getUserid());
			pLogin.setSeries(uuidString);
			pLogin.setToken(userInfoBySha256);
			pLogin.setValidtime(validTime);
			this.insert(pLogin);
		} else
		{
			pLogin.setSeries(uuidString);
			pLogin.setToken(userInfoBySha256);
			pLogin.setValidtime(validTime);
			this.updateByPrimaryKey(pLogin);
		}
		// 保存cookie
		return cookieValue;
	}
}
