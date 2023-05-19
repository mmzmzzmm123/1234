package com.ruoyi.common.constant;

import java.util.HashMap;
import java.util.Map;

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
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 短信验证码 redis key
     */
    public static final String SMS_CODE_KEY = "sms_codes:";

    /**
     * 短信验证码校验 redis key
     */
    public static final String SMS_CODE_CHECK_KEY = "sms_check_times_code:";

    /**
     * 短信发送间隔校验 redis key
     */
    public static final String SMS_CODE_INTERVAL_KEY = "sms_check_last_send_time_code:";

    /**
     * 贷款信息 redis key
     */
    public static final String LOAN_CODE_KEY = "loan_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";
    
    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 短信验证码有效期（分钟）
     */
    public static final Integer SMS_CODE_EXPIRATION = 10;

    /**
     * 短信发送次数限制 （次/天）
     */
    public static final Integer SMS_CODE_LIMIT_PERDAY = 5;

    /**
     * 短信发送间隔（毫秒）
     */
    public static final Long SMS_CODE_LIMIT_INTERVAL = 60*1000L;

    /**
     * 是否允许贷款的期限 3天
     */
    public static final Integer LOAN_TIME_EXPIRATION = 3;

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
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi://";

    public static final String TYPE_GTGSH = "C04";

    /**
     * 用户类型
     */
    public static final Map<String,String> COMPANY_TYPE_MAP = new HashMap<String,String>(){
        {
            put("C01", "企业法人");
            put("C02", "社会组织法人");
            put("C03", "事业单位法人");
            put(TYPE_GTGSH, "个体工商户");
        }
    };
}
