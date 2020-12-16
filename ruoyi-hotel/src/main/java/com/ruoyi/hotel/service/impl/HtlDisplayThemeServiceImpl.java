package com.ruoyi.hotel.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hotel.mapper.HtlDisplayThemeMapper;
import com.ruoyi.hotel.domain.HtlDisplayTheme;
import com.ruoyi.hotel.service.IHtlDisplayThemeService;

/**
 * 主题配置Service业务层处理
 * 
 * @author sucheng
 * @date 2020-11-22
 */
@Service
public class HtlDisplayThemeServiceImpl implements IHtlDisplayThemeService 
{
    @Autowired
    private HtlDisplayThemeMapper htlDisplayThemeMapper;

    /**
     * 查询主题配置
     * 
     * @param hotelId 主题配置ID
     * @return 主题配置
     */
    @Override
    public HtlDisplayTheme selectHtlDisplayThemeById(Long hotelId)
    {
        return htlDisplayThemeMapper.selectHtlDisplayThemeById(hotelId);
    }

    /**
     * 查询主题配置列表
     * 
     * @param htlDisplayTheme 主题配置
     * @return 主题配置
     */
    @Override
    public List<HtlDisplayTheme> selectHtlDisplayThemeList(HtlDisplayTheme htlDisplayTheme)
    {
        return htlDisplayThemeMapper.selectHtlDisplayThemeList(htlDisplayTheme);
    }

    /**
     * 新增主题配置
     * 
     * @param htlDisplayTheme 主题配置
     * @return 结果
     */
    @Override
    public int insertHtlDisplayTheme(HtlDisplayTheme htlDisplayTheme)
    {
        htlDisplayTheme.setCreateTime(DateUtils.getNowDate());
        return htlDisplayThemeMapper.insertHtlDisplayTheme(htlDisplayTheme);
    }

    /**
     * 修改主题配置
     * 
     * @param htlDisplayTheme 主题配置
     * @return 结果
     */
    @Override
    public int updateHtlDisplayTheme(HtlDisplayTheme htlDisplayTheme)
    {
        htlDisplayTheme.setUpdateTime(DateUtils.getNowDate());
        return htlDisplayThemeMapper.updateHtlDisplayTheme(htlDisplayTheme);
    }

    /**
     * 批量删除主题配置
     * 
     * @param hotelIds 需要删除的主题配置ID
     * @return 结果
     */
    @Override
    public int deleteHtlDisplayThemeByIds(Long[] hotelIds)
    {
        return htlDisplayThemeMapper.deleteHtlDisplayThemeByIds(hotelIds);
    }

    /**
     * 删除主题配置信息
     * 
     * @param hotelId 主题配置ID
     * @return 结果
     */
    @Override
    public int deleteHtlDisplayThemeById(Long hotelId)
    {
        return htlDisplayThemeMapper.deleteHtlDisplayThemeById(hotelId);
    }
}
