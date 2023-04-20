package com.boot.common.core.domain.model;

import lombok.Data;

/**
 * 微信用户登录对象
 * 
 * @author boot
 */
@Data
public class WXLoginBody  extends LoginBody
{
    /**
     * 小程序授权码
     */
    private String code;

    /**
     * 小程序appid
     */
    private String appid;

}
