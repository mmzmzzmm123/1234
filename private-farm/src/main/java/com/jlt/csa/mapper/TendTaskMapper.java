package com.jlt.csa.mapper;

import java.util.List;
import com.jlt.csa.domain.TendTask;

/**
 * 打理任务Mapper接口
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public interface TendTaskMapper 
{
    /**
     * 查询打理任务
     * 
     * @param id 打理任务主键
     * @return 打理任务
     */
    public TendTask selectTendTaskById(Long id);

    /**
     * 查询打理任务列表
     * 
     * @param tendTask 打理任务
     * @return 打理任务集合
     */
    public List<TendTask> selectTendTaskList(TendTask tendTask);

    /**
     * 新增打理任务
     * 
     * @param tendTask 打理任务
     * @return 结果
     */
    public int insertTendTask(TendTask tendTask);

    /**
     * 修改打理任务
     * 
     * @param tendTask 打理任务
     * @return 结果
     */
    public int updateTendTask(TendTask tendTask);

    /**
     * 删除打理任务
     * 
     * @param id 打理任务主键
     * @return 结果
     */
    public int deleteTendTaskById(Long id);

    /**
     * 批量删除打理任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTendTaskByIds(Long[] ids);
}
