package com.baoli.task.mapper;

import java.util.List;
import com.baoli.task.domain.BaoliBizYearTaskPlanEvaluate;

/**
 * 年任务考核Mapper接口
 * 
 * @author niujs
 * @date 2024-05-14
 */
public interface BaoliBizYearTaskPlanEvaluateMapper 
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
     * 删除年任务考核
     * 
     * @param id 年任务考核主键
     * @return 结果
     */
    public int deleteBaoliBizYearTaskPlanEvaluateById(Long id);

    /**
     * 批量删除年任务考核
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizYearTaskPlanEvaluateByIds(Long[] ids);
}
