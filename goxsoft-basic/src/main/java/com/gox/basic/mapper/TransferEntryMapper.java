package com.gox.basic.mapper;

import java.util.List;
import com.gox.basic.domain.TransferEntry;
import org.springframework.stereotype.Repository;

/**
 *   Mapper接口
 * 
 * @author gox
 * @date 2021-03-09
 */
@Repository
public interface TransferEntryMapper 
{
    /**
     * 查询  
     * 
     * @param id   ID
     * @return   
     */
    public TransferEntry selectTransferEntryById(Long id);

    /**
     * 查询  列表
     * 
     * @param transferEntry   
     * @return   集合
     */
    public List<TransferEntry> selectTransferEntryList(TransferEntry transferEntry);

    /**
     * 新增  
     * 
     * @param transferEntry   
     * @return 结果
     */
    public int insertTransferEntry(TransferEntry transferEntry);

    /**
     * 修改  
     * 
     * @param transferEntry   
     * @return 结果
     */
    public int updateTransferEntry(TransferEntry transferEntry);

    /**
     * 删除  
     * 
     * @param id   ID
     * @return 结果
     */
    public int deleteTransferEntryById(Long id);

    /**
     * 批量删除  
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTransferEntryByIds(Long[] ids);
}
