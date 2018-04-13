package com.homeene.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
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
import com.homeene.model.Game;
import com.homeene.service.AnswerService;
import com.homeene.service.AwardService;
import com.homeene.service.CookieService;
import com.homeene.service.GameService;
import com.homeene.service.MyAwardService;
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
	private AwardService awardService;

	@Resource
	private MyAwardService myAwardService;

	@Resource
	private GameService gameService;

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
	public Map<String, Object> selectOne(HttpServletRequest req, HttpServletResponse rsp)
			throws UnsupportedEncodingException {
		Game u = cookieService.cookieToUser(req);
		System.out.println("select One" + u.getUserid());
		if (u != null)
		{
			Times t = this.checkTimes(u.getUserid());
			if (t == null || t.getTimes() < 5)
			{
				Question q = questionService.selectone(u);
				List<Options> option = optionService.selectOptionByQuestionId(q.getId());
				Map<String, Object> map = new HashMap<>();
				map.put("question", q);
				map.put("options", option);
				map.put("times", t != null ? 4 - t.getTimes() : 4);
				System.out.println("select One map " + map.toString());
				return map;
			} else
			{
				rsp.setStatus(HttpStatus.SC_NOT_ACCEPTABLE);// over 3times today
			}
		}
		return null;
	}

	@RequestMapping(value = "/checkAnswer", method = RequestMethod.POST, produces = "application/json")
	public Award checkAnswer(@RequestBody Map<String, Integer> map, HttpServletRequest req, HttpServletResponse rsp)
			throws UnsupportedEncodingException {
		Integer questionId = map.get("questionId");
		Integer answerId = map.get("answerId");
		Answer answer = answerService.selectByQuestion(questionId);
		Game u = cookieService.cookieToUser(req);
		this.updateTimes(u);// 添加次数
		if (answerId == answer.getAnswerId() || answerId.equals(answer.getAnswerId()))
		{
			return this.getAward(u);
		} else
		{
			rsp.setStatus(HttpStatus.SC_BAD_REQUEST);
		}
		return null;

	}

	/**
	 * 分享得卡片
	 * 
	 * @param req
	 * @param rsp
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/shareCard", method = RequestMethod.GET)
	public Award shareCard(HttpServletRequest req, HttpServletResponse rsp) throws UnsupportedEncodingException {
		Game u = cookieService.cookieToUser(req);
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", u.getUserid());
		LocalDate date = LocalDate.now();
		map.put("date", date.toString());
		System.out.println("shareCard times" + map.toString());
		Times times = timesService.selectByCreateTime(map);
		if (times.getShare() == 0)
		{
			times.setShare(1);
			timesService.update(times);
			return this.getAward(u);
		} else
		{
			rsp.setStatus(HttpStatus.SC_NOT_ACCEPTABLE);// over 3times today
		}
		return null;
	}

	public Award getAward(Game u) throws UnsupportedEncodingException {

		List<Award> awardList = awardService.getAward();
		List<Game> collectList = gameService.selectByCollect(1);
		List<MyAward> myward = myAwardService.selectMyAward(u.getUserid());
		Award award = null;
		if (collectList.size() >= 100)
		{
			award = awardService.lotterOver(awardList, myward);
		} else
		{
			award = awardService.lotter(awardList, myward);// 将已抽到的卡片概率分给其它，抽取一张卡片
		}

		System.out.println("我抽到的卡片" + award.getId());
		award = awardService.selectById(award.getId());
		award.setIssue(award.getIssue() + 1);
		awardService.updateAward(award);// 修改卡片发放次数
		HashMap<String, Object> map = new HashMap<>();
		map.put("awardId", award.getId());
		map.put("userId", u.getUserid());
		MyAward myAward = myAwardService.selectMyAwardById(map);// 查看该卡是否拥有
		if (myAward == null)
		{
			myAward = new MyAward();
			myAward.setAwardId(award.getId());
			myAward.setUserId(u.getUserid());
			myAward.setGameId(u.getId());
			myAward.setTotal(1);
			myAwardService.insert(myAward);
		} else
		{
			myAward.setTotal(myAward.getTotal() + 1);
			myAwardService.update(myAward);
		}
		Integer total = myAwardService.selectMyCollect(u.getUserid());// 是否集齐21张
		if (total == 21&&u.getCollect()!=1)
		{
			u.setCollect(1);
			u.setCollectTime(new Date());
			gameService.update(u);
		}
		return award;
	}

	public Times checkTimes(String userId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		LocalDate date = LocalDate.now();
		map.put("date", date.toString());
		System.out.println("check times" + map.toString());
		Times t = timesService.selectByCreateTime(map);
		return t;
	}

	public void updateTimes(Game u) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", u.getUserid());
		LocalDate date = LocalDate.now();
		map.put("date", date.toString());
		System.out.println("update times" + map.toString());
		Times times = timesService.selectByCreateTime(map);
		if (times == null)
		{
			times = new Times();
			times.setDate(date.toString());
			times.setTimes(1);
			times.setUserId(u.getUserid());
			timesService.insert(times);
		} else
		{
			times.setTimes(times.getTimes() + 1);
			timesService.update(times);
		}
		u.setCurrent(0);
		gameService.update(u);
	}

}
