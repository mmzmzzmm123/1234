package com.stdiet.system.domain;

import java.io.Serializable;

public class CusSalesman implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CusSalesman{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
