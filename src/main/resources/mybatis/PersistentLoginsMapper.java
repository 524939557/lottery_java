package com.huabane.dao;

import com.huabane.model.PersistentLogins;

public interface PersistentLoginsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PersistentLogins record);

    int insertSelective(PersistentLogins record);

    PersistentLogins selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PersistentLogins record);

    int updateByPrimaryKey(PersistentLogins record);
}