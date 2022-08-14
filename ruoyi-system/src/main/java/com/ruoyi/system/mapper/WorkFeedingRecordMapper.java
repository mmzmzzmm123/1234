package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkFeedingRecord;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-07
 */
public interface WorkFeedingRecordMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param feedingRecordId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkFeedingRecord selectWorkFeedingRecordByFeedingRecordId(Long feedingRecordId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workFeedingRecord 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkFeedingRecord> selectWorkFeedingRecordList(WorkFeedingRecord workFeedingRecord);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workFeedingRecord 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkFeedingRecord(WorkFeedingRecord workFeedingRecord);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workFeedingRecord 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkFeedingRecord(WorkFeedingRecord workFeedingRecord);

    /**
     * 删除【请填写功能名称】
     * 
     * @param feedingRecordId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkFeedingRecordByFeedingRecordId(Long feedingRecordId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param feedingRecordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkFeedingRecordByFeedingRecordIds(Long[] feedingRecordIds);


    public int deleteWorkFeedingRecordByBatchNo(String batchNo);
}
