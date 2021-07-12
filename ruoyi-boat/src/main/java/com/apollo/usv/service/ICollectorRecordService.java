package com.apollo.usv.service;

import java.util.List;
import com.apollo.usv.domain.CollectorRecord;

/**
 * 水质记录Service接口
 * 
 * @author lwq
 * @date 2021-07-02
 */
public interface ICollectorRecordService 
{
    /**
     * 查询水质记录
     * 
     * @param id 水质记录ID
     * @return 水质记录
     */
    public CollectorRecord selectCollectorRecordById(Long id);

    /**
     * 查询水质记录列表
     * 
     * @param collectorRecord 水质记录
     * @return 水质记录集合
     */
    public List<CollectorRecord> selectCollectorRecordList(CollectorRecord collectorRecord);

    /**
     * 新增水质记录
     * 
     * @param collectorRecord 水质记录
     * @return 结果
     */
    public int insertCollectorRecord(CollectorRecord collectorRecord);

    /**
     * 修改水质记录
     * 
     * @param collectorRecord 水质记录
     * @return 结果
     */
    public int updateCollectorRecord(CollectorRecord collectorRecord);

    /**
     * 批量删除水质记录
     * 
     * @param ids 需要删除的水质记录ID
     * @return 结果
     */
    public int deleteCollectorRecordByIds(Long[] ids);

    /**
     * 删除水质记录信息
     * 
     * @param id 水质记录ID
     * @return 结果
     */
    public int deleteCollectorRecordById(Long id);
}
