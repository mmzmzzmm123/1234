package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkGelRecord;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-07
 */
public interface WorkGelRecordMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param gelRecordId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkGelRecord selectWorkGelRecordByGelRecordId(Long gelRecordId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workGelRecord 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkGelRecord> selectWorkGelRecordList(WorkGelRecord workGelRecord);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workGelRecord 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkGelRecord(WorkGelRecord workGelRecord);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workGelRecord 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkGelRecord(WorkGelRecord workGelRecord);

    /**
     * 删除【请填写功能名称】
     * 
     * @param gelRecordId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkGelRecordByGelRecordId(Long gelRecordId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param gelRecordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkGelRecordByGelRecordIds(Long[] gelRecordIds);

    public int deleteWorkGelRecordByBatchNo(String batchNo);
}
