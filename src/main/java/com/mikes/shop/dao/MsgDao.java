package com.mikes.shop.dao;

import com.mikes.shop.entity.MsgEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MsgDao {

    void insert(MsgEntity msg);
}
