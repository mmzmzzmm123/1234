package com.onethinker.bk.service;

import com.onethinker.bk.domain.WebInfo;

import java.util.List;

/**
 * 网站信息Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IWebInfoService {

    /**
     * 修改网站信息
     *
     * @param webInfo 网站信息
     * @return 结果
     */
    int updateWebInfo(WebInfo webInfo);

    /**
     * 获取Web网站记录
     *
     * @return
     */
    WebInfo getWebInfo();

    /**
     * 获取头像信息
     * @param key
     * @param ipAddr
     * @return
     */
    String getRandomAvatar(String key, String ipAddr);
}
