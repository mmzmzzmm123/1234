package com.ruoyi.hotel.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hotel.mapper.HtlThemeConfigMapper;
import com.ruoyi.hotel.domain.HtlThemeConfig;
import com.ruoyi.hotel.service.IHtlThemeConfigService;

/**
 * 主题配置Service业务层处理
 * 
 * @author sucheng
 * @date 2020-12-25
 */
@Service
public class HtlThemeConfigServiceImpl implements IHtlThemeConfigService 
{
    @Autowired
    private HtlThemeConfigMapper htlThemeConfigMapper;

    /**
     * 查询主题配置
     * 
     * @param hotelId 主题配置ID
     * @return 主题配置
     */
    @Override
    public HtlThemeConfig selectHtlThemeConfigById(Long hotelId)
    {
        return htlThemeConfigMapper.selectHtlThemeConfigById(hotelId);
    }

    /**
     * 查询主题配置列表
     * 
     * @param htlThemeConfig 主题配置
     * @return 主题配置
     */
    @Override
    public List<HtlThemeConfig> selectHtlThemeConfigList(HtlThemeConfig htlThemeConfig)
    {
        return htlThemeConfigMapper.selectHtlThemeConfigList(htlThemeConfig);
    }

    /**
     * 新增主题配置
     * 
     * @param htlThemeConfig 主题配置
     * @return 结果
     */
    @Override
    public int insertHtlThemeConfig(HtlThemeConfig htlThemeConfig)
    {
        htlThemeConfig.setCreateTime(DateUtils.getNowDate());
        return htlThemeConfigMapper.insertHtlThemeConfig(htlThemeConfig);
    }

    /**
     * 修改主题配置
     * 
     * @param htlThemeConfig 主题配置
     * @return 结果
     */
    @Override
    public int updateHtlThemeConfig(HtlThemeConfig htlThemeConfig)
    {
        htlThemeConfig.setUpdateTime(DateUtils.getNowDate());
        return htlThemeConfigMapper.updateHtlThemeConfig(htlThemeConfig);
    }

    /**
     * 批量删除主题配置
     * 
     * @param hotelIds 需要删除的主题配置ID
     * @return 结果
     */
    @Override
    public int deleteHtlThemeConfigByIds(Long[] hotelIds)
    {
        return htlThemeConfigMapper.deleteHtlThemeConfigByIds(hotelIds);
    }

    /**
     * 删除主题配置信息
     * 
     * @param hotelId 主题配置ID
     * @return 结果
     */
    @Override
    public int deleteHtlThemeConfigById(Long hotelId)
    {
        return htlThemeConfigMapper.deleteHtlThemeConfigById(hotelId);
    }
}
