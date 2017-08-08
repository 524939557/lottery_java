package com.homeene.dao;

import com.homeene.model.Option;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface OptionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Option record);

    Option selectByPrimaryKey(Integer id);

    List<Option> selectAll();

    int updateByPrimaryKey(Option record);

	List<Option> selectByQuestionId(int questionId);
}