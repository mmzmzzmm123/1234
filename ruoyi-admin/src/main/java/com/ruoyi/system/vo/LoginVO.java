package com.ruoyi.system.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 登录返回对象
 */
@Data
@Builder
public class LoginVO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 微信小程序开放id
     */
    private String openid;
    /**
     * 认证token
     */
    private String token;
}
