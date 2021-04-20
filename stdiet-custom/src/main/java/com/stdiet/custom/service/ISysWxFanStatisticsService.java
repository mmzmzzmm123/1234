package com.stdiet.custom.service;

import java.util.List;

import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.custom.domain.SysWxFanStatistics;
import com.stdiet.custom.dto.request.FanStatisticsRequest;

/**
 * 进粉统计Service接口
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
public interface ISysWxFanStatisticsService
{
    /**
     * 查询进粉统计
     *
     * @param id 进粉统计ID
     * @return 进粉统计
     */
    public SysWxFanStatistics selectSysWxFanStatisticsById(Long id);

    /**
     * 查询进粉统计列表
     *
     * @param sysWxFanStatistics 进粉统计
     * @return 进粉统计集合
     */
    public List<SysWxFanStatistics> selectSysWxFanStatisticsList(SysWxFanStatistics sysWxFanStatistics);

    /**
     * 新增进粉统计
     *
     * @param sysWxFanStatistics 进粉统计
     * @return 结果
     */
    public int insertSysWxFanStatistics(SysWxFanStatistics sysWxFanStatistics);

    /**
     * 修改进粉统计
     *
     * @param sysWxFanStatistics 进粉统计
     * @return 结果
     */
    public int updateSysWxFanStatistics(SysWxFanStatistics sysWxFanStatistics);

    /**
     * 批量删除进粉统计
     *
     * @param ids 需要删除的进粉统计ID
     * @return 结果
     */
    public int deleteSysWxFanStatisticsByIds(Long[] ids);

    /**
     * 删除进粉统计信息
     *
     * @param id 进粉统计ID
     * @return 结果
     */
    public int deleteSysWxFanStatisticsById(Long id);

    /**
     * 同时给多个微信号添加进粉量统计
     * @param fanStatisticsRequest
     * @return
     */
    AjaxResult addWxFanNum(FanStatisticsRequest fanStatisticsRequest);

    /**
     * 根据用户ID、微信ID、进粉时间查询统计
     * @param sysWxFanStatistics
     * @return
     */
    List<SysWxFanStatistics> getWxFanStatisticsByUserIdAndFanTime(SysWxFanStatistics sysWxFanStatistics);

    /**
     * 查询总进粉数量
     * @param sysWxFanStatistics
     * @return
     */
    public int selectFanNumCount(SysWxFanStatistics sysWxFanStatistics);

    List<SysWxFanStatistics> exportStatisticsList(SysWxFanStatistics sysWxFanStatistics);
}