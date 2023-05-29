package com.ruoyi.common.core.domain.model;

import lombok.Data;

@Data
public class WxLoginBody {
    /**
     * 微信应用名称，自己定义，方便记忆和使用。也可使用appId
     */
    private String appName;
    /**
     * 微信应用appId, 也可使用appName
     */
    private String appId;
    /**
     * 登录凭据
     */
    private String code;
}
