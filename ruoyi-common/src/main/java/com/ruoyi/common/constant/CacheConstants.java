package com.ruoyi.common.constant;

/**
 * 缓存的key 常量
 * 
 * @author ruoyi
 */
public class CacheConstants
{
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    public static final String LOGIN_TOKEN_CACHENAME = "login_tokens";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    public static final String CAPTCHA_CODE_CACHENAME = "captcha_codes";
    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    public static final String SYS_CONFIG_CACHENAME = "sys_config";
    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";
    public static final String SYS_DICT_CACHENAME = "sys_dict";
    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";
    public static final String REPEAT_SUBMIT_CACHENAME = "repeat_submit";
    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";
    public static final String RATE_LIMIT_CACHENAME= "rate_limit";
    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    public static final String PWD_ERR_CNT_CACHENAME = "pwd_err_cnt";
}
