package com.homeene.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.homeene.dao.RedpackMapper;
import com.homeene.model.Redpack;
@Service
public class RedpackService {
	@Resource
	private RedpackMapper redpackMapper;

	public Integer getTotalAmount() {
		// TODO Auto-generated method stub
		return redpackMapper.getTotalAmount();
	}

	public void insert(Redpack redpack) {
		// TODO Auto-generated method stub
		redpackMapper.insert(redpack);
	}

}
