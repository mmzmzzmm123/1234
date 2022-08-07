package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkReactionBatch;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-06
 */
public interface WorkReactionBatchMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param batchId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkReactionBatch selectWorkReactionBatchByBatchId(Long batchId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workReactionBatch 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkReactionBatch> selectWorkReactionBatchList(WorkReactionBatch workReactionBatch);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workReactionBatch 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkReactionBatch(WorkReactionBatch workReactionBatch);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workReactionBatch 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkReactionBatch(WorkReactionBatch workReactionBatch);

    /**
     * 删除【请填写功能名称】
     * 
     * @param batchId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkReactionBatchByBatchId(Long batchId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param batchIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkReactionBatchByBatchIds(Long[] batchIds);

    /**
     * 删除通过批次号
     *
     * @param batchNo【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkReactionBatchByBatchNo(String batchNo);
}
