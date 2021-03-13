package com.gox.basic.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.basic.mapper.TransferMapper;
import com.gox.basic.domain.Transfer;
import com.gox.basic.service.ITransferService;

/**
 *   Service业务层处理
 * 
 * @author gox
 * @date 2021-03-10
 */
@Service
public class TransferServiceImpl implements ITransferService 
{
    @Autowired
    private TransferMapper transferMapper;

    /**
     * 查询  
     * 
     * @param id   ID
     * @return   
     */
    @Override
    public Transfer selectTransferById(Long id)
    {
        return transferMapper.selectTransferById(id);
    }

    /**
     * 查询  列表
     * 
     * @param transfer   
     * @return   
     */
    @Override
    public List<Transfer> selectTransferList(Transfer transfer)
    {
        return transferMapper.selectTransferList(transfer);
    }

    /**
     * 新增  
     * 
     * @param transfer   
     * @return 结果
     */
    @Override
    public int insertTransfer(Transfer transfer)
    {
        transfer.setStatus("待审核");
        return transferMapper.insertTransfer(transfer);
    }

    /**
     * 修改  
     * 
     * @param transfer   
     * @return 结果
     */
    @Override
    public int updateTransfer(Transfer transfer)
    {
        return transferMapper.updateTransfer(transfer);
    }

    /**
     * 批量删除  
     * 
     * @param ids 需要删除的  ID
     * @return 结果
     */
    @Override
    public int deleteTransferByIds(Long[] ids)
    {
        return transferMapper.deleteTransferByIds(ids);
    }

    /**
     * 删除  信息
     * 
     * @param id   ID
     * @return 结果
     */
    @Override
    public int deleteTransferById(Long id)
    {
        return transferMapper.deleteTransferById(id);
    }
}
