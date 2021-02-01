package com.stdiet.custom.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysWxSaleAccountMapper;
import com.stdiet.custom.domain.SysWxSaleAccount;
import com.stdiet.custom.service.ISysWxSaleAccountService;

/**
 * 微信销售账号Service业务层处理
 * 
 * @author wonder
 * @date 2021-01-29
 */
@Service
public class SysWxSaleAccountServiceImpl implements ISysWxSaleAccountService 
{
    @Autowired
    private SysWxSaleAccountMapper sysWxSaleAccountMapper;

    /**
     * 查询微信销售账号
     * 
     * @param id 微信销售账号ID
     * @return 微信销售账号
     */
    @Override
    public SysWxSaleAccount selectSysWxSaleAccountById(Long id)
    {
        return sysWxSaleAccountMapper.selectSysWxSaleAccountById(id);
    }

    /**
     * 查询微信销售账号列表
     * 
     * @param sysWxSaleAccount 微信销售账号
     * @return 微信销售账号
     */
    @Override
    public List<SysWxSaleAccount> selectSysWxSaleAccountList(SysWxSaleAccount sysWxSaleAccount)
    {
        return sysWxSaleAccountMapper.selectSysWxSaleAccountList(sysWxSaleAccount);
    }

    /**
     * 新增微信销售账号
     * 
     * @param sysWxSaleAccount 微信销售账号
     * @return 结果
     */
    @Override
    public int insertSysWxSaleAccount(SysWxSaleAccount sysWxSaleAccount)
    {
        return sysWxSaleAccountMapper.insertSysWxSaleAccount(sysWxSaleAccount);
    }

    /**
     * 修改微信销售账号
     * 
     * @param sysWxSaleAccount 微信销售账号
     * @return 结果
     */
    @Override
    public int updateSysWxSaleAccount(SysWxSaleAccount sysWxSaleAccount)
    {
        return sysWxSaleAccountMapper.updateSysWxSaleAccount(sysWxSaleAccount);
    }

    /**
     * 批量删除微信销售账号
     * 
     * @param ids 需要删除的微信销售账号ID
     * @return 结果
     */
    @Override
    public int deleteSysWxSaleAccountByIds(Long[] ids)
    {
        return sysWxSaleAccountMapper.deleteSysWxSaleAccountByIds(ids);
    }

    /**
     * 删除微信销售账号信息
     * 
     * @param id 微信销售账号ID
     * @return 结果
     */
    @Override
    public int deleteSysWxSaleAccountById(Long id)
    {
        return sysWxSaleAccountMapper.deleteSysWxSaleAccountById(id);
    }
}