package com.mikes.shop.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MsgEntity extends BaseEntity {
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
