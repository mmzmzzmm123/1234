package com.ruoyi.project.benyi.service;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByDayFlowTask;

/**
 * 一日流程任务Service接口
 * 
 * @author tsbz
 * @date 2020-05-14
 */
public interface IByDayFlowTaskService 
{
    /**
     * 查询一日流程任务
     * 
     * @param code 一日流程任务CODE
     * @return 一日流程任务
     */
    public ByDayFlowTask selectByDayFlowTaskByCode(Long code);

    /**
     * 查询一日流程任务列表
     * 
     * @param byDayFlowTask 一日流程任务
     * @return 一日流程任务集合
     */
    public List<ByDayFlowTask> selectByDayFlowTaskList(ByDayFlowTask byDayFlowTask);

    /**
     * 新增一日流程任务
     * 
     * @param byDayFlowTask 一日流程任务
     * @return 结果
     */
    public int insertByDayFlowTask(ByDayFlowTask byDayFlowTask);

    /**
     * 修改一日流程任务
     * 
     * @param byDayFlowTask 一日流程任务
     * @return 结果
     */
    public int updateByDayFlowTask(ByDayFlowTask byDayFlowTask);

    /**
     * 批量删除一日流程任务
     * 
     * @param codes 需要删除的一日流程任务CODE
     * @return 结果
     */
    public int deleteByDayFlowTaskByCodes(Long[] codes);

    /**
     * 删除一日流程任务信息
     * 
     * @param code 一日流程任务CODE
     * @return 结果
     */
    public int deleteByDayFlowTaskByCode(Long code);
}
