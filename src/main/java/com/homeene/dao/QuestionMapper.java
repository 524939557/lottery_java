package com.homeene.dao;

import com.homeene.model.Question;
import java.util.List;

public interface QuestionMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Question record);

	Question selectByPrimaryKey(Integer id);

	List<Question> selectAll();

	int updateByPrimaryKey(Question record);

	Question selectOne();
}