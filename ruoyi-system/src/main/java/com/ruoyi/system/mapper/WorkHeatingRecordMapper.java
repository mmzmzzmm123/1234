package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkHeatingRecord;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-07
 */
public interface WorkHeatingRecordMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param heatingRecordId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkHeatingRecord selectWorkHeatingRecordByHeatingRecordId(Long heatingRecordId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workHeatingRecord 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkHeatingRecord> selectWorkHeatingRecordList(WorkHeatingRecord workHeatingRecord);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workHeatingRecord 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkHeatingRecord(WorkHeatingRecord workHeatingRecord);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workHeatingRecord 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkHeatingRecord(WorkHeatingRecord workHeatingRecord);

    /**
     * 删除【请填写功能名称】
     * 
     * @param heatingRecordId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkHeatingRecordByHeatingRecordId(Long heatingRecordId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param heatingRecordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkHeatingRecordByHeatingRecordIds(Long[] heatingRecordIds);


    public int deleteWorkHeatingRecordByBatchNo(String batchNo);
}
