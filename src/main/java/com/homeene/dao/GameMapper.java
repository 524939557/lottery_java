package com.homeene.dao;

import com.homeene.model.Game;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface GameMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Game record);

    Game selectByPrimaryKey(Integer id);

    List<Game> selectAll();

    int updateByPrimaryKey(Game record);

	Game selectByUserId(String string);

	List<Game> selectByCollect(int collect);

	Game selectLotteryByUserId(Integer id);
}