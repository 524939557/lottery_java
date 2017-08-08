package com.homeene.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.homeene.model.Answer;
import com.homeene.model.Option;
import com.homeene.model.Question;
import com.homeene.service.AnswerService;
import com.homeene.service.OptionService;
import com.homeene.service.QuestionService;

@RestController
@RequestMapping(value = "/sample")
public class SampleController {

	@Resource
	private QuestionService questionService;

	@Resource
	private OptionService optionService;
	
	@Resource
	private AnswerService answerService;

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public List<Question> selectQuestion() {
		List<Question> questionList = questionService.selectQuestion();
		return questionList;
	}

	@RequestMapping(value = "/selectOne", method = RequestMethod.GET)
	public Map<String, Object> selectOne() {
		Question q = questionService.selectone();
		List<Option> option=optionService.selectOptionByQuestionId(q.getId());
		Answer answer=answerService.selectByQuestion(q.getId());
		Map<String,Object> map=new HashMap<>();
		map.put("question", q);
		map.put("options",option);
		map.put("answer", answer);
		return map;
	}
}
