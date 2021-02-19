package com.stdiet.custom.service;

import java.util.List;
import com.stdiet.custom.domain.SysFoodHeatStatistics;
import com.stdiet.custom.dto.request.FoodHeatCalculatorRequest;

/**
 * 外食热量统计Service接口
 *
 * @author xzj
 * @date 2021-02-19
 */
public interface ISysFoodHeatStatisticsService
{
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
     * 批量删除外食热量统计
     *
     * @param ids 需要删除的外食热量统计ID
     * @return 结果
     */
    public int deleteSysFoodHeatStatisticsByIds(Long[] ids);

    /**
     * 删除外食热量统计信息
     *
     * @param id 外食热量统计ID
     * @return 结果
     */
    public int deleteSysFoodHeatStatisticsById(Long id);

    /**
     * 客户自己添加外食计算数据，批量添加
     * @param foodHeatCalculatorRequest
     * @return
     */
    public int addMuchFoodHeat(FoodHeatCalculatorRequest foodHeatCalculatorRequest);
}