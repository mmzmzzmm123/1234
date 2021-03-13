package com.gox.basic.mapper;

import java.util.List;
import com.gox.basic.domain.Transfer;

/**
 *   Mapper接口
 * 
 * @author gox
 * @date 2021-03-10
 */
public interface TransferMapper 
{
    /**
     * 查询  
     * 
     * @param id   ID
     * @return   
     */
    public Transfer selectTransferById(Long id);

    /**
     * 查询  列表
     * 
     * @param transfer   
     * @return   集合
     */
    public List<Transfer> selectTransferList(Transfer transfer);

    /**
     * 新增  
     * 
     * @param transfer   
     * @return 结果
     */
    public int insertTransfer(Transfer transfer);

    /**
     * 修改  
     * 
     * @param transfer   
     * @return 结果
     */
    public int updateTransfer(Transfer transfer);

    /**
     * 删除  
     * 
     * @param id   ID
     * @return 结果
     */
    public int deleteTransferById(Long id);

    /**
     * 批量删除  
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTransferByIds(Long[] ids);
}
