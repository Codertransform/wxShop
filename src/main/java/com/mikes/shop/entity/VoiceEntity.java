package com.mikes.shop.entity;

public class VoiceEntity extends BaseEntity {
    private String MediaId;
    private String Format;
    private String Recognition;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getRecognition() {
        return Recognition;
    }

    public void setRecognition(String recognition) {
        Recognition = recognition;
    }

    @Override
    public String toString() {
        return "VoiceEntity{" +
                "MediaId='" + MediaId + '\'' +
                ", Format='" + Format + '\'' +
                ", Recognition='" + Recognition + '\'' +
                '}';
    }
}
