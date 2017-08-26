package com.homeene.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.homeene.model.PersistentLogins;

@Mapper
public interface PersistentLoginsMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(PersistentLogins record);

	int insertSelective(PersistentLogins record);

	PersistentLogins selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(PersistentLogins record);

	int updateByPrimaryKey(PersistentLogins record);

	PersistentLogins selectByUsernameAndSeries(HashMap<String, String> map);

	PersistentLogins selectByUserId(String userId);

}