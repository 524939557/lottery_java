package com.homeene.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeene.model.PersistentLogins;
import com.homeene.model.User;
import com.homeene.utils.EncryptionUtil;

@Service
public class CookieService  {
	@Resource
	private PersistentLoginService persistentLoginsServiceImpl;
	@Resource
	private UserService userService;

	public User cookieToUser(HttpServletRequest request) throws UnsupportedEncodingException {

		String header=request.getHeader("Authorization");
		System.out.println("Authorization-------------"+header);
		if (header!=null) {
			String cookieValue = EncryptionUtil.base64Decode(header);
			System.out.println("cookieservice-------------"+cookieValue);
			String[] cValues = cookieValue.split(":");
			if (cValues.length == 2) {
				String userId = cValues[0]; //
				return userService.selectByUserId(userId);
			}
		}
		return null;
	
	}

}
