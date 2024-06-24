package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BusWalletsMapper;
import com.ruoyi.system.domain.BusWallets;
import com.ruoyi.system.service.IBusWalletsService;

/**
 * 存储用户的账户余额信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
@Service
public class BusWalletsServiceImpl implements IBusWalletsService 
{
    @Autowired
    private BusWalletsMapper busWalletsMapper;

    /**
     * 查询存储用户的账户余额信息
     * 
     * @param walletId 存储用户的账户余额信息主键
     * @return 存储用户的账户余额信息
     */
    @Override
    public BusWallets selectBusWalletsByWalletId(Long walletId)
    {
        return busWalletsMapper.selectBusWalletsByWalletId(walletId);
    }

    /**
     * 查询存储用户的账户余额信息列表
     * 
     * @param busWallets 存储用户的账户余额信息
     * @return 存储用户的账户余额信息
     */
    @Override
    public List<BusWallets> selectBusWalletsList(BusWallets busWallets)
    {
        return busWalletsMapper.selectBusWalletsList(busWallets);
    }

    /**
     * 新增存储用户的账户余额信息
     * 
     * @param busWallets 存储用户的账户余额信息
     * @return 结果
     */
    @Override
    public int insertBusWallets(BusWallets busWallets)
    {
        return busWalletsMapper.insertBusWallets(busWallets);
    }

    /**
     * 修改存储用户的账户余额信息
     * 
     * @param busWallets 存储用户的账户余额信息
     * @return 结果
     */
    @Override
    public int updateBusWallets(BusWallets busWallets)
    {
        return busWalletsMapper.updateBusWallets(busWallets);
    }

    /**
     * 批量删除存储用户的账户余额信息
     * 
     * @param walletIds 需要删除的存储用户的账户余额信息主键
     * @return 结果
     */
    @Override
    public int deleteBusWalletsByWalletIds(Long[] walletIds)
    {
        return busWalletsMapper.deleteBusWalletsByWalletIds(walletIds);
    }

    /**
     * 删除存储用户的账户余额信息信息
     * 
     * @param walletId 存储用户的账户余额信息主键
     * @return 结果
     */
    @Override
    public int deleteBusWalletsByWalletId(Long walletId)
    {
        return busWalletsMapper.deleteBusWalletsByWalletId(walletId);
    }
}
