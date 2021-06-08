package com.stdiet.custom.service.impl;

import java.util.List;
import java.util.Map;

import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.dto.request.FanStatisticsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysWxFanStatisticsMapper;
import com.stdiet.custom.domain.SysWxFanStatistics;
import com.stdiet.custom.service.ISysWxFanStatisticsService;

/**
 * 进粉统计Service业务层处理
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
@Service
public class SysWxFanStatisticsServiceImpl implements ISysWxFanStatisticsService
{
    @Autowired
    private SysWxFanStatisticsMapper sysWxFanStatisticsMapper;

    /**
     * 查询进粉统计
     *
     * @param id 进粉统计ID
     * @return 进粉统计
     */
    @Override
    public SysWxFanStatistics selectSysWxFanStatisticsById(Long id)
    {
        return sysWxFanStatisticsMapper.selectSysWxFanStatisticsById(id);
    }

    /**
     * 查询进粉统计列表
     *
     * @param sysWxFanStatistics 进粉统计
     * @return 进粉统计
     */
    @Override
    public List<SysWxFanStatistics> selectSysWxFanStatisticsList(SysWxFanStatistics sysWxFanStatistics)
    {
        return sysWxFanStatisticsMapper.selectSysWxFanStatisticsList(sysWxFanStatistics);
    }

    /**
     * 新增进粉统计
     *
     * @param sysWxFanStatistics 进粉统计
     * @return 结果
     */
    @Override
    public int insertSysWxFanStatistics(SysWxFanStatistics sysWxFanStatistics)
    {
        sysWxFanStatistics.setCreateTime(DateUtils.getNowDate());
        return sysWxFanStatisticsMapper.insertSysWxFanStatistics(sysWxFanStatistics);
    }

    /**
     * 修改进粉统计
     *
     * @param sysWxFanStatistics 进粉统计
     * @return 结果
     */
    @Override
    public int updateSysWxFanStatistics(SysWxFanStatistics sysWxFanStatistics)
    {
        sysWxFanStatistics.setUpdateTime(DateUtils.getNowDate());
        return sysWxFanStatisticsMapper.updateSysWxFanStatistics(sysWxFanStatistics);
    }

    /**
     * 批量删除进粉统计
     *
     * @param ids 需要删除的进粉统计ID
     * @return 结果
     */
    @Override
    public int deleteSysWxFanStatisticsByIds(Long[] ids)
    {
        return sysWxFanStatisticsMapper.deleteSysWxFanStatisticsByIds(ids);
    }

    /**
     * 删除进粉统计信息
     *
     * @param id 进粉统计ID
     * @return 结果
     */
    @Override
    public int deleteSysWxFanStatisticsById(Long id)
    {
        return sysWxFanStatisticsMapper.deleteSysWxFanStatisticsById(id);
    }

    /**
     * 根据用户ID、微信ID、进粉时间查询统计
     * @param sysWxFanStatistics
     * @return
     */
    @Override
    public List<SysWxFanStatistics> getWxFanStatisticsByUserIdAndFanTime(SysWxFanStatistics sysWxFanStatistics){
        return sysWxFanStatisticsMapper.getWxFanStatisticsByUserIdAndFanTime(sysWxFanStatistics);
    }

    /**
     * 同时给多个微信号添加进粉量统计
     * @param fanStatisticsRequest
     * @return
     */
    public AjaxResult addWxFanNum(FanStatisticsRequest fanStatisticsRequest){
        int row = 0;
        if(fanStatisticsRequest.getWxId() != null && fanStatisticsRequest.getUserId() != null){
            SysWxFanStatistics param = new SysWxFanStatistics();
            param.setUserId(fanStatisticsRequest.getUserId());
            param.setFanTime(fanStatisticsRequest.getFanTime());
            //查询今日是否已添加
            List<SysWxFanStatistics> oldWxFanStatisticsList = getWxFanStatisticsByUserIdAndFanTime(param);
            if(oldWxFanStatisticsList != null && oldWxFanStatisticsList.size() > 0){
                return AjaxResult.error("今日已添加过进粉统计，无法重复添加");
            }
            int index = 0;
            for (Long wxId : fanStatisticsRequest.getWxId()) {
                SysWxFanStatistics sysWxFanStatistics = new SysWxFanStatistics();
                sysWxFanStatistics.setWxId(wxId);
                sysWxFanStatistics.setFanTime(fanStatisticsRequest.getFanTime());
                sysWxFanStatistics.setFanNum(fanStatisticsRequest.getFanNum()[index++]);
                sysWxFanStatistics.setUserId(fanStatisticsRequest.getUserId());
                row = insertSysWxFanStatistics(sysWxFanStatistics);
            }
        }
        return row > 0 ? AjaxResult.success() : AjaxResult.error("添加失败");
    }

    /**
     * 查询总进粉数量
     * @param sysWxFanStatistics
     * @return
     */
    public int selectFanNumCount(SysWxFanStatistics sysWxFanStatistics){
        return sysWxFanStatisticsMapper.selectFanNumCount(sysWxFanStatistics);
    }

    @Override
    public List<SysWxFanStatistics> exportStatisticsList(SysWxFanStatistics sysWxFanStatistics) {
        return sysWxFanStatisticsMapper.exportStatisticsList(sysWxFanStatistics);
    }

    /**
     * 根据时间范围统计每个渠道的进粉数量
     * @param sysWxFanStatistics
     * @return
     */
    @Override
    public List<Map<String,Object>> getTotalFanNumGroupByChannel(SysWxFanStatistics sysWxFanStatistics){
        return sysWxFanStatisticsMapper.getTotalFanNumGroupByChannel(sysWxFanStatistics);
    }
}