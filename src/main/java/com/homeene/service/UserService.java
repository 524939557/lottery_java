package com.homeene.service;

import java.util.List;

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

	public User selectByUserId(String userId) {
		return userMapper.selectByUserId(userId);
	}

	public int update(User user) {
		return userMapper.updateByPrimaryKey(user);
	}

	public List<User> selectByCollect(int collect) {
		return userMapper.selectByCollect(collect);
	}

	public User selectLotteryByUserId(String userId) {
		return userMapper.selectLotteryByUserId(userId);
	}
}
