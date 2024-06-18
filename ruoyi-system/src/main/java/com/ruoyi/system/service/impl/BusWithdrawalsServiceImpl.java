package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BusWithdrawalsMapper;
import com.ruoyi.system.domain.BusWithdrawals;
import com.ruoyi.system.service.IBusWithdrawalsService;

/**
 * 记录用户的提现信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
@Service
public class BusWithdrawalsServiceImpl implements IBusWithdrawalsService 
{
    @Autowired
    private BusWithdrawalsMapper busWithdrawalsMapper;

    /**
     * 查询记录用户的提现信息
     * 
     * @param withdrawalId 记录用户的提现信息主键
     * @return 记录用户的提现信息
     */
    @Override
    public BusWithdrawals selectBusWithdrawalsByWithdrawalId(Long withdrawalId)
    {
        return busWithdrawalsMapper.selectBusWithdrawalsByWithdrawalId(withdrawalId);
    }

    /**
     * 查询记录用户的提现信息列表
     * 
     * @param busWithdrawals 记录用户的提现信息
     * @return 记录用户的提现信息
     */
    @Override
    public List<BusWithdrawals> selectBusWithdrawalsList(BusWithdrawals busWithdrawals)
    {
        return busWithdrawalsMapper.selectBusWithdrawalsList(busWithdrawals);
    }

    /**
     * 新增记录用户的提现信息
     * 
     * @param busWithdrawals 记录用户的提现信息
     * @return 结果
     */
    @Override
    public int insertBusWithdrawals(BusWithdrawals busWithdrawals)
    {
        return busWithdrawalsMapper.insertBusWithdrawals(busWithdrawals);
    }

    /**
     * 修改记录用户的提现信息
     * 
     * @param busWithdrawals 记录用户的提现信息
     * @return 结果
     */
    @Override
    public int updateBusWithdrawals(BusWithdrawals busWithdrawals)
    {
        return busWithdrawalsMapper.updateBusWithdrawals(busWithdrawals);
    }

    /**
     * 批量删除记录用户的提现信息
     * 
     * @param withdrawalIds 需要删除的记录用户的提现信息主键
     * @return 结果
     */
    @Override
    public int deleteBusWithdrawalsByWithdrawalIds(Long[] withdrawalIds)
    {
        return busWithdrawalsMapper.deleteBusWithdrawalsByWithdrawalIds(withdrawalIds);
    }

    /**
     * 删除记录用户的提现信息信息
     * 
     * @param withdrawalId 记录用户的提现信息主键
     * @return 结果
     */
    @Override
    public int deleteBusWithdrawalsByWithdrawalId(Long withdrawalId)
    {
        return busWithdrawalsMapper.deleteBusWithdrawalsByWithdrawalId(withdrawalId);
    }
}
