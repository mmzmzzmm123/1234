package com.baoli.task.service;

import java.util.List;
import com.baoli.task.domain.BaoliBizYearTaskPlan;

/**
 * 年任务管理（T）Service接口
 * 
 * @author niujs
 * @date 2024-05-14
 */
public interface IBaoliBizYearTaskPlanService 
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
     * 批量删除年任务管理（T）
     * 
     * @param ids 需要删除的年任务管理（T）主键集合
     * @return 结果
     */
    public int deleteBaoliBizYearTaskPlanByIds(Long[] ids);

    /**
     * 删除年任务管理（T）信息
     * 
     * @param id 年任务管理（T）主键
     * @return 结果
     */
    public int deleteBaoliBizYearTaskPlanById(Long id);
}
