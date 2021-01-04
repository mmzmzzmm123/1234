package com.ruoyi.hotel.mapper;

import java.util.List;
import com.ruoyi.hotel.domain.HtlThemeInfo;

/**
 * 主题详情Mapper接口
 * 
 * @author sucheng
 * @date 2020-12-25
 */
public interface HtlThemeInfoMapper 
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
     * 删除主题详情
     * 
     * @param themeId 主题详情ID
     * @return 结果
     */
    public int deleteHtlThemeInfoById(Long themeId);

    /**
     * 批量删除主题详情
     * 
     * @param themeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteHtlThemeInfoByIds(Long[] themeIds);
}
