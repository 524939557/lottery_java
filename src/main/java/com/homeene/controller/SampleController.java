package com.homeene.controller;

import static org.mockito.Mockito.times;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.homeene.model.Answer;
import com.homeene.model.Options;
import com.homeene.model.Question;
import com.homeene.model.Times;
import com.homeene.model.User;
import com.homeene.service.AnswerService;
import com.homeene.service.CookieService;
import com.homeene.service.OptionService;
import com.homeene.service.QuestionService;
import com.homeene.service.TimesService;

@RestController
@RequestMapping(value = "/sample")
public class SampleController {

	@Resource
	private QuestionService questionService;

	@Resource
	private OptionService optionService;
	
	@Resource
	private AnswerService answerService;
	
	@Resource
	private TimesService timesService;
	
	@Resource
	private CookieService cookieService;

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public List<Question> selectQuestion() {
		List<Question> questionList = questionService.selectQuestion();
		return questionList;
	}

	@RequestMapping(value = "/selectOne", method = RequestMethod.GET)
	public Map<String, Object> selectOne(HttpServletRequest req,HttpServletResponse rsp) throws UnsupportedEncodingException {
		User u=cookieService.cookieToUser(req);
		if(u!=null) {
			if(this.checkTimes(u.getUserid())) {
				Question q = questionService.selectone();
				List<Options> option=optionService.selectOptionByQuestionId(q.getId());
				Answer answer=answerService.selectByQuestion(q.getId());
				Map<String,Object> map=new HashMap<>();
				map.put("question", q);
				map.put("options",option);
				map.put("answer", answer);
				return map;
			}else {
				rsp.setStatus(HttpStatus.SC_NOT_ACCEPTABLE);//over 3times today
			}
		}
		return null;
	}
	
	public boolean checkTimes(String userId) {
		Times t=timesService.selectByCreateTime(userId,new Date());
		if(t==null) {
			t=new Times();
			t.setCreateTime(new Date());
			t.setTimes(1);
			t.setUserId(userId);
			timesService.insert(t);
		}else {
			int times=t.getTimes();
			if(times>3) {
				return false;
			}else {
				t.setTimes(times+1);
				timesService.update(t);
			}
		}
		return true;
	}
}	
