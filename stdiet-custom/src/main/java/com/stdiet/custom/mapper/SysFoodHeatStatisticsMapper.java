package com.stdiet.custom.mapper;

import java.util.HashMap;
import java.util.List;
import com.stdiet.custom.domain.SysFoodHeatStatistics;

/**
 * 外食热量统计Mapper接口
 *
 * @author xzj
 * @date 2021-02-19
 */
public interface SysFoodHeatStatisticsMapper {
    /**
     * 查询外食热量统计
     *
     * @param id 外食热量统计ID
     * @return 外食热量统计
     */
    public SysFoodHeatStatistics selectSysFoodHeatStatisticsById(Long id);

    /**
     * 查询外食热量统计列表
     *
     * @param sysFoodHeatStatistics 外食热量统计
     * @return 外食热量统计集合
     */
    public List<SysFoodHeatStatistics> selectSysFoodHeatStatisticsList(SysFoodHeatStatistics sysFoodHeatStatistics);

    /**
     * 新增外食热量统计
     *
     * @param sysFoodHeatStatistics 外食热量统计
     * @return 结果
     */
    public int insertSysFoodHeatStatistics(SysFoodHeatStatistics sysFoodHeatStatistics);

    /**
     * 修改外食热量统计
     *
     * @param sysFoodHeatStatistics 外食热量统计
     * @return 结果
     */
    public int updateSysFoodHeatStatistics(SysFoodHeatStatistics sysFoodHeatStatistics);

    /**
     * 删除外食热量统计
     *
     * @param id 外食热量统计ID
     * @return 结果
     */
    public int deleteSysFoodHeatStatisticsById(Long id);

    /**
     * 批量删除外食热量统计
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysFoodHeatStatisticsByIds(Long[] ids);

    /**
     * 批量添加
     * @param sysFoodHeatStatisticsList
     * @return
     */
    public int insertFoodHeatBatch(List<HashMap> sysFoodHeatStatisticsList);
}