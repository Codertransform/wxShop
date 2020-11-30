package com.mikes.shop.dao;

import com.mikes.shop.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    User get(User user);

    void insert(User user);

    void update(User user);

    void delete(User user);
}
