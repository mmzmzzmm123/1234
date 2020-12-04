package com.ruoyi.common.constant;

/**
 * 通用常量信息
 *
 * @author ruoyi
 */
public class Constants
{
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 cache key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";
    /**
     * 验证码 cache name
     */
    public static final String CAPTCHA_CODE_CACHE = "captcha-codes";

    /**
     * 登录用户 cache key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";
    /**
     * 登录用户 cache name
     */
    public static final String LOGIN_TOKEN_CACHE = "login-tokens";

    /**
     * 防重提交 cache key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";
    /**
     * 防重提交 cache name
     */
    public static final String REPEAT_SUBMIT_CACHE = "repeat-submit";
    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";
    /**
     * 参数管理 cache name
     */
    public static final String SYS_CONFIG_CACHE = "sys-config";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";
    /**
     * 字典管理 cache name
     */
    public static final String SYS_DICT_CACHE = "sys-dict";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**验证码相关*/
    public static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";
    public static final String KAPTCHA_SESSION_DATE = "KAPTCHA_SESSION_DATE";
    public static final String KAPTCHA_SESSION_CONFIG_KEY = "kaptcha.session.key";
    public static final String KAPTCHA_SESSION_CONFIG_DATE = "kaptcha.session.date";
    public static final String KAPTCHA_BORDER = "kaptcha.border";
    public static final String KAPTCHA_BORDER_COLOR = "kaptcha.border.color";
    public static final String KAPTCHA_BORDER_THICKNESS = "kaptcha.border.thickness";
    public static final String KAPTCHA_NOISE_COLOR = "kaptcha.noise.color";
    public static final String KAPTCHA_NOISE_IMPL = "kaptcha.noise.impl";
    public static final String KAPTCHA_OBSCURIFICATOR_IMPL = "kaptcha.obscurificator.impl";
    public static final String KAPTCHA_PRODUCER_IMPL = "kaptcha.producer.impl";
    public static final String KAPTCHA_TEXTPRODUCER_IMPL = "kaptcha.textproducer.impl";
    public static final String KAPTCHA_TEXTPRODUCER_CHAR_STRING = "kaptcha.textproducer.char.string";
    public static final String KAPTCHA_TEXTPRODUCER_CHAR_LENGTH = "kaptcha.textproducer.char.length";
    public static final String KAPTCHA_TEXTPRODUCER_FONT_NAMES = "kaptcha.textproducer.font.names";
    public static final String KAPTCHA_TEXTPRODUCER_FONT_COLOR = "kaptcha.textproducer.font.color";
    public static final String KAPTCHA_TEXTPRODUCER_FONT_SIZE = "kaptcha.textproducer.font.size";
    public static final String KAPTCHA_TEXTPRODUCER_CHAR_SPACE = "kaptcha.textproducer.char.space";
    public static final String KAPTCHA_WORDRENDERER_IMPL = "kaptcha.word.impl";
    public static final String KAPTCHA_BACKGROUND_IMPL = "kaptcha.background.impl";
    public static final String KAPTCHA_BACKGROUND_CLR_FROM = "kaptcha.background.clear.from";
    public static final String KAPTCHA_BACKGROUND_CLR_TO = "kaptcha.background.clear.to";
    public static final String KAPTCHA_IMAGE_WIDTH = "kaptcha.image.width";
    public static final String KAPTCHA_IMAGE_HEIGHT = "kaptcha.image.height";
}
