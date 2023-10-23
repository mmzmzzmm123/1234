package com.ruoyi.onethinker.dto;

/**
 * @author : yangyouqi
 * @date : 2023/10/23 0023 21:28
 */
public class PlatformUserReqDTO {

    public static final String SOURCE_TYPE_ACCOUNT = "1";
    public static final String SOURCE_TYPE_WX = "2";
    /**
     * 平台用户来源 1：账户密码登录 2：微信平台登录
     */
    private String sourceType;

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
