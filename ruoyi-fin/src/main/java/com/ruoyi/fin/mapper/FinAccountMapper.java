package com.ruoyi.fin.mapper;

import java.util.List;
import com.ruoyi.fin.domain.FinAccount;

/**
 * 记账账户Mapper接口
 * 
 * @author ruoyi
 * @date 2023-09-15
 */
public interface FinAccountMapper 
{
    /**
     * 查询记账账户
     * 
     * @param acctId 记账账户主键
     * @return 记账账户
     */
    public FinAccount selectFinAccountByAcctId(Long acctId);

    /**
     * 查询记账账户列表
     * 
     * @param finAccount 记账账户
     * @return 记账账户集合
     */
    public List<FinAccount> selectFinAccountList(FinAccount finAccount);

    /**
     * 新增记账账户
     * 
     * @param finAccount 记账账户
     * @return 结果
     */
    public int insertFinAccount(FinAccount finAccount);

    /**
     * 修改记账账户
     * 
     * @param finAccount 记账账户
     * @return 结果
     */
    public int updateFinAccount(FinAccount finAccount);

    /**
     * 删除记账账户
     * 
     * @param acctId 记账账户主键
     * @return 结果
     */
    public int deleteFinAccountByAcctId(Long acctId);

    /**
     * 批量删除记账账户
     * 
     * @param acctIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinAccountByAcctIds(Long[] acctIds);
}
