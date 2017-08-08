package com.homeene.dao;

import com.homeene.model.Answer;
import java.util.List;

public interface AnswerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Answer record);

    Answer selectByPrimaryKey(Integer id);

    List<Answer> selectAll(int id);

    int updateByPrimaryKey(Answer record);
    
    Answer selectByQuestion(Integer id);
}