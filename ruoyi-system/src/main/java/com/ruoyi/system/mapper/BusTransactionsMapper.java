package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BusTransactions;

/**
 * 记录用户的充值、提现等交易信息Mapper接口
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
public interface BusTransactionsMapper 
{
    /**
     * 查询记录用户的充值、提现等交易信息
     * 
     * @param transactionId 记录用户的充值、提现等交易信息主键
     * @return 记录用户的充值、提现等交易信息
     */
    public BusTransactions selectBusTransactionsByTransactionId(Long transactionId);

    /**
     * 查询记录用户的充值、提现等交易信息列表
     * 
     * @param busTransactions 记录用户的充值、提现等交易信息
     * @return 记录用户的充值、提现等交易信息集合
     */
    public List<BusTransactions> selectBusTransactionsList(BusTransactions busTransactions);

    /**
     * 新增记录用户的充值、提现等交易信息
     * 
     * @param busTransactions 记录用户的充值、提现等交易信息
     * @return 结果
     */
    public int insertBusTransactions(BusTransactions busTransactions);

    /**
     * 修改记录用户的充值、提现等交易信息
     * 
     * @param busTransactions 记录用户的充值、提现等交易信息
     * @return 结果
     */
    public int updateBusTransactions(BusTransactions busTransactions);

    /**
     * 删除记录用户的充值、提现等交易信息
     * 
     * @param transactionId 记录用户的充值、提现等交易信息主键
     * @return 结果
     */
    public int deleteBusTransactionsByTransactionId(Long transactionId);

    /**
     * 批量删除记录用户的充值、提现等交易信息
     * 
     * @param transactionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusTransactionsByTransactionIds(Long[] transactionIds);
}
