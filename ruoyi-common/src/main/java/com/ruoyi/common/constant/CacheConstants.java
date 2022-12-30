package com.ruoyi.common.constant;

/**
 * 缓存的key 常量
 *
 * @author ruoyi
 */
public class CacheConstants {
    /**
     * 分割符号
     */
    public static final String SEPARATE = ":";
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens" + SEPARATE;

    /**
     * 用户会话记录
     */
    public static final String USER_TOKENS_KEY = "user_tokens" + SEPARATE;

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes" + SEPARATE;

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config" + SEPARATE;

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict" + SEPARATE;

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit" + SEPARATE;

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit" + SEPARATE;

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt" + SEPARATE;

    /**
     * 用户信息前缀
     */
    public static final String USER_KEY = "user_info" + SEPARATE;

    /**
     * 用户权限前缀
     */
    public static final String USER_PERM_KEY = "user_perm" + SEPARATE;

    /**
     * 用户未读消息数量
     */
    public static final String USER_UNREAD_COUNT_KEY = "user_unread_count" + SEPARATE;

    /**
     * 用户推送消息
     */
    public static final String USER_PUSH_MSG = "user_push_msg"+ SEPARATE;
}
