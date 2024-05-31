package com.onethinker.common.enums;

import lombok.Getter;

/**
 * 缓存key常量枚举
 * @author yangyouqi
 * @date 2023/10/24
 */
@Getter
public enum CacheEnum {
    /**
     * 缓存key常量枚举
     */
    LOGIN_TOKEN_KEY("login_tokens:", "用户信息"),
    SYS_CONFIG_KEY("sys_config:", "配置信息"),
    SYS_DICT_KEY("sys_dict:", "数据字典"),
    CAPTCHA_CODE_KEY("captcha_codes:", "验证码"),
    REPEAT_SUBMIT_KEY("repeat_submit:", "防重提交"),
    RATE_LIMIT_KEY("rate_limit:", "限流处理"),
    PWD_ERR_CNT_KEY("pwd_err_cnt:", "密码错误次数"),
    QUERY_USER_DETAIL_DATA_ID_KEY("pu_login_user_info:", "平台登录用户信息"),
    SUBSCRIBE_MSG_KEY("subscribe_msg:", "订阅消息"),
    WEB_INFO("bk_web_info:", "网站基础信息"),
    BX_CUSTOMER_INFO_KEY("bx_customer_info_key:", "佰兴-客户信息"),
    UPLOAD_FILE_STATUS("upload_file_status:","文件上传状态"),
    QUERY_USER_INTEGRAL_KEY("query_user_integral_key:", "平台用户积分信息"),
    QUERY_FILE_INFO_KEY("query_file_info_key:","查询文件信息"),
    ACTIVITY_REDIS_KEY("activity_redis_key:","活动信息");

    private String code;
    private String msg;

    CacheEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
