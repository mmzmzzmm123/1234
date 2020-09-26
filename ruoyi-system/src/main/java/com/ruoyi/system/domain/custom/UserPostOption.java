package com.ruoyi.system.domain.custom;

public class UserPostOption {

    private Long userId;

    private String userName;

    private String postCode;

    public String getPostCode() {
        return postCode;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserPostOption{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
