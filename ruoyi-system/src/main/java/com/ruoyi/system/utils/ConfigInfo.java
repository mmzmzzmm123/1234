package com.ruoyi.system.utils;

/**
 * 泉州市汇聚平台接口
 * @author lxc
 * @version 1.0
 * @description
 * @date  2021/7/19 12:03
 */
public class ConfigInfo {


    /** 政务内网:intranet  政务外网：extranet */
//    public static final String NETWORK = "INTRANET";
    public static final String NETWORK = "EXTRANET";

    /** 获取令牌token地址 */
    public static final String EXTRANET_TOKEN_URL = "http://222.77.0.158:18081/api-gateway/gateway/get-token";
    /** 公钥 */
    public static final String EXTRANET_APP_KEY = "9cae455b838b450498d340994ec218b5";

    /** 私钥 */
    public static final String EXTRANET_PRI_KEY = "b7f330de121049218bfd941c872c22e8";

    /**
     * 共享云调用接口地址
     */

    /** （全省）根据企业名称查询企业信息 */
    public static final String QUERY_COMPANY_INFO = "http://222.77.0.158:18081/api-gateway/gateway/u70is68s/api/ResidentId/queryResidentIdInfo";

    /** （全省）根据企业模糊名称（不拆词匹配）查询企业名称 */
    public static final String QUERY_COMPANY_NAME = "http://222.77.0.158:18081/api-gateway/gateway/u70is68s/EnterpInfo/GetNameByNotRemoveWordFuzzySearch";


}
