package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkExtractionRecord;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-07
 */
public interface WorkExtractionRecordMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param extractionRecordId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkExtractionRecord selectWorkExtractionRecordByExtractionRecordId(Long extractionRecordId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workExtractionRecord 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkExtractionRecord> selectWorkExtractionRecordList(WorkExtractionRecord workExtractionRecord);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workExtractionRecord 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkExtractionRecord(WorkExtractionRecord workExtractionRecord);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workExtractionRecord 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkExtractionRecord(WorkExtractionRecord workExtractionRecord);

    /**
     * 删除【请填写功能名称】
     * 
     * @param extractionRecordId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkExtractionRecordByExtractionRecordId(Long extractionRecordId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param extractionRecordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkExtractionRecordByExtractionRecordIds(Long[] extractionRecordIds);

    public int deleteWorkExtractionRecordByBatchNo(String batchNo);
}
