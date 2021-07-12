package com.apollo.usv.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apollo.usv.mapper.CollectorRecordMapper;
import com.apollo.usv.domain.CollectorRecord;
import com.apollo.usv.service.ICollectorRecordService;

/**
 * 水质记录Service业务层处理
 * 
 * @author lwq
 * @date 2021-07-02
 */
@Service
public class CollectorRecordServiceImpl implements ICollectorRecordService 
{
    @Autowired
    private CollectorRecordMapper collectorRecordMapper;

    /**
     * 查询水质记录
     * 
     * @param id 水质记录ID
     * @return 水质记录
     */
    @Override
    public CollectorRecord selectCollectorRecordById(Long id)
    {
        return collectorRecordMapper.selectCollectorRecordById(id);
    }

    /**
     * 查询水质记录列表
     * 
     * @param collectorRecord 水质记录
     * @return 水质记录
     */
    @Override
    public List<CollectorRecord> selectCollectorRecordList(CollectorRecord collectorRecord)
    {
        return collectorRecordMapper.selectCollectorRecordList(collectorRecord);
    }

    /**
     * 新增水质记录
     * 
     * @param collectorRecord 水质记录
     * @return 结果
     */
    @Override
    public int insertCollectorRecord(CollectorRecord collectorRecord)
    {
        return collectorRecordMapper.insertCollectorRecord(collectorRecord);
    }

    /**
     * 修改水质记录
     * 
     * @param collectorRecord 水质记录
     * @return 结果
     */
    @Override
    public int updateCollectorRecord(CollectorRecord collectorRecord)
    {
        return collectorRecordMapper.updateCollectorRecord(collectorRecord);
    }

    /**
     * 批量删除水质记录
     * 
     * @param ids 需要删除的水质记录ID
     * @return 结果
     */
    @Override
    public int deleteCollectorRecordByIds(Long[] ids)
    {
        return collectorRecordMapper.deleteCollectorRecordByIds(ids);
    }

    /**
     * 删除水质记录信息
     * 
     * @param id 水质记录ID
     * @return 结果
     */
    @Override
    public int deleteCollectorRecordById(Long id)
    {
        return collectorRecordMapper.deleteCollectorRecordById(id);
    }
}
