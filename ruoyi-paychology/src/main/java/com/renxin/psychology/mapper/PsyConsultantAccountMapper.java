package com.renxin.psychology.mapper;

import com.renxin.psychology.domain.PsyConsultantAccount;

import java.util.List;

/**
 * 账户Mapper接口
 * 
 * @author renxin
 * @date 2024-06-20
 */
public interface PsyConsultantAccountMapper 
{

    public void initAccount();

    /**
     * 查询账户
     * 
     * @param consultantId 账户主键
     * @return 账户
     */
    public PsyConsultantAccount selectPsyConsultantAccountByConsultantId(Long consultantId);

    /**
     * 查询账户列表
     * 
     * @param psyConsultantAccount 账户
     * @return 账户集合
     */
    public List<PsyConsultantAccount> selectPsyConsultantAccountList(PsyConsultantAccount psyConsultantAccount);

    /**
     * 新增账户
     * 
     * @param psyConsultantAccount 账户
     * @return 结果
     */
    public int insertPsyConsultantAccount(PsyConsultantAccount psyConsultantAccount);

    /**
     * 修改账户
     * 
     * @param psyConsultantAccount 账户
     * @return 结果
     */
    public int updatePsyConsultantAccount(PsyConsultantAccount psyConsultantAccount);

    /**
     * 删除账户
     * 
     * @param consultantId 账户主键
     * @return 结果
     */
    public int deletePsyConsultantAccountByConsultantId(Long consultantId);

    /**
     * 批量删除账户
     * 
     * @param consultantIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsyConsultantAccountByConsultantIds(Long[] consultantIds);
}
