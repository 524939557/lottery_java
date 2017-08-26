package com.homeene.dao;

import com.homeene.model.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

	User selectByUserId(String string);

	List<User> selectByCollect(int collect);

	User selectLotteryByUserId(String userId);
}