package com.ruoyi.common.constant;

/**
 * @Auther: Wang
 * @Date: 2021/07/24 23:17
 * 功能描述: 百度网盘的 请求url
 */
public class BaiduUrl {

    /**
     * 获取用户的身份信息 名称
     */
    public static final String USER = "https://pan.baidu.com/rest/2.0/xpan/nas";

    /**
     * 获取容量信息
     */
    public static final String CAPACITY = "https://pan.baidu.com/api/quota";

    /**
     * 获取文件列表
     */
    public static final String FILE_LIST = "https://pan.baidu.com/rest/2.0/xpan/file";

    /**
     * 获取递归文件列表
     */
    public static final String RECURSION = "https://pan.baidu.com/rest/2.0/xpan/multimedia";

    /**
     * 搜索文件
     */
    public static final String FILE_NAME = "https://pan.baidu.com/rest/2.0/xpan/file";

    /**
     * 通过ID查询文件的详细信息
     */
    public static final String FILEMETAS = "https://pan.baidu.com/rest/2.0/xpan/multimedia";


}
