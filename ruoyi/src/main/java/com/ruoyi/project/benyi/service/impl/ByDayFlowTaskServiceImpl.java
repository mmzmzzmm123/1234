package com.ruoyi.project.benyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByDayFlowTaskMapper;
import com.ruoyi.project.benyi.domain.ByDayFlowTask;
import com.ruoyi.project.benyi.service.IByDayFlowTaskService;

/**
 * 一日流程任务Service业务层处理
 * 
 * @author tsbz
 * @date 2020-05-14
 */
@Service
public class ByDayFlowTaskServiceImpl implements IByDayFlowTaskService 
{
    @Autowired
    private ByDayFlowTaskMapper byDayFlowTaskMapper;

    /**
     * 查询一日流程任务
     * 
     * @param code 一日流程任务CODE
     * @return 一日流程任务
     */
    @Override
    public ByDayFlowTask selectByDayFlowTaskByCode(Long code)
    {
        return byDayFlowTaskMapper.selectByDayFlowTaskByCode(code);
    }

    /**
     * 查询一日流程任务列表
     * 
     * @param byDayFlowTask 一日流程任务
     * @return 一日流程任务
     */
    @Override
    public List<ByDayFlowTask> selectByDayFlowTaskList(ByDayFlowTask byDayFlowTask)
    {
        return byDayFlowTaskMapper.selectByDayFlowTaskList(byDayFlowTask);
    }

    /**
     * 新增一日流程任务
     * 
     * @param byDayFlowTask 一日流程任务
     * @return 结果
     */
    @Override
    public int insertByDayFlowTask(ByDayFlowTask byDayFlowTask)
    {
        return byDayFlowTaskMapper.insertByDayFlowTask(byDayFlowTask);
    }

    /**
     * 修改一日流程任务
     * 
     * @param byDayFlowTask 一日流程任务
     * @return 结果
     */
    @Override
    public int updateByDayFlowTask(ByDayFlowTask byDayFlowTask)
    {
        return byDayFlowTaskMapper.updateByDayFlowTask(byDayFlowTask);
    }

    /**
     * 批量删除一日流程任务
     * 
     * @param codes 需要删除的一日流程任务CODE
     * @return 结果
     */
    @Override
    public int deleteByDayFlowTaskByCodes(Long[] codes)
    {
        return byDayFlowTaskMapper.deleteByDayFlowTaskByCodes(codes);
    }

    /**
     * 删除一日流程任务信息
     * 
     * @param code 一日流程任务CODE
     * @return 结果
     */
    @Override
    public int deleteByDayFlowTaskByCode(Long code)
    {
        return byDayFlowTaskMapper.deleteByDayFlowTaskByCode(code);
    }
}
