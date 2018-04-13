package com.homeene.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.homeene.dao.QuestionMapper;
import com.homeene.model.Question;
import com.homeene.model.Game;
import com.homeene.utils.PrizeMathRandom;

@Service
public class QuestionService {
	@Resource
	private QuestionMapper questionMapper;
	
	@Resource
	private GameService gameService;
	
	public List<Question> selectQuestion(){
		return questionMapper.selectAll();
	}
	
	public Question selectone(Game u) {
		Question q =new Question();
		if(u.getQuestionId()!=null&&u.getCurrent()==1) {
			q= this.selectQuestionById(u.getQuestionId());
		}else {
			int type=PrizeMathRandom.RundQuestion();
			q=this.selctQuestion(type);
			u.setQuestionId(q.getId());
			u.setCurrent(1);
			gameService.update(u);
		}
		return q;
	}
	
	public Question selctQuestion(int type) {
		return questionMapper.selectOne(type);
	}

	public Question selectQuestionById(int questionId) {
		// TODO Auto-generated method stub
		return questionMapper.selectByPrimaryKey(questionId);
	}
}
