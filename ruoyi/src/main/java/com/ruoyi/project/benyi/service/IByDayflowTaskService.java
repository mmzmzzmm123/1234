package com.ruoyi.project.benyi.service;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByDayflowTask;

/**
 * 一日流程任务Service接口
 * 
 * @author tsbz
 * @date 2020-05-13
 */
public interface IByDayflowTaskService 
{
    /**
     * 查询一日流程任务
     * 
     * @param id 一日流程任务ID
     * @return 一日流程任务
     */
    public ByDayflowTask selectByDayflowTaskById(Long id);

    /**
     * 查询一日流程任务列表
     * 
     * @param byDayflowTask 一日流程任务
     * @return 一日流程任务集合
     */
    public List<ByDayflowTask> selectByDayflowTaskList(ByDayflowTask byDayflowTask);

    /**
     * 新增一日流程任务
     * 
     * @param byDayflowTask 一日流程任务
     * @return 结果
     */
    public int insertByDayflowTask(ByDayflowTask byDayflowTask);

    /**
     * 修改一日流程任务
     * 
     * @param byDayflowTask 一日流程任务
     * @return 结果
     */
    public int updateByDayflowTask(ByDayflowTask byDayflowTask);

    /**
     * 批量删除一日流程任务
     * 
     * @param ids 需要删除的一日流程任务ID
     * @return 结果
     */
    public int deleteByDayflowTaskByIds(Long[] ids);

    /**
     * 删除一日流程任务信息
     * 
     * @param id 一日流程任务ID
     * @return 结果
     */
    public int deleteByDayflowTaskById(Long id);
}
