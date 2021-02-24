package com.stdiet.custom.service;

import java.util.List;
import com.stdiet.custom.domain.SysCustomerHeatStatistics;
import com.stdiet.custom.dto.response.NutritionalCalories;

/**
 * 外食热量统计Service接口
 *
 * @author xzj
 * @date 2021-02-20
 */
public interface ISysCustomerHeatStatisticsService
{
    /**
     * 查询外食热量统计
     *
     * @param id 外食热量统计ID
     * @return 外食热量统计
     */
    public SysCustomerHeatStatistics selectSysCustomerHeatStatisticsById(Long id);

    /**
     * 查询外食热量统计列表
     *
     * @param sysCustomerHeatStatistics 外食热量统计
     * @return 外食热量统计集合
     */
    public List<SysCustomerHeatStatistics> selectSysCustomerHeatStatisticsList(SysCustomerHeatStatistics sysCustomerHeatStatistics);

    /**
     * 新增外食热量统计
     *
     * @param sysCustomerHeatStatistics 外食热量统计
     * @return 结果
     */
    public int insertSysCustomerHeatStatistics(SysCustomerHeatStatistics sysCustomerHeatStatistics);

    /**
     * 修改外食热量统计
     *
     * @param sysCustomerHeatStatistics 外食热量统计
     * @return 结果
     */
    public int updateSysCustomerHeatStatistics(SysCustomerHeatStatistics sysCustomerHeatStatistics);

    /**
     * 批量删除外食热量统计
     *
     * @param ids 需要删除的外食热量统计ID
     * @return 结果
     */
    public int deleteSysCustomerHeatStatisticsByIds(Long[] ids);

    /**
     * 删除外食热量统计信息
     *
     * @param id 外食热量统计ID
     * @return 结果
     */
    public int deleteSysCustomerHeatStatisticsById(Long id);

    /**
     * 更新食材热量并计算当天总热量
     * @param sysCustomerHeatStatistics
     * @return
     */
    public int calculateCustomerHeat(SysCustomerHeatStatistics sysCustomerHeatStatistics);

    /**
     * 根据日期查询是否客户热量统计
     * @param sysCustomerHeatStatistics
     * @return
     */
    SysCustomerHeatStatistics getCustomerHeatStatisticsByDate(SysCustomerHeatStatistics sysCustomerHeatStatistics);

    /**
     * 根据客户热量食材统计ID查询详情
     * @param id
     * @return
     */
    public NutritionalCalories getNutritionalCaloriesByCustomerHeatId(Long id);
}