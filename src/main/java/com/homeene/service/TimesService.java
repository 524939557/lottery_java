package com.homeene.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.homeene.dao.TimesMapper;
import com.homeene.model.Times;

@Service
public class TimesService {

	@Resource
	private TimesMapper timesMapper;
	public int insert(Times t) {
		return timesMapper.insert(t);
	}
	public int update(Times t) {
		return timesMapper.updateByPrimaryKey(t);
	} 
	public Times selectByCreateTime(String userid,Date time) {
		return timesMapper.selectByCreateTime(userid,time);
	}
}
