package com.homeene.dao;

import com.homeene.model.MyAward;
import java.util.List;

public interface MyAwardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MyAward record);

    MyAward selectByPrimaryKey(Integer id);

    List<MyAward> selectAll();

    int updateByPrimaryKey(MyAward record);

	List<MyAward> selectMyAward(String userId);
}