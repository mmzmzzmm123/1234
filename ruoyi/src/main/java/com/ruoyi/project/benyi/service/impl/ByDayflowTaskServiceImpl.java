package com.ruoyi.project.benyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByDayflowTaskMapper;
import com.ruoyi.project.benyi.domain.ByDayflowTask;
import com.ruoyi.project.benyi.service.IByDayflowTaskService;

/**
 * 一日流程任务Service业务层处理
 * 
 * @author tsbz
 * @date 2020-05-13
 */
@Service
public class ByDayflowTaskServiceImpl implements IByDayflowTaskService 
{
    @Autowired
    private ByDayflowTaskMapper byDayflowTaskMapper;

    /**
     * 查询一日流程任务
     * 
     * @param id 一日流程任务ID
     * @return 一日流程任务
     */
    @Override
    public ByDayflowTask selectByDayflowTaskById(Long id)
    {
        return byDayflowTaskMapper.selectByDayflowTaskById(id);
    }

    /**
     * 查询一日流程任务列表
     * 
     * @param byDayflowTask 一日流程任务
     * @return 一日流程任务
     */
    @Override
    public List<ByDayflowTask> selectByDayflowTaskList(ByDayflowTask byDayflowTask)
    {
        return byDayflowTaskMapper.selectByDayflowTaskList(byDayflowTask);
    }

    /**
     * 新增一日流程任务
     * 
     * @param byDayflowTask 一日流程任务
     * @return 结果
     */
    @Override
    public int insertByDayflowTask(ByDayflowTask byDayflowTask)
    {
        return byDayflowTaskMapper.insertByDayflowTask(byDayflowTask);
    }

    /**
     * 修改一日流程任务
     * 
     * @param byDayflowTask 一日流程任务
     * @return 结果
     */
    @Override
    public int updateByDayflowTask(ByDayflowTask byDayflowTask)
    {
        return byDayflowTaskMapper.updateByDayflowTask(byDayflowTask);
    }

    /**
     * 批量删除一日流程任务
     * 
     * @param ids 需要删除的一日流程任务ID
     * @return 结果
     */
    @Override
    public int deleteByDayflowTaskByIds(Long[] ids)
    {
        return byDayflowTaskMapper.deleteByDayflowTaskByIds(ids);
    }

    /**
     * 删除一日流程任务信息
     * 
     * @param id 一日流程任务ID
     * @return 结果
     */
    @Override
    public int deleteByDayflowTaskById(Long id)
    {
        return byDayflowTaskMapper.deleteByDayflowTaskById(id);
    }
}
