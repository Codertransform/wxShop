package com.mikes.shop.dao;

import com.mikes.shop.entity.AccessToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccessTokenDao {

    AccessToken get();

    void insert(AccessToken newtoken);

    void update(AccessToken newtoken);
}
