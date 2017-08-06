package com.homeene.dao;

import com.homeene.model.Info;
import java.util.List;

public interface InfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Info record);

    Info selectByPrimaryKey(Integer id);

    List<Info> selectAll();

    int updateByPrimaryKey(Info record);
}