package com.onethinker.user.dto;

import lombok.Data;

/**
 * @author : yangyouqi
 * @date : 2023/10/23 0023 21:28
 */
@Data
public class PlatformUserReqDTO {

    /**
     * 微信平台
     */
    public static final String SOURCE_TYPE_WX = "1";
    /**
     * 账户密码登录
     */
    public static final String SOURCE_TYPE_ACCOUNT = "2";

    /**
     * 平台用户来源 1：微信平台登录 2：账户密码登录
     */
    private String sourceType;

    private String userName;
    private String password;

    private String code;
    private String openId;
    private String phone;

    private String avatarUrl;

    private String nickName;
}
