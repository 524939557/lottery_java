package com.homeene.dao;

import com.homeene.model.Times;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface TimesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Times record);

    Times selectByPrimaryKey(Integer id);

    List<Times> selectAll();

    int updateByPrimaryKey(Times record);

	Times selectByCreateTime(Map<String,String> map);
}