package com.homeene.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.homeene.dao.UserMapper;
import com.homeene.model.User;

@Service
public class UserService {

	@Resource
	private UserMapper userMapper;

	public int insert(User user) {
		return userMapper.insert(user);
	}
	public User selectByUserId(String string) {
		return userMapper.selectByUserId(string);
	}
}
