package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BusTransactionsMapper;
import com.ruoyi.system.domain.BusTransactions;
import com.ruoyi.system.service.IBusTransactionsService;

/**
 * 记录用户的充值、提现等交易信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
@Service
public class BusTransactionsServiceImpl implements IBusTransactionsService 
{
    @Autowired
    private BusTransactionsMapper busTransactionsMapper;

    /**
     * 查询记录用户的充值、提现等交易信息
     * 
     * @param transactionId 记录用户的充值、提现等交易信息主键
     * @return 记录用户的充值、提现等交易信息
     */
    @Override
    public BusTransactions selectBusTransactionsByTransactionId(Long transactionId)
    {
        return busTransactionsMapper.selectBusTransactionsByTransactionId(transactionId);
    }

    /**
     * 查询记录用户的充值、提现等交易信息列表
     * 
     * @param busTransactions 记录用户的充值、提现等交易信息
     * @return 记录用户的充值、提现等交易信息
     */
    @Override
    public List<BusTransactions> selectBusTransactionsList(BusTransactions busTransactions)
    {
        return busTransactionsMapper.selectBusTransactionsList(busTransactions);
    }

    /**
     * 新增记录用户的充值、提现等交易信息
     * 
     * @param busTransactions 记录用户的充值、提现等交易信息
     * @return 结果
     */
    @Override
    public int insertBusTransactions(BusTransactions busTransactions)
    {
        return busTransactionsMapper.insertBusTransactions(busTransactions);
    }

    /**
     * 修改记录用户的充值、提现等交易信息
     * 
     * @param busTransactions 记录用户的充值、提现等交易信息
     * @return 结果
     */
    @Override
    public int updateBusTransactions(BusTransactions busTransactions)
    {
        return busTransactionsMapper.updateBusTransactions(busTransactions);
    }

    /**
     * 批量删除记录用户的充值、提现等交易信息
     * 
     * @param transactionIds 需要删除的记录用户的充值、提现等交易信息主键
     * @return 结果
     */
    @Override
    public int deleteBusTransactionsByTransactionIds(Long[] transactionIds)
    {
        return busTransactionsMapper.deleteBusTransactionsByTransactionIds(transactionIds);
    }

    /**
     * 删除记录用户的充值、提现等交易信息信息
     * 
     * @param transactionId 记录用户的充值、提现等交易信息主键
     * @return 结果
     */
    @Override
    public int deleteBusTransactionsByTransactionId(Long transactionId)
    {
        return busTransactionsMapper.deleteBusTransactionsByTransactionId(transactionId);
    }
}
