package com.homeene.dao;

import com.homeene.model.AccessToken;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AccessTokenMapper {

	int deleteByPrimaryKey(String openid);

	int insert(AccessToken record);

	AccessToken selectByPrimaryKey(String openid);

	List<AccessToken> selectAll();

	int updateByPrimaryKey(AccessToken record);
}