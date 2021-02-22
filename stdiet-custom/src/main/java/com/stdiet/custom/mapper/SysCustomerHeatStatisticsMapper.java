package com.stdiet.custom.mapper;

import java.util.List;

import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysCustomerHeatStatistics;
import com.stdiet.custom.dto.response.NutritionalCalories;

/**
 * 外食热量统计Mapper接口
 *
 * @author xzj
 * @date 2021-02-20
 */
public interface SysCustomerHeatStatisticsMapper
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
     * 删除外食热量统计
     *
     * @param id 外食热量统计ID
     * @return 结果
     */
    public int deleteSysCustomerHeatStatisticsById(Long id);

    /**
     * 批量删除外食热量统计
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysCustomerHeatStatisticsByIds(Long[] ids);

    /**
     * 根据客户ID、日期查询客户热量统计数据
     * @param sysCustomerHeatStatistics
     * @return
     */
    public SysCustomerHeatStatistics getCustomerHeatStatisticsByDate(SysCustomerHeatStatistics sysCustomerHeatStatistics);
}