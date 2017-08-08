package com.homeene.dao;

import com.homeene.model.Info;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface InfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Info record);

    Info selectByPrimaryKey(Integer id);

    List<Info> selectAll();

    int updateByPrimaryKey(Info record);
}