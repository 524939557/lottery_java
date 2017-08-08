package com.homeene.dao;

import com.homeene.model.Award;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface AwardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Award record);

    Award selectByPrimaryKey(Integer id);

    List<Award> selectAll();

    int updateByPrimaryKey(Award record);
}