package com.homeene.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.homeene.dao.MyAwardMapper;
import com.homeene.model.MyAward;

@Service
public class MyAwardService {

	@Resource
	private MyAwardMapper myAwardMapper;
	
	public List<MyAward> selectMyAward(String userId){
		return myAwardMapper.selectMyAward(userId);
	}
	
	public int insert(MyAward myAward) {
		return myAwardMapper.insert(myAward);
	}
	
	public MyAward selectMyAwardById(Integer id,String userId) {
		return myAwardMapper.selectByAwardId(id,userId);
	}
	
	public int update(MyAward myAward) {
		return myAwardMapper.updateByPrimaryKey(myAward);
	}
}
