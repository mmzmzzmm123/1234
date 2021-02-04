package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysWxSaleAccountMapper;
import com.stdiet.custom.domain.SysWxSaleAccount;
import com.stdiet.custom.service.ISysWxSaleAccountService;

/**
 * 微信账号Service业务层处理
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
@Service
public class SysWxSaleAccountServiceImpl implements ISysWxSaleAccountService
{
    @Autowired
    private SysWxSaleAccountMapper sysWxSaleAccountMapper;

    /**
     * 查询微信账号
     *
     * @param id 微信账号ID
     * @return 微信账号
     */
    @Override
    public SysWxSaleAccount selectSysWxSaleAccountById(Long id)
    {
        return sysWxSaleAccountMapper.selectSysWxSaleAccountById(id);
    }

    /**
     * 查询微信账号列表
     *
     * @param sysWxSaleAccount 微信账号
     * @return 微信账号
     */
    @Override
    public List<SysWxSaleAccount> selectSysWxSaleAccountList(SysWxSaleAccount sysWxSaleAccount)
    {
        return sysWxSaleAccountMapper.selectSysWxSaleAccountList(sysWxSaleAccount);
    }

    /**
     * 新增微信账号
     *
     * @param sysWxSaleAccount 微信账号
     * @return 结果
     */
    @Override
    public int insertSysWxSaleAccount(SysWxSaleAccount sysWxSaleAccount)
    {
        sysWxSaleAccount.setCreateTime(DateUtils.getNowDate());
        return sysWxSaleAccountMapper.insertSysWxSaleAccount(sysWxSaleAccount);
    }

    /**
     * 修改微信账号
     *
     * @param sysWxSaleAccount 微信账号
     * @return 结果
     */
    @Override
    public int updateSysWxSaleAccount(SysWxSaleAccount sysWxSaleAccount)
    {
        sysWxSaleAccount.setUpdateTime(DateUtils.getNowDate());
        return sysWxSaleAccountMapper.updateSysWxSaleAccount(sysWxSaleAccount);
    }

    /**
     * 批量删除微信账号
     *
     * @param ids 需要删除的微信账号ID
     * @return 结果
     */
    @Override
    public int deleteSysWxSaleAccountByIds(Long[] ids)
    {
        return sysWxSaleAccountMapper.deleteSysWxSaleAccountByIds(ids);
    }

    /**
     * 删除微信账号信息
     *
     * @param id 微信账号ID
     * @return 结果
     */
    @Override
    public int deleteSysWxSaleAccountById(Long id)
    {
        return sysWxSaleAccountMapper.deleteSysWxSaleAccountById(id);
    }

    /**
     * 根据微信号或手机号查询是否已存在
     * @param accountOrPhone 手机号或微信号
     * @param type 0微信号 1手机号
     * @return
     */
    @Override
    public SysWxSaleAccount selectWxAccountByAccountOrPhone(String accountOrPhone, int type){
        SysWxSaleAccount param = new SysWxSaleAccount();
        if(type == 0){
            param.setWxAccount(accountOrPhone);
        }else{
            param.setWxPhone(accountOrPhone);
        }
        return sysWxSaleAccountMapper.selectWxAccountByAccountOrPhone(param);
    }
}