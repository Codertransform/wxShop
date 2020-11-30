package com.mikes.shop.entity;

public class AccessToken {
    private String id;
    private String accessToken;
    private int expiresIn;
    private long expiresAfter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getExpiresAfter() {
        return expiresAfter;
    }

    public void setExpiresAfter(long expiresAfter) {
        this.expiresAfter = expiresAfter;
    }
}
