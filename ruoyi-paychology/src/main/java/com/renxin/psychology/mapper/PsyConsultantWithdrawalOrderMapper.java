package com.renxin.psychology.mapper;

import com.renxin.psychology.domain.PsyConsultantWithdrawalOrder;

import java.util.List;

/**
 * 提现订单Mapper接口
 * 
 * @author renxin
 * @date 2024-06-20
 */
public interface PsyConsultantWithdrawalOrderMapper 
{
    /**
     * 查询提现订单
     * 
     * @param withdrawalNo 提现订单主键
     * @return 提现订单
     */
    public PsyConsultantWithdrawalOrder selectPsyConsultantWithdrawalOrderByWithdrawalNo(Long withdrawalNo);

    /**
     * 查询提现订单列表
     * 
     * @param psyConsultantWithdrawalOrder 提现订单
     * @return 提现订单集合
     */
    public List<PsyConsultantWithdrawalOrder> selectPsyConsultantWithdrawalOrderList(PsyConsultantWithdrawalOrder psyConsultantWithdrawalOrder);

    /**
     * 新增提现订单
     * 
     * @param psyConsultantWithdrawalOrder 提现订单
     * @return 结果
     */
    public int insertPsyConsultantWithdrawalOrder(PsyConsultantWithdrawalOrder psyConsultantWithdrawalOrder);

    /**
     * 修改提现订单
     * 
     * @param psyConsultantWithdrawalOrder 提现订单
     * @return 结果
     */
    public int updatePsyConsultantWithdrawalOrder(PsyConsultantWithdrawalOrder psyConsultantWithdrawalOrder);

    /**
     * 删除提现订单
     * 
     * @param withdrawalNo 提现订单主键
     * @return 结果
     */
    public int deletePsyConsultantWithdrawalOrderByWithdrawalNo(Long withdrawalNo);

    /**
     * 批量删除提现订单
     * 
     * @param withdrawalNos 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsyConsultantWithdrawalOrderByWithdrawalNos(Long[] withdrawalNos);
}
