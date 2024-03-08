package com.ruoyi.system.vo;

import lombok.Data;

/**
 * 登录参数
 */
@Data
public class LoginBody {
    /**
     * 微信小程序code
     */
    private String code;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 手机号
     */
    private String phoneNumber;
}
