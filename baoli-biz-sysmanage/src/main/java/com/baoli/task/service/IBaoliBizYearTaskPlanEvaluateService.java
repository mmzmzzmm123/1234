package com.baoli.task.service;

import java.util.List;
import com.baoli.task.domain.BaoliBizYearTaskPlanEvaluate;

/**
 * 年任务考核Service接口
 * 
 * @author niujs
 * @date 2024-05-14
 */
public interface IBaoliBizYearTaskPlanEvaluateService 
{
    /**
     * 查询年任务考核
     * 
     * @param id 年任务考核主键
     * @return 年任务考核
     */
    public BaoliBizYearTaskPlanEvaluate selectBaoliBizYearTaskPlanEvaluateById(Long id);

    /**
     * 查询年任务考核列表
     * 
     * @param baoliBizYearTaskPlanEvaluate 年任务考核
     * @return 年任务考核集合
     */
    public List<BaoliBizYearTaskPlanEvaluate> selectBaoliBizYearTaskPlanEvaluateList(BaoliBizYearTaskPlanEvaluate baoliBizYearTaskPlanEvaluate);

    /**
     * 新增年任务考核
     * 
     * @param baoliBizYearTaskPlanEvaluate 年任务考核
     * @return 结果
     */
    public int insertBaoliBizYearTaskPlanEvaluate(BaoliBizYearTaskPlanEvaluate baoliBizYearTaskPlanEvaluate);

    /**
     * 修改年任务考核
     * 
     * @param baoliBizYearTaskPlanEvaluate 年任务考核
     * @return 结果
     */
    public int updateBaoliBizYearTaskPlanEvaluate(BaoliBizYearTaskPlanEvaluate baoliBizYearTaskPlanEvaluate);

    /**
     * 批量删除年任务考核
     * 
     * @param ids 需要删除的年任务考核主键集合
     * @return 结果
     */
    public int deleteBaoliBizYearTaskPlanEvaluateByIds(Long[] ids);

    /**
     * 删除年任务考核信息
     * 
     * @param id 年任务考核主键
     * @return 结果
     */
    public int deleteBaoliBizYearTaskPlanEvaluateById(Long id);
}
