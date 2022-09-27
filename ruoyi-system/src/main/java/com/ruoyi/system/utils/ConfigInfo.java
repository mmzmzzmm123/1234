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
    public static final String EXTRANET_TOKEN_URL = "https://gateway.qzbigdata.com/gateway/get-token";
    /** 公钥 */
    public static final String EXTRANET_APP_KEY = "9cae455b838b450498d340994ec218b5";

    /** 私钥 */
    public static final String EXTRANET_PRI_KEY = "b7f330de121049218bfd941c872c22e8";

    /**
     * 共享云调用接口地址
     */

    /** （全省）根据企业名称查询企业信息 */
    public static final String QUERY_COMPANY_INFO = "https://gateway.qzbigdata.com/gateway/u70is68s/api/ResidentId/queryResidentIdInfo";

    /** （全省）根据企业模糊名称（不拆词匹配）查询企业名称 */
    public static final String QUERY_COMPANY_NAME = "https://gateway.qzbigdata.com/gateway/u70is68s/EnterpInfo/GetNameByNotRemoveWordFuzzySearch";
    /** （全市）根据店铺名字(模糊)查询个体工商户基本信息 */
    public static final String QUERY_GTGSH_NAME = "https://gateway.qzbigdata.com/gateway/tiswdspt/queryByName";
    /** （全省）根据统一社会信用代码查询个体工商户基本信息 */
    public static final String QUERY_GTGSH_XYDM = "https://gateway.qzbigdata.com/gateway/u70is68s/IndivBusinessInfo/queryBytyshxydm";
    /** (全市)根据名称查询失信被执行人 */
    public static final String UntrustworthyPersonnel= "https://gateway.qzbigdata.com/gateway/xozfst1w/api/HousingTransaction/UntrustworthyPersonnel";
    /** （全省）根据企业全称查询福建省内注册企业完整信息 */
    public static final String queryAllByEnterp = "https://gateway.qzbigdata.com/gateway/u70is68s/queryAllByEnterp";
    /** 日志推送接口 */
    public static final String dumpLogs = "https://gateway.qzbigdata.com/gateway/xozfst1w/api/dumpLogs";


    /** 操作人员 */
    public static final String OPERATOR = "信贷直通车";
    /** 操作人员身份证 */
    public static final String IDCARD = "350521199710081056";

}
