package com.ruoyi.fin.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fin.mapper.FinAccountMapper;
import com.ruoyi.fin.domain.FinAccount;
import com.ruoyi.fin.service.IFinAccountService;

/**
 * 记账账户Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-23
 */
@Service
public class FinAccountServiceImpl implements IFinAccountService 
{
    @Autowired
    private FinAccountMapper finAccountMapper;

    /**
     * 查询记账账户
     * 
     * @param acctId 记账账户主键
     * @return 记账账户
     */
    @Override
    public FinAccount selectFinAccountByAcctId(Long acctId)
    {
        return finAccountMapper.selectFinAccountByAcctId(acctId);
    }

    /**
     * 查询记账账户列表
     * 
     * @param finAccount 记账账户
     * @return 记账账户
     */
    @Override
    public List<FinAccount> selectFinAccountList(FinAccount finAccount)
    {
        return finAccountMapper.selectFinAccountList(finAccount);
    }

    /**
     * 新增记账账户
     * 
     * @param finAccount 记账账户
     * @return 结果
     */
    @Override
    public int insertFinAccount(FinAccount finAccount)
    {
        finAccount.setCreateTime(DateUtils.getNowDate());
        finAccount.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        return finAccountMapper.insertFinAccount(finAccount);
    }

    /**
     * 修改记账账户
     * 
     * @param finAccount 记账账户
     * @return 结果
     */
    @Override
    public int updateFinAccount(FinAccount finAccount)
    {
        finAccount.setUpdateTime(DateUtils.getNowDate());
        finAccount.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        return finAccountMapper.updateFinAccount(finAccount);
    }

    /**
     * 批量删除记账账户
     * 
     * @param acctIds 需要删除的记账账户主键
     * @return 结果
     */
    @Override
    public int deleteFinAccountByAcctIds(Long[] acctIds)
    {
        return finAccountMapper.deleteFinAccountByAcctIds(acctIds);
    }

    /**
     * 删除记账账户信息
     * 
     * @param acctId 记账账户主键
     * @return 结果
     */
    @Override
    public int deleteFinAccountByAcctId(Long acctId)
    {
        return finAccountMapper.deleteFinAccountByAcctId(acctId);
    }
}
