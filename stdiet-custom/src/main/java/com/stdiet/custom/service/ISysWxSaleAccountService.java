package com.stdiet.custom.service;

import java.util.List;

import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.custom.domain.SysWxSaleAccount;

/**
 * 微信销售账号Service接口
 * 
 * @author wonder
 * @date 2021-01-29
 */
public interface ISysWxSaleAccountService 
{
    /**
     * 查询微信销售账号
     * 
     * @param id 微信销售账号ID
     * @return 微信销售账号
     */
    public SysWxSaleAccount selectSysWxSaleAccountById(Long id);

    /**
     * 查询微信销售账号列表
     * 
     * @param sysWxSaleAccount 微信销售账号
     * @return 微信销售账号集合
     */
    public List<SysWxSaleAccount> selectSysWxSaleAccountList(SysWxSaleAccount sysWxSaleAccount);

    /**
     * 新增微信销售账号
     * 
     * @param sysWxSaleAccount 微信销售账号
     * @return 结果
     */
    public int insertSysWxSaleAccount(SysWxSaleAccount sysWxSaleAccount);

    /**
     * 修改微信销售账号
     * 
     * @param sysWxSaleAccount 微信销售账号
     * @return 结果
     */
    public int updateSysWxSaleAccount(SysWxSaleAccount sysWxSaleAccount);

    /**
     * 批量删除微信销售账号
     * 
     * @param ids 需要删除的微信销售账号ID
     * @return 结果
     */
    public int deleteSysWxSaleAccountByIds(Long[] ids);

    /**
     * 删除微信销售账号信息
     * 
     * @param id 微信销售账号ID
     * @return 结果
     */
    public int deleteSysWxSaleAccountById(Long id);

}