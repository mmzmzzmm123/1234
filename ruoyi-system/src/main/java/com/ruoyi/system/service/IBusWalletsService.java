package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BusWallets;

/**
 * 存储用户的账户余额信息Service接口
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
public interface IBusWalletsService 
{
    /**
     * 查询存储用户的账户余额信息
     * 
     * @param walletId 存储用户的账户余额信息主键
     * @return 存储用户的账户余额信息
     */
    public BusWallets selectBusWalletsByWalletId(Long walletId);

    /**
     * 查询存储用户的账户余额信息列表
     * 
     * @param busWallets 存储用户的账户余额信息
     * @return 存储用户的账户余额信息集合
     */
    public List<BusWallets> selectBusWalletsList(BusWallets busWallets);

    /**
     * 新增存储用户的账户余额信息
     * 
     * @param busWallets 存储用户的账户余额信息
     * @return 结果
     */
    public int insertBusWallets(BusWallets busWallets);

    /**
     * 修改存储用户的账户余额信息
     * 
     * @param busWallets 存储用户的账户余额信息
     * @return 结果
     */
    public int updateBusWallets(BusWallets busWallets);

    /**
     * 批量删除存储用户的账户余额信息
     * 
     * @param walletIds 需要删除的存储用户的账户余额信息主键集合
     * @return 结果
     */
    public int deleteBusWalletsByWalletIds(Long[] walletIds);

    /**
     * 删除存储用户的账户余额信息信息
     * 
     * @param walletId 存储用户的账户余额信息主键
     * @return 结果
     */
    public int deleteBusWalletsByWalletId(Long walletId);
}
