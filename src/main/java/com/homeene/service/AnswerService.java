package com.homeene.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.homeene.dao.AnswerMapper;
import com.homeene.model.Answer;

@Service
public class AnswerService {

	@Resource
	private AnswerMapper answerMapper;

	public Answer selectByQuestion(int id) {
		return answerMapper.selectByQuestion(id);
	}
}
