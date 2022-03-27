package com.jlt.csa.mapper;

import java.util.List;
import com.jlt.csa.domain.ArchedRecord;

/**
 * 拱棚使用记录Mapper接口
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public interface ArchedRecordMapper 
{
    /**
     * 查询拱棚使用记录
     * 
     * @param id 拱棚使用记录主键
     * @return 拱棚使用记录
     */
    public ArchedRecord selectArchedRecordById(Long id);

    /**
     * 查询拱棚使用记录列表
     * 
     * @param archedRecord 拱棚使用记录
     * @return 拱棚使用记录集合
     */
    public List<ArchedRecord> selectArchedRecordList(ArchedRecord archedRecord);

    /**
     * 新增拱棚使用记录
     * 
     * @param archedRecord 拱棚使用记录
     * @return 结果
     */
    public int insertArchedRecord(ArchedRecord archedRecord);

    /**
     * 修改拱棚使用记录
     * 
     * @param archedRecord 拱棚使用记录
     * @return 结果
     */
    public int updateArchedRecord(ArchedRecord archedRecord);

    /**
     * 删除拱棚使用记录
     * 
     * @param id 拱棚使用记录主键
     * @return 结果
     */
    public int deleteArchedRecordById(Long id);

    /**
     * 批量删除拱棚使用记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteArchedRecordByIds(Long[] ids);
}
