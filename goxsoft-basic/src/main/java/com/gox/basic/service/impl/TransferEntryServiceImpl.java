package com.gox.basic.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.basic.mapper.TransferEntryMapper;
import com.gox.basic.domain.TransferEntry;
import com.gox.basic.service.ITransferEntryService;

/**
 *   Service业务层处理
 * 
 * @author gox
 * @date 2021-03-10
 */
@Service
public class TransferEntryServiceImpl implements ITransferEntryService 
{
    @Autowired
    private TransferEntryMapper transferEntryMapper;

    /**
     * 查询  
     * 
     * @param id   ID
     * @return   
     */
    @Override
    public TransferEntry selectTransferEntryById(Long id)
    {
        return transferEntryMapper.selectTransferEntryById(id);
    }

    /**
     * 查询  列表
     * 
     * @param transferEntry   
     * @return   
     */
    @Override
    public List<TransferEntry> selectTransferEntryList(TransferEntry transferEntry)
    {
        return transferEntryMapper.selectTransferEntryList(transferEntry);
    }

    /**
     * 新增  
     * 
     * @param transferEntry   
     * @return 结果
     */
    @Override
    public int insertTransferEntry(TransferEntry transferEntry)
    {
        return transferEntryMapper.insertTransferEntry(transferEntry);
    }

    /**
     * 修改  
     * 
     * @param transferEntry   
     * @return 结果
     */
    @Override
    public int updateTransferEntry(TransferEntry transferEntry)
    {
        return transferEntryMapper.updateTransferEntry(transferEntry);
    }

    /**
     * 批量删除  
     * 
     * @param ids 需要删除的  ID
     * @return 结果
     */
    @Override
    public int deleteTransferEntryByIds(Long[] ids)
    {
        return transferEntryMapper.deleteTransferEntryByIds(ids);
    }

    /**
     * 删除  信息
     * 
     * @param id   ID
     * @return 结果
     */
    @Override
    public int deleteTransferEntryById(Long id)
    {
        return transferEntryMapper.deleteTransferEntryById(id);
    }
}
