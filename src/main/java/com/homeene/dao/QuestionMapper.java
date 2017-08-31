package com.homeene.dao;

import com.homeene.model.Question;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface QuestionMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Question record);

	Question selectByPrimaryKey(Integer id);

	List<Question> selectAll();

	int updateByPrimaryKey(Question record);

	Question selectOne(Integer type);
}