package com.homeene.dao;

import com.homeene.model.GrossProfit;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
 @Mapper
public interface GrossProfitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GrossProfit record);

    GrossProfit selectByPrimaryKey(Integer id);

    List<GrossProfit> selectAll();

    int updateByPrimaryKey(GrossProfit record);
}