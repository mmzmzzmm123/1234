package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkConcentrateRecord;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-07
 */
public interface WorkConcentrateRecordMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param concentrateRecordId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkConcentrateRecord selectWorkConcentrateRecordByConcentrateRecordId(Long concentrateRecordId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workConcentrateRecord 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkConcentrateRecord> selectWorkConcentrateRecordList(WorkConcentrateRecord workConcentrateRecord);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workConcentrateRecord 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkConcentrateRecord(WorkConcentrateRecord workConcentrateRecord);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workConcentrateRecord 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkConcentrateRecord(WorkConcentrateRecord workConcentrateRecord);

    /**
     * 删除【请填写功能名称】
     * 
     * @param concentrateRecordId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkConcentrateRecordByConcentrateRecordId(Long concentrateRecordId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param concentrateRecordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkConcentrateRecordByConcentrateRecordIds(Long[] concentrateRecordIds);

    public int deleteWorkConcentrateRecordByBatchNo(String batchNo);
}
