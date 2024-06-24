package com.renxin.psychology.service;

import com.renxin.psychology.domain.PsyConsultantReferralOrder;

import java.util.List;

/**
 * 转介订单Service接口
 * 
 * @author renxin
 * @date 2024-06-20
 */
public interface IPsyConsultantReferralOrderService 
{
    /**
     * 查询转介订单
     * 
     * @param referralId 转介订单主键
     * @return 转介订单
     */
    public PsyConsultantReferralOrder selectPsyConsultantReferralOrderByReferralId(Long referralId);

    /**
     * 查询转介订单列表
     * 
     * @param psyConsultantReferralOrder 转介订单
     * @return 转介订单集合
     */
    public List<PsyConsultantReferralOrder> selectPsyConsultantReferralOrderList(PsyConsultantReferralOrder psyConsultantReferralOrder);

    /**
     * 新增转介订单
     * 
     * @param psyConsultantReferralOrder 转介订单
     * @return 结果
     */
    public int insertPsyConsultantReferralOrder(PsyConsultantReferralOrder psyConsultantReferralOrder);

    /**
     * 修改转介订单
     * 
     * @param psyConsultantReferralOrder 转介订单
     * @return 结果
     */
    public int updatePsyConsultantReferralOrder(PsyConsultantReferralOrder psyConsultantReferralOrder);

    /**
     * 批量删除转介订单
     * 
     * @param referralIds 需要删除的转介订单主键集合
     * @return 结果
     */
    public int deletePsyConsultantReferralOrderByReferralIds(Long[] referralIds);

    /**
     * 删除转介订单信息
     * 
     * @param referralId 转介订单主键
     * @return 结果
     */
    public int deletePsyConsultantReferralOrderByReferralId(Long referralId);
}