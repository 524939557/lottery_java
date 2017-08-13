package com.homeene.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.homeene.dao.OptionsMapper;
import com.homeene.model.Options;

@Service
public class OptionService {

	@Resource
	private OptionsMapper optionMapper;
	public List<Options> selectOptionByQuestionId(int questionId){
		return optionMapper.selectByQuestionId(questionId);
	}
}
