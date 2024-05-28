package com.onethinker.common.constant;

/**
 * 系统参数
 * @author yangyouqi
 * @date 2024/5/15
 */
public interface SystemConst {
    /**
     * 系统标识
     */
    String SYSTEM = "SYSTEM";
    /**
     * 系统
     */
    String SYSTEM_NAME = "系统";

    /**
     * 游客
     */
    String DEFAULT_ANONYMOUS_NAME = "游客";

    /**
     * 默认最大服务数
     */
    int DEFAULT_MAX_SERVER_SIZE = 25;

    /**
     * 最大分页大小
     */
    int MAX_PAGE_SIZE = 10000;

    /**
     * 省级标志
     */
    String PROVINCE_FLAG = "440000000";

    /**
     * 所有标志
     */
    String ALL_FLAG = "all";

    /**
     * 省级区划代码
     */
    String PROVINCE_REGION_CODE = "440000000";

    /**
     * 默认CSM租户id
     */
    String DEFAULT_COMPANY_ID = "1";

    /**
     * 默认租户编码
     */
    String DEFAULT_TENANT_CODE = "iops";
    /**
     * 默认租户id
     */
    String DEFAULT_TENANT_ID = "00000000-0000-0000-0000-000000000000";


    /**
     * redis分隔符
     */
    String REDIS_SPLIT_CHAT = ":";

    /**
     * 逗号分隔符
     */
    String STRING_SPLIT_COMMA = ",";

    /**
     * 接口错误码，查询值为空
     */
    String FLAG_ERROR_CODE_NULL = "0";
    /**
     * 接口成功码
     */
    String FLAG_SUCCESS_CODE = "1";
    /**
     * 接口失败码，查询值为空
     */
    String FLAG_ERROR_CODE_FAIL = "-1";
    /**
     * 业务错误
     */
    String FLAG_ERROR_CODE_SYSTEM = "-100";
    /**
     * 接口错误，查询值为空
     */
    String ERROR_TEXT_NULL = "结果为空";
    /**
     * 接口失败，查询值为空
     */
    String ERROR_TEXT_FAIL = "参数错误，操作失败";
    /**
     * 业务错误提示
     */
    String ERROR_TEXT_SYSTEM = "操作异常,请联系管理员";
    /**
     * 接口成功
     */
    String SUCCESS_TEXT = "操作成功";

    /**
     * API_KEY
     */
    String API_KEY = "X-API-KEY";

    /**
     * 默认顶层区域编码
     */
    String DEFAULT_PARENT_REGION_CODE = "440000000";

    /**
     * 默认区域编码
     */
    String DEFAULT_REGION_CODE = "440000000";

    /**
     * 应用名称
     */
    String CONF_APPLICATION_NAME = "spring.application.name";

    /**
     * 空格符
     */
    String SYMBOL_BLANK = " ";

    /**
     * 密钥
     */
    public static final String CRYPOTJS_KEY = "aoligeimeimaobin";
}
