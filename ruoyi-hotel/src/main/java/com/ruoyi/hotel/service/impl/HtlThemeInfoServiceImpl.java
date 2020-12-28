package com.ruoyi.hotel.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hotel.mapper.HtlThemeInfoMapper;
import com.ruoyi.hotel.domain.HtlThemeInfo;
import com.ruoyi.hotel.service.IHtlThemeInfoService;

/**
 * 主题详情Service业务层处理
 * 
 * @author sucheng
 * @date 2020-12-25
 */
@Service
public class HtlThemeInfoServiceImpl implements IHtlThemeInfoService 
{
    @Autowired
    private HtlThemeInfoMapper htlThemeInfoMapper;

    /**
     * 查询主题详情
     * 
     * @param themeId 主题详情ID
     * @return 主题详情
     */
    @Override
    public HtlThemeInfo selectHtlThemeInfoById(Long themeId)
    {
        return htlThemeInfoMapper.selectHtlThemeInfoById(themeId);
    }

    /**
     * 查询主题详情列表
     * 
     * @param htlThemeInfo 主题详情
     * @return 主题详情
     */
    @Override
    public List<HtlThemeInfo> selectHtlThemeInfoList(HtlThemeInfo htlThemeInfo)
    {
        return htlThemeInfoMapper.selectHtlThemeInfoList(htlThemeInfo);
    }

    /**
     * 新增主题详情
     * 
     * @param htlThemeInfo 主题详情
     * @return 结果
     */
    @Override
    public int insertHtlThemeInfo(HtlThemeInfo htlThemeInfo)
    {
        htlThemeInfo.setCreateTime(DateUtils.getNowDate());
        return htlThemeInfoMapper.insertHtlThemeInfo(htlThemeInfo);
    }

    /**
     * 修改主题详情
     * 
     * @param htlThemeInfo 主题详情
     * @return 结果
     */
    @Override
    public int updateHtlThemeInfo(HtlThemeInfo htlThemeInfo)
    {
        htlThemeInfo.setUpdateTime(DateUtils.getNowDate());
        return htlThemeInfoMapper.updateHtlThemeInfo(htlThemeInfo);
    }

    /**
     * 批量删除主题详情
     * 
     * @param themeIds 需要删除的主题详情ID
     * @return 结果
     */
    @Override
    public int deleteHtlThemeInfoByIds(Long[] themeIds)
    {
        return htlThemeInfoMapper.deleteHtlThemeInfoByIds(themeIds);
    }

    /**
     * 删除主题详情信息
     * 
     * @param themeId 主题详情ID
     * @return 结果
     */
    @Override
    public int deleteHtlThemeInfoById(Long themeId)
    {
        return htlThemeInfoMapper.deleteHtlThemeInfoById(themeId);
    }
}
