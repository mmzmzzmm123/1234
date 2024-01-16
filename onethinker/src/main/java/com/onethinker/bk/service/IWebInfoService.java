package com.onethinker.bk.service;

import java.util.List;
import com.onethinker.bk.domain.WebInfo;

/**
 * 网站信息Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IWebInfoService {
    /**
     * 查询网站信息
     *
     * @param id 网站信息主键
     * @return 网站信息
     */
    public WebInfo selectWebInfoById(Long id);

    /**
     * 查询网站信息列表
     *
     * @param webInfo 网站信息
     * @return 网站信息集合
     */
    public List<WebInfo> selectWebInfoList(WebInfo webInfo);

    /**
     * 新增网站信息
     *
     * @param webInfo 网站信息
     * @return 结果
     */
    public int insertWebInfo(WebInfo webInfo);

    /**
     * 修改网站信息
     *
     * @param webInfo 网站信息
     * @return 结果
     */
    public int updateWebInfo(WebInfo webInfo);

    /**
     * 批量删除网站信息
     *
     * @param ids 需要删除的网站信息主键集合
     * @return 结果
     */
    public int deleteWebInfoByIds(Long[] ids);

    /**
     * 删除网站信息信息
     *
     * @param id 网站信息主键
     * @return 结果
     */
    public int deleteWebInfoById(Long id);

    /**
     * 获取Web网站记录
     * @return
     */
    WebInfo getWebInfo();
}
