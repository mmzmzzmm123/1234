package com.stdiet.custom.mapper;

import com.stdiet.custom.domain.SysWxFanStatistics;

import java.util.List;

/**
 * 进粉统计Mapper接口
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
public interface SysWxFanStatisticsMapper {
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
     * 删除进粉统计
     *
     * @param id 进粉统计ID
     * @return 结果
     */
    public int deleteSysWxFanStatisticsById(Long id);

    /**
     * 批量删除进粉统计
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysWxFanStatisticsByIds(Long[] ids);

    /**
     * 根据用户ID和进粉统计日期查询进粉统计记录
     */
    public List<SysWxFanStatistics> getWxFanStatisticsByUserIdAndFanTime(SysWxFanStatistics sysWxFanStatistics);

    /**
     * 查询总进粉数量
     *
     * @param sysWxFanStatistics
     * @return
     */
    public int selectFanNumCount(SysWxFanStatistics sysWxFanStatistics);

    public List<SysWxFanStatistics> exportStatisticsList(SysWxFanStatistics sysWxFanStatistics);
}