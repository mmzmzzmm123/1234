package com.ruoyi.hotel.service;

import java.util.List;
import com.ruoyi.hotel.domain.HtlThemeInfo;

/**
 * 主题详情Service接口
 * 
 * @author sucheng
 * @date 2020-12-25
 */
public interface IHtlThemeInfoService 
{
    /**
     * 查询主题详情
     * 
     * @param themeId 主题详情ID
     * @return 主题详情
     */
    public HtlThemeInfo selectHtlThemeInfoById(Long themeId);

    /**
     * 查询主题详情列表
     * 
     * @param htlThemeInfo 主题详情
     * @return 主题详情集合
     */
    public List<HtlThemeInfo> selectHtlThemeInfoList(HtlThemeInfo htlThemeInfo);

    /**
     * 新增主题详情
     * 
     * @param htlThemeInfo 主题详情
     * @return 结果
     */
    public int insertHtlThemeInfo(HtlThemeInfo htlThemeInfo);

    /**
     * 修改主题详情
     * 
     * @param htlThemeInfo 主题详情
     * @return 结果
     */
    public int updateHtlThemeInfo(HtlThemeInfo htlThemeInfo);

    /**
     * 批量删除主题详情
     * 
     * @param themeIds 需要删除的主题详情ID
     * @return 结果
     */
    public int deleteHtlThemeInfoByIds(Long[] themeIds);

    /**
     * 删除主题详情信息
     * 
     * @param themeId 主题详情ID
     * @return 结果
     */
    public int deleteHtlThemeInfoById(Long themeId);
}
