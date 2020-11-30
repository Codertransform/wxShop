package com.mikes.shop.service;

import com.mikes.shop.dao.AccessTokenDao;
import com.mikes.shop.entity.AccessToken;
import com.mikes.shop.utils.CommonUtils;
import com.mikes.shop.utils.WeiXinUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class AccessTokenService {

    @Autowired
    private AccessTokenDao tokenDao;

    public AccessToken checkAccessToken() throws IOException {
        AccessToken token = tokenDao.get();
        if (token == null) {
            token = WeiXinUtils.getAccessToken();
            token.setId(CommonUtils.uuid());
            token.setExpiresAfter(new Date().getTime() + (token.getExpiresIn()*1000));
            tokenDao.insert(token);
            return token;
        }else if (new Date().getTime() > token.getExpiresAfter()){
            token = WeiXinUtils.getAccessToken();
            token.setId(token.getId());
            token.setExpiresAfter(new Date().getTime() + (token.getExpiresIn()*1000));
            tokenDao.update(token);
            return token;
        }else {
            return token;
        }
    }
}
