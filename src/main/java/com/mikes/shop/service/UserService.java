package com.mikes.shop.service;

import com.mikes.shop.dao.UserDao;
import com.mikes.shop.entity.User;
import com.mikes.shop.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User get(User user){
        return userDao.get(user);
    }

    public void save(User user){
        if (user.getId() != null){
            userDao.update(user);
        }else {
            user.setId(CommonUtils.uuid());
            userDao.insert(user);
        }
    }

    public void delete(User user){
        userDao.delete(user);
    }
}
