package com.homeene.service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.homeene.alibaba.demo.Env;
import com.homeene.dao.PersistentLoginsMapper;
import com.homeene.model.PersistentLogins;
import com.homeene.model.User;
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

	public int insertSelective(PersistentLogins record) {
		// TODO Auto-generated method stub
		return persistentLogins.insertSelective(record);
	}

	public PersistentLogins selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return persistentLogins.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(PersistentLogins record) {
		// TODO Auto-generated method stub
		return persistentLogins.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKey(PersistentLogins record) {
		// TODO Auto-generated method stub
		return persistentLogins.updateByPrimaryKeySelective(record);
	}

	public PersistentLogins selectByUsernameAndSeries(
			@Param("mobile") String mobile, @Param("series") String series) {
		// TODO Auto-generated method stub
		return persistentLogins.selectByUsernameAndSeries(mobile, series);
	}

	public PersistentLogins selectByUsername(@Param("mobile") String mobile) {
		// TODO Auto-generated method stub
		return persistentLogins.selectByUsername(mobile);
	}

	public String addCookie(User user){
		// 有效期
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1); // 一个月
		Date validTime = calendar.getTime();
		// 精确到分的时间字符串
		String timeString = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.HOUR_OF_DAY) + "-"
				+ calendar.get(Calendar.MINUTE);

		// sha256加密用户信息
		String userInfoBySha256 = EncryptionUtil.sha256Hex(user.getUserid() + "_" + user.getMobile() + "_"
				+ timeString + "_" +  Env.CORP_ID);

		// UUID值
		String uuidString = UUID.randomUUID().toString();
		// Cookie值
		String cookieValue = EncryptionUtil.base64Encode(user.getUserid() + ":" + uuidString);
		// 在数据库中保存自动登录记录（如果已有该用户的记录则更新记录）
		PersistentLogins pLogin = this.selectByUsername(user.getMobile());
		if (pLogin == null) {
			pLogin = new PersistentLogins();
			pLogin.setUsername(user.getUserid());
			pLogin.setSeries(uuidString);
			pLogin.setToken(userInfoBySha256);
			pLogin.setValidtime(validTime);
			this.insertSelective(pLogin);
		}else{
			pLogin.setSeries(uuidString);
			pLogin.setToken(userInfoBySha256);
			pLogin.setValidtime(validTime);
			this.updateByPrimaryKeySelective(pLogin);
		}
		// 保存cookie
		return cookieValue;
	}
}
