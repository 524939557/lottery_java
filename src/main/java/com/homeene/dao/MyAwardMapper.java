package com.homeene.dao;

import com.homeene.model.MyAward;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface MyAwardMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(MyAward record);

    MyAward selectByPrimaryKey(Integer id);

    List<MyAward> selectAll();

    int updateByPrimaryKey(MyAward record);

	List<MyAward> selectMyAward(String userId);
	
	MyAward selectByAwardId(HashMap<String, Object> map);
	
	int selectMyCollect(String userId);
}