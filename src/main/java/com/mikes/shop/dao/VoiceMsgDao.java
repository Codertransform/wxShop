package com.mikes.shop.dao;

import com.mikes.shop.entity.VoiceEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VoiceMsgDao {

    void insert(VoiceEntity voice);
}
