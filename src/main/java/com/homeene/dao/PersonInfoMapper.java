package com.homeene.dao;

import org.apache.ibatis.annotations.Mapper;
import com.homeene.model.PersonInfo;
import java.util.List;

@Mapper
public interface PersonInfoMapper {

	int deleteByPrimaryKey(String openid);

	int insert(PersonInfo record);

	PersonInfo selectByPrimaryKey(String openid);

	List<PersonInfo> selectAll();

	int updateByPrimaryKey(PersonInfo record);
}