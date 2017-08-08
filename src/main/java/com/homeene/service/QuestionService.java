package com.homeene.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.homeene.dao.QuestionMapper;
import com.homeene.model.Question;

@Service
public class QuestionService {
	@Resource
	private QuestionMapper questionMapper;
	
	public List<Question> selectQuestion(){
		return questionMapper.selectAll();
	}
	
	public Question selectone() {
		return questionMapper.selectOne();
	}
}
