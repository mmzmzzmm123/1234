package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.TendTaskMapper;
import com.jlt.csa.domain.TendTask;
import com.jlt.csa.service.ITendTaskService;

/**
 * 打理任务Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@Service
public class TendTaskServiceImpl implements ITendTaskService 
{
    @Autowired
    private TendTaskMapper tendTaskMapper;

    /**
     * 查询打理任务
     * 
     * @param id 打理任务主键
     * @return 打理任务
     */
    @Override
    public TendTask selectTendTaskById(Long id)
    {
        return tendTaskMapper.selectTendTaskById(id);
    }

    /**
     * 查询打理任务列表
     * 
     * @param tendTask 打理任务
     * @return 打理任务
     */
    @Override
    public List<TendTask> selectTendTaskList(TendTask tendTask)
    {
        return tendTaskMapper.selectTendTaskList(tendTask);
    }

    /**
     * 新增打理任务
     * 
     * @param tendTask 打理任务
     * @return 结果
     */
    @Override
    public int insertTendTask(TendTask tendTask)
    {
        tendTask.setCreateTime(DateUtils.getNowDate());
        return tendTaskMapper.insertTendTask(tendTask);
    }

    /**
     * 修改打理任务
     * 
     * @param tendTask 打理任务
     * @return 结果
     */
    @Override
    public int updateTendTask(TendTask tendTask)
    {
        tendTask.setUpdateTime(DateUtils.getNowDate());
        return tendTaskMapper.updateTendTask(tendTask);
    }

    /**
     * 批量删除打理任务
     * 
     * @param ids 需要删除的打理任务主键
     * @return 结果
     */
    @Override
    public int deleteTendTaskByIds(Long[] ids)
    {
        return tendTaskMapper.deleteTendTaskByIds(ids);
    }

    /**
     * 删除打理任务信息
     * 
     * @param id 打理任务主键
     * @return 结果
     */
    @Override
    public int deleteTendTaskById(Long id)
    {
        return tendTaskMapper.deleteTendTaskById(id);
    }
}
