package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.ArchedRecordMapper;
import com.jlt.csa.domain.ArchedRecord;
import com.jlt.csa.service.IArchedRecordService;

/**
 * 拱棚使用记录Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@Service
public class ArchedRecordServiceImpl implements IArchedRecordService 
{
    @Autowired
    private ArchedRecordMapper archedRecordMapper;

    /**
     * 查询拱棚使用记录
     * 
     * @param id 拱棚使用记录主键
     * @return 拱棚使用记录
     */
    @Override
    public ArchedRecord selectArchedRecordById(Long id)
    {
        return archedRecordMapper.selectArchedRecordById(id);
    }

    /**
     * 查询拱棚使用记录列表
     * 
     * @param archedRecord 拱棚使用记录
     * @return 拱棚使用记录
     */
    @Override
    public List<ArchedRecord> selectArchedRecordList(ArchedRecord archedRecord)
    {
        return archedRecordMapper.selectArchedRecordList(archedRecord);
    }

    /**
     * 新增拱棚使用记录
     * 
     * @param archedRecord 拱棚使用记录
     * @return 结果
     */
    @Override
    public int insertArchedRecord(ArchedRecord archedRecord)
    {
        archedRecord.setCreateTime(DateUtils.getNowDate());
        return archedRecordMapper.insertArchedRecord(archedRecord);
    }

    /**
     * 修改拱棚使用记录
     * 
     * @param archedRecord 拱棚使用记录
     * @return 结果
     */
    @Override
    public int updateArchedRecord(ArchedRecord archedRecord)
    {
        archedRecord.setUpdateTime(DateUtils.getNowDate());
        return archedRecordMapper.updateArchedRecord(archedRecord);
    }

    /**
     * 批量删除拱棚使用记录
     * 
     * @param ids 需要删除的拱棚使用记录主键
     * @return 结果
     */
    @Override
    public int deleteArchedRecordByIds(Long[] ids)
    {
        return archedRecordMapper.deleteArchedRecordByIds(ids);
    }

    /**
     * 删除拱棚使用记录信息
     * 
     * @param id 拱棚使用记录主键
     * @return 结果
     */
    @Override
    public int deleteArchedRecordById(Long id)
    {
        return archedRecordMapper.deleteArchedRecordById(id);
    }
}
