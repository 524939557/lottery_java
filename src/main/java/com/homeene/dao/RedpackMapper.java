package com.homeene.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.homeene.model.Redpack;

@Mapper
public interface RedpackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Redpack record);

    Redpack selectByPrimaryKey(Integer id);

    List<Redpack> selectAll();

    int updateByPrimaryKey(Redpack record);

	Integer getTotalAmount();

	Redpack selectByOpenId(String openid);
}