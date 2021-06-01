package com.stdiet.custom.mapper;

import java.util.List;

import com.stdiet.custom.domain.SysWxAdLog;
import com.stdiet.custom.domain.SysWxSaleAccount;

/**
 * 微信账号Mapper接口
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
public interface SysWxSaleAccountMapper
{
    /**
     * 查询微信账号
     *
     * @param id 微信账号ID
     * @return 微信账号
     */
    public SysWxSaleAccount selectSysWxSaleAccountById(Long id);

    /**
     * 查询微信账号列表
     *
     * @param sysWxSaleAccount 微信账号
     * @return 微信账号集合
     */
    public List<SysWxSaleAccount> selectSysWxSaleAccountList(SysWxSaleAccount sysWxSaleAccount);

    /**
     * 新增微信账号
     *
     * @param sysWxSaleAccount 微信账号
     * @return 结果
     */
    public int insertSysWxSaleAccount(SysWxSaleAccount sysWxSaleAccount);

    /**
     * 修改微信账号
     *
     * @param sysWxSaleAccount 微信账号
     * @return 结果
     */
    public int updateSysWxSaleAccount(SysWxSaleAccount sysWxSaleAccount);

    /**
     * 删除微信账号
     *
     * @param id 微信账号ID
     * @return 结果
     */
    public int deleteSysWxSaleAccountById(Long id);

    /**
     * 批量删除微信账号
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysWxSaleAccountByIds(Long[] ids);

    /**
     * 根据微信号或手机号查询是否已存在
     * @param sysWxSaleAccount
     * @return
     */
    SysWxSaleAccount selectWxAccountByAccountOrPhone(SysWxSaleAccount sysWxSaleAccount);

    SysWxSaleAccount selectWxAdId();

    int insertWxAdLog(SysWxAdLog sysWxAdLog);

    /**
     * 获取可接粉的微信号以及分配的销售昵称
     * @return
     */
    List<SysWxSaleAccount> getWxAccountAndSale(SysWxSaleAccount sysWxSaleAccount);
}