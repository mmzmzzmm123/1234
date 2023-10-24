package com.ruoyi.onethinker.dto;

/**
 * @author : yangyouqi
 * @date : 2023/10/23 0023 21:28
 */
public class PlatformUserReqDTO {

    public static final String SOURCE_TYPE_WX = "1";
    public static final String SOURCE_TYPE_ACCOUNT = "2";

    /**
     * 平台用户来源 1：微信平台登录 2：账户密码登录
     */
    private String sourceType;

    private String userName;
    private String password;
    private String openId;
    private String phone;

    private String avatarUrl;

    private String nickName;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
