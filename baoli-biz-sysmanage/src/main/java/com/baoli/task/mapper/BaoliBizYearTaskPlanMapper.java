package com.baoli.task.mapper;

import java.util.List;
import com.baoli.task.domain.BaoliBizYearTaskPlan;

/**
 * 年任务管理（T）Mapper接口
 * 
 * @author niujs
 * @date 2024-05-14
 */
public interface BaoliBizYearTaskPlanMapper 
{
    /**
     * 查询年任务管理（T）
     * 
     * @param id 年任务管理（T）主键
     * @return 年任务管理（T）
     */
    public BaoliBizYearTaskPlan selectBaoliBizYearTaskPlanById(Long id);

    /**
     * 查询年任务管理（T）列表
     * 
     * @param baoliBizYearTaskPlan 年任务管理（T）
     * @return 年任务管理（T）集合
     */
    public List<BaoliBizYearTaskPlan> selectBaoliBizYearTaskPlanList(BaoliBizYearTaskPlan baoliBizYearTaskPlan);

    /**
     * 新增年任务管理（T）
     * 
     * @param baoliBizYearTaskPlan 年任务管理（T）
     * @return 结果
     */
    public int insertBaoliBizYearTaskPlan(BaoliBizYearTaskPlan baoliBizYearTaskPlan);

    /**
     * 修改年任务管理（T）
     * 
     * @param baoliBizYearTaskPlan 年任务管理（T）
     * @return 结果
     */
    public int updateBaoliBizYearTaskPlan(BaoliBizYearTaskPlan baoliBizYearTaskPlan);

    /**
     * 删除年任务管理（T）
     * 
     * @param id 年任务管理（T）主键
     * @return 结果
     */
    public int deleteBaoliBizYearTaskPlanById(Long id);

    /**
     * 批量删除年任务管理（T）
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizYearTaskPlanByIds(Long[] ids);
}
