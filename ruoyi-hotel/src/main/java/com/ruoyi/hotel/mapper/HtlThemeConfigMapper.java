package com.ruoyi.hotel.mapper;

import java.util.List;
import com.ruoyi.hotel.domain.HtlThemeConfig;

/**
 * 主题配置Mapper接口
 * 
 * @author sucheng
 * @date 2020-12-25
 */
public interface HtlThemeConfigMapper 
{
    /**
     * 查询主题配置
     * 
     * @param hotelId 主题配置ID
     * @return 主题配置
     */
    public HtlThemeConfig selectHtlThemeConfigById(Long hotelId);

    /**
     * 查询主题配置列表
     * 
     * @param htlThemeConfig 主题配置
     * @return 主题配置集合
     */
    public List<HtlThemeConfig> selectHtlThemeConfigList(HtlThemeConfig htlThemeConfig);

    /**
     * 新增主题配置
     * 
     * @param htlThemeConfig 主题配置
     * @return 结果
     */
    public int insertHtlThemeConfig(HtlThemeConfig htlThemeConfig);

    /**
     * 修改主题配置
     * 
     * @param htlThemeConfig 主题配置
     * @return 结果
     */
    public int updateHtlThemeConfig(HtlThemeConfig htlThemeConfig);

    /**
     * 删除主题配置
     * 
     * @param hotelId 主题配置ID
     * @return 结果
     */
    public int deleteHtlThemeConfigById(Long hotelId);

    /**
     * 批量删除主题配置
     * 
     * @param hotelIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteHtlThemeConfigByIds(Long[] hotelIds);
}
