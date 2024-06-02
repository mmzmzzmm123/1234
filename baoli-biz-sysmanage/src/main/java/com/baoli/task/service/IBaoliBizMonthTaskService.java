package com.baoli.task.service;

import java.util.List;
import com.baoli.task.domain.BaoliBizMonthTask;

/**
 * 月度任务Service接口
 * 
 * @author niujs
 * @date 2024-05-14
 */
public interface IBaoliBizMonthTaskService 
{
    /**
     * 查询月度任务
     * 
     * @param id 月度任务主键
     * @return 月度任务
     */
    public BaoliBizMonthTask selectBaoliBizMonthTaskById(Long id);

    /**
     * 查询月度任务列表
     * 
     * @param baoliBizMonthTask 月度任务
     * @return 月度任务集合
     */
    public List<BaoliBizMonthTask> selectBaoliBizMonthTaskList(BaoliBizMonthTask baoliBizMonthTask);

    /**
     * 新增月度任务
     * 
     * @param baoliBizMonthTask 月度任务
     * @return 结果
     */
    public int insertBaoliBizMonthTask(BaoliBizMonthTask baoliBizMonthTask);

    /**
     * 修改月度任务
     * 
     * @param baoliBizMonthTask 月度任务
     * @return 结果
     */
    public int updateBaoliBizMonthTask(BaoliBizMonthTask baoliBizMonthTask);

    /**
     * 批量删除月度任务
     * 
     * @param ids 需要删除的月度任务主键集合
     * @return 结果
     */
    public int deleteBaoliBizMonthTaskByIds(Long[] ids);

    /**
     * 删除月度任务信息
     * 
     * @param id 月度任务主键
     * @return 结果
     */
    public int deleteBaoliBizMonthTaskById(Long id);
}
