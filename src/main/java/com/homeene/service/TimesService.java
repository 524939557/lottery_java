package com.homeene.service;

import java.util.Date;
import java.util.Map;

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
	public Times selectByCreateTime(Map<String,String> map) {
		return timesMapper.selectByCreateTime(map);
	}
}
