package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkProcessRecord;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-06
 */
public interface WorkProcessRecordMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param processRecordId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkProcessRecord selectWorkProcessRecordByProcessRecordId(Long processRecordId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workProcessRecord 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkProcessRecord> selectWorkProcessRecordList(WorkProcessRecord workProcessRecord);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workProcessRecord 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkProcessRecord(WorkProcessRecord workProcessRecord);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workProcessRecord 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkProcessRecord(WorkProcessRecord workProcessRecord);

    /**
     * 删除【请填写功能名称】
     * 
     * @param processRecordId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkProcessRecordByProcessRecordId(Long processRecordId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param processRecordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkProcessRecordByProcessRecordIds(Long[] processRecordIds);
}
