package com.baoli.task.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.task.mapper.BaoliBizMonthTaskMapper;
import com.baoli.task.domain.BaoliBizMonthTask;
import com.baoli.task.service.IBaoliBizMonthTaskService;

/**
 * 月度任务Service业务层处理
 * 
 * @author niujs
 * @date 2024-05-14
 */
@Service
public class BaoliBizMonthTaskServiceImpl implements IBaoliBizMonthTaskService 
{
    @Autowired
    private BaoliBizMonthTaskMapper baoliBizMonthTaskMapper;

    /**
     * 查询月度任务
     * 
     * @param id 月度任务主键
     * @return 月度任务
     */
    @Override
    public BaoliBizMonthTask selectBaoliBizMonthTaskById(Long id)
    {
        return baoliBizMonthTaskMapper.selectBaoliBizMonthTaskById(id);
    }

    /**
     * 查询月度任务列表
     * 
     * @param baoliBizMonthTask 月度任务
     * @return 月度任务
     */
    @Override
    public List<BaoliBizMonthTask> selectBaoliBizMonthTaskList(BaoliBizMonthTask baoliBizMonthTask)
    {
        return baoliBizMonthTaskMapper.selectBaoliBizMonthTaskList(baoliBizMonthTask);
    }

    /**
     * 新增月度任务
     * 
     * @param baoliBizMonthTask 月度任务
     * @return 结果
     */
    @Override
    public int insertBaoliBizMonthTask(BaoliBizMonthTask baoliBizMonthTask)
    {
        return baoliBizMonthTaskMapper.insertBaoliBizMonthTask(baoliBizMonthTask);
    }

    /**
     * 修改月度任务
     * 
     * @param baoliBizMonthTask 月度任务
     * @return 结果
     */
    @Override
    public int updateBaoliBizMonthTask(BaoliBizMonthTask baoliBizMonthTask)
    {
        return baoliBizMonthTaskMapper.updateBaoliBizMonthTask(baoliBizMonthTask);
    }

    /**
     * 批量删除月度任务
     * 
     * @param ids 需要删除的月度任务主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizMonthTaskByIds(Long[] ids)
    {
        return baoliBizMonthTaskMapper.deleteBaoliBizMonthTaskByIds(ids);
    }

    /**
     * 删除月度任务信息
     * 
     * @param id 月度任务主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizMonthTaskById(Long id)
    {
        return baoliBizMonthTaskMapper.deleteBaoliBizMonthTaskById(id);
    }
}
