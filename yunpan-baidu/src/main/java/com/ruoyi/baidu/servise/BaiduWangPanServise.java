package com.ruoyi.baidu.servise;



/**
 * @Auther: Wang
 * @Date: 2021/07/24 22:33
 * 功能描述:
 */

public interface BaiduWangPanServise {



    /**
     * @Description: 获取用户的身份信息
     *
     * @param  access_token token令牌
     *
     */
   String getUserInfo(String access_token);

    /**
     * @Description: 获取容量信息
     *
     * @param  access_token token令牌
     *
     */
   String getCapacity(String access_token);

    /**
     * @Description: 获取文件列表信息
     *
     * @param  access_token token令牌
     *
     */
   String getFileList(String access_token);

    /**
     * @Description: 获取文件列表信息
     *
     * @param  access_token token令牌
     * @param  filePath 递归的文件夹路径 如: /myApp
     *
     */
    String getFileRecursionList(String access_token,String filePath);

    /**
     * @Description: 搜索文件
     *
     * @param  access_token token令牌
     * @param  fileName 文件名称
     *
     */
    String getSouSuoFileList(String access_token,String fileName);

    /**
     * @Description: 通过文件ID查询文件的信息
     *
     * @param  access_token token令牌
     * @param  fileId 文件ID
     *
     */
    String getfilemetas(String access_token,String fileId);







}
