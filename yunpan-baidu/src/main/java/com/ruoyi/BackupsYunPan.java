package com.ruoyi;

import org.springframework.stereotype.Service;

/**
 * @Auther: Wang
 * @Date: 2021/11/20 00:19
 * 功能描述:
 */

public interface BackupsYunPan {


    /**
     * @Description:
     *
     * @param filePath 文件路徑
     * @param fileName 文件名称
     * @param atoken acc token
     * @return
     * @Date
     * @author: wanghao
     *
     */
    public String BaiDuYunBackups(String filePath,String fileName ,String atoken);

}
