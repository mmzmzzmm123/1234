package com.jlt.csa.mapper;

import java.util.List;
import com.jlt.csa.domain.CommunicateRecord;

/**
 * 沟通记录Mapper接口
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public interface CommunicateRecordMapper 
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
     * 删除沟通记录
     * 
     * @param id 沟通记录主键
     * @return 结果
     */
    public int deleteCommunicateRecordById(Long id);

    /**
     * 批量删除沟通记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCommunicateRecordByIds(Long[] ids);
}
