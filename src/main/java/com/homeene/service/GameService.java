package com.homeene.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.homeene.dao.GameMapper;
import com.homeene.model.Game;

@Service
public class GameService {

	@Resource
	private GameMapper gameMapper;

	public int insert(Game user) {
		return gameMapper.insert(user);
	}

	public Game selectByUserId(String userId) {
		return gameMapper.selectByUserId(userId);
	}

	public int update(Game user) {
		return gameMapper.updateByPrimaryKey(user);
	}

	public List<Game> selectByCollect(int collect) {
		return gameMapper.selectByCollect(collect);
	}

	public Game selectLotteryByUserId(Integer id) {
		return gameMapper.selectLotteryByUserId(id);
	}
	
	public List<Game> selectUser(){
		return gameMapper.selectAll();
	}

}
