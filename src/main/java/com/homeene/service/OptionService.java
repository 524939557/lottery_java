package com.homeene.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.homeene.dao.OptionMapper;
import com.homeene.model.Option;

@Service
public class OptionService {

	@Resource
	private OptionMapper optionMapper;
	public List<Option> selectOptionByQuestionId(int questionId){
		return optionMapper.selectByQuestionId(questionId);
	}
}
