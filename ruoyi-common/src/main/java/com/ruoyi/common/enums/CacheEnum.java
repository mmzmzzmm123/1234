package com.ruoyi.common.enums;

/**
 * @Title: CacheEnum
 * @Author itmei
 * @Package com.ruoyi.common.constant
 * @Date 2023/10/24 21:46
 * @description: 缓存key常量枚举
 */
public enum CacheEnum {
    /**
     * 用户信息
     */
    LOGIN_TOKEN_KEY("login_tokens:", "用户信息"),

    /**
     * 配置信息
     */
    SYS_CONFIG_KEY("sys_config:", "配置信息"),

    /**
     * 数据字典
     */
    SYS_DICT_KEY("sys_dict:", "数据字典"),

    /**
     * 验证码
     */
    CAPTCHA_CODE_KEY("captcha_codes:", "验证码"),

    /**
     * 防重提交
     */
    REPEAT_SUBMIT_KEY("repeat_submit:", "防重提交"),

    /**
     * 限流处理
     */
    RATE_LIMIT_KEY("rate_limit:", "限流处理"),

    /**
     * 密码错误次数
     */
    PWD_ERR_CNT_KEY("pwd_err_cnt:", "密码错误次数"),

    /**
     * 平台登录用户信息
     */
    QUERY_USER_DETAIL_DATA_ID_KEY("pu_login_user_info:","平台登录用户信息"),

    /**
     * 平台用户信息
     */
    QUERY_USER_PHONE_KEY("pu_user_info:","平台用户信息");

    private String code;
    private String msg;

    CacheEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    CacheEnum() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
