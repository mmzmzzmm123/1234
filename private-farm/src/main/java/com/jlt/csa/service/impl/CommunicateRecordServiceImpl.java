package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.CommunicateRecordMapper;
import com.jlt.csa.domain.CommunicateRecord;
import com.jlt.csa.service.ICommunicateRecordService;

/**
 * 沟通记录Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@Service
public class CommunicateRecordServiceImpl implements ICommunicateRecordService 
{
    @Autowired
    private CommunicateRecordMapper communicateRecordMapper;

    /**
     * 查询沟通记录
     * 
     * @param id 沟通记录主键
     * @return 沟通记录
     */
    @Override
    public CommunicateRecord selectCommunicateRecordById(Long id)
    {
        return communicateRecordMapper.selectCommunicateRecordById(id);
    }

    /**
     * 查询沟通记录列表
     * 
     * @param communicateRecord 沟通记录
     * @return 沟通记录
     */
    @Override
    public List<CommunicateRecord> selectCommunicateRecordList(CommunicateRecord communicateRecord)
    {
        return communicateRecordMapper.selectCommunicateRecordList(communicateRecord);
    }

    /**
     * 新增沟通记录
     * 
     * @param communicateRecord 沟通记录
     * @return 结果
     */
    @Override
    public int insertCommunicateRecord(CommunicateRecord communicateRecord)
    {
        communicateRecord.setCreateTime(DateUtils.getNowDate());
        return communicateRecordMapper.insertCommunicateRecord(communicateRecord);
    }

    /**
     * 修改沟通记录
     * 
     * @param communicateRecord 沟通记录
     * @return 结果
     */
    @Override
    public int updateCommunicateRecord(CommunicateRecord communicateRecord)
    {
        communicateRecord.setUpdateTime(DateUtils.getNowDate());
        return communicateRecordMapper.updateCommunicateRecord(communicateRecord);
    }

    /**
     * 批量删除沟通记录
     * 
     * @param ids 需要删除的沟通记录主键
     * @return 结果
     */
    @Override
    public int deleteCommunicateRecordByIds(Long[] ids)
    {
        return communicateRecordMapper.deleteCommunicateRecordByIds(ids);
    }

    /**
     * 删除沟通记录信息
     * 
     * @param id 沟通记录主键
     * @return 结果
     */
    @Override
    public int deleteCommunicateRecordById(Long id)
    {
        return communicateRecordMapper.deleteCommunicateRecordById(id);
    }
}
