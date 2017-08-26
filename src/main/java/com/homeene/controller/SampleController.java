package com.homeene.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.homeene.model.Answer;
import com.homeene.model.Award;
import com.homeene.model.MyAward;
import com.homeene.model.Options;
import com.homeene.model.Question;
import com.homeene.model.Times;
import com.homeene.model.User;
import com.homeene.service.AnswerService;
import com.homeene.service.AwardService;
import com.homeene.service.CookieService;
import com.homeene.service.MyAwardService;
import com.homeene.service.OptionService;
import com.homeene.service.QuestionService;
import com.homeene.service.TimesService;
import com.homeene.service.UserService;

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
	private AwardService awardService;
	
	@Resource
	private MyAwardService myAwardService;
	
	@Resource
	private UserService userservice;

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
				Map<String,Object> map=new HashMap<>();
				map.put("question", q);
				map.put("options",option);
				return map;
			}else {
				rsp.setStatus(HttpStatus.SC_NOT_ACCEPTABLE);//over 3times today
			}
		}
		return null;
	}
	
	@RequestMapping(value="/checkAnswer",method=RequestMethod.POST,produces = "application/json")
	public Award checkAnswer(@RequestBody Map<String,Integer> map,HttpServletRequest req,HttpServletResponse rsp) throws UnsupportedEncodingException{
		Integer questionId=map.get("questionId");
		Integer answerId=map.get("answerId");
		Answer answer=answerService.selectByQuestion(questionId);
		User u=cookieService.cookieToUser(req);
		if(answerId==answer.getId()) {
			return this.getAward(u);
		}else {
			rsp.setStatus(HttpStatus.SC_BAD_REQUEST);
		}
		return null;
		
	}
	
	
	public Award getAward(User u) throws UnsupportedEncodingException {

		List<Award> awardList = awardService.getAward();
		List<MyAward> myward = myAwardService.selectMyAward(u.getUserid());
		Award award = awardService.lotter(awardList, myward);// 将已抽到的卡片概率分给其它，抽取一张卡片
		HashMap<String,Object> map=new HashMap<>();
		map.put("awardId", award.getId());
		map.put("userId", u.getUserid());
		MyAward myAward = myAwardService.selectMyAwardById(map);// 查看该卡是否拥有
		if (myAward == null)
		{
			myAward = new MyAward();
			myAward.setAwardId(award.getId());
			myAward.setUserId(u.getUserid());
			myAward.setTotal(1);
			myAwardService.insert(myAward);
		} else
		{
			myAward.setTotal(myAward.getTotal() + 1);
			myAwardService.update(myAward);
		}
		Integer total = myAwardService.selectMyCollect(u.getUserid());// 是否集齐21张
		if (total == 21)
		{
			u.setCollect(1);
			u.setCollectTime(new Date());
			userservice.update(u);
		}
		return award;
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
			if(times>5) {
				return false;
			}else {
				t.setTimes(times+1);
				timesService.update(t);
			}
		}
		return true;
	}
}	
