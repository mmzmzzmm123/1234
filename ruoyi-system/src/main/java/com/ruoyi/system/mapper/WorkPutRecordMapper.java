package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkPutRecord;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-06
 */
public interface WorkPutRecordMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param putId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkPutRecord selectWorkPutRecordByPutId(Long putId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workPutRecord 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkPutRecord> selectWorkPutRecordList(WorkPutRecord workPutRecord);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workPutRecord 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkPutRecord(WorkPutRecord workPutRecord);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workPutRecord 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkPutRecord(WorkPutRecord workPutRecord);

    /**
     * 删除【请填写功能名称】
     * 
     * @param putId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkPutRecordByPutId(Long putId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param putIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkPutRecordByPutIds(Long[] putIds);
}
