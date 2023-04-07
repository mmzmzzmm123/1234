package com.ruoyi.system.biz.token;

public class Constants {

    /**
     * 令牌claims key：用户 uuid(唯一标识)
     */
    public static final String LOGIN_USER_KEY = "jf_login_user_key";

    /**
     * 令牌claims key：用户id(业务系统的用户 id)
     */
    public static final String LOGIN_TIME = "jf_login_time";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "jf_login_tokens:";
}


