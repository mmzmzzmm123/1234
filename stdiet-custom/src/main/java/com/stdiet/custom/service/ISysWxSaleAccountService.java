package com.stdiet.custom.service;

import java.util.List;
import com.stdiet.custom.domain.SysWxSaleAccount;

/**
 * 微信账号Service接口
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
public interface ISysWxSaleAccountService
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
     * 批量删除微信账号
     *
     * @param ids 需要删除的微信账号ID
     * @return 结果
     */
    public int deleteSysWxSaleAccountByIds(Long[] ids);

    /**
     * 删除微信账号信息
     *
     * @param id 微信账号ID
     * @return 结果
     */
    public int deleteSysWxSaleAccountById(Long id);

    /**
     * 根据微信号或手机号查询是否已存在
     * @param accountOrPhone 手机号或微信号
     * @param type 0微信号 1手机号
     * @return
     */
    SysWxSaleAccount selectWxAccountByAccountOrPhone(String accountOrPhone, int type);
}