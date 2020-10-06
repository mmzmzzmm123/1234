package com.ruoyi.custom.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;

public class CusUserPost implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String userName;

    private String postCode;

    private String postId;

    private String postName;

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostName() {
        return postName;
    }

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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "UserPostOption{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", postCode='" + postCode + '\'' +
                ", postid='" + postId + '\'' +
                ", postName='" + postName + '\'' +
                '}';
    }
}
