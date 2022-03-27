package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.CommunicateRecord;

/**
 * 沟通记录Service接口
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public interface ICommunicateRecordService 
{
    /**
     * 查询沟通记录
     * 
     * @param id 沟通记录主键
     * @return 沟通记录
     */
    public CommunicateRecord selectCommunicateRecordById(Long id);

    /**
     * 查询沟通记录列表
     * 
     * @param communicateRecord 沟通记录
     * @return 沟通记录集合
     */
    public List<CommunicateRecord> selectCommunicateRecordList(CommunicateRecord communicateRecord);

    /**
     * 新增沟通记录
     * 
     * @param communicateRecord 沟通记录
     * @return 结果
     */
    public int insertCommunicateRecord(CommunicateRecord communicateRecord);

    /**
     * 修改沟通记录
     * 
     * @param communicateRecord 沟通记录
     * @return 结果
     */
    public int updateCommunicateRecord(CommunicateRecord communicateRecord);

    /**
     * 批量删除沟通记录
     * 
     * @param ids 需要删除的沟通记录主键集合
     * @return 结果
     */
    public int deleteCommunicateRecordByIds(Long[] ids);

    /**
     * 删除沟通记录信息
     * 
     * @param id 沟通记录主键
     * @return 结果
     */
    public int deleteCommunicateRecordById(Long id);
}
