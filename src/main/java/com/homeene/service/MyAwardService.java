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
}
