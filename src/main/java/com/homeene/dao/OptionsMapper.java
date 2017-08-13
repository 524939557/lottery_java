package com.homeene.dao;

import com.homeene.model.Options;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface OptionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Options record);

    Options selectByPrimaryKey(Integer id);

    List<Options> selectAll();

    int updateByPrimaryKey(Options record);

	List<Options> selectByQuestionId(int questionId);
}