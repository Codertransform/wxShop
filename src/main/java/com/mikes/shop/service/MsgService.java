package com.mikes.shop.service;

import com.mikes.shop.dao.MsgDao;
import com.mikes.shop.entity.MsgEntity;
import com.mikes.shop.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsgService {

    @Autowired
    private MsgDao msgDao;

    public void save(String toUserName, String fromUserName, String createTime, String msgType, String content, String msgId) {
        MsgEntity msg = new MsgEntity();
        msg.setToUserName(toUserName);
        msg.setFromUserName(fromUserName);
        msg.setCreateTime(Long.valueOf(createTime));
        msg.setMsgType(msgType);
        msg.setContent(content);
        msg.setMsgId(msgId);
        msg.setId(CommonUtils.uuid());
        msg.setDelFlag(CommonUtils.DEL_FLAG_NORMAL);
        msgDao.insert(msg);
    }
}
