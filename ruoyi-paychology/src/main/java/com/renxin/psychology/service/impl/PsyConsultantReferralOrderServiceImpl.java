package com.renxin.psychology.service.impl;

import java.util.List;
import com.renxin.common.utils.DateUtils;
import com.renxin.psychology.domain.PsyConsultantReferralOrder;
import com.renxin.psychology.mapper.PsyConsultantReferralOrderMapper;
import com.renxin.psychology.service.IPsyConsultantReferralOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 转介订单Service业务层处理
 * 
 * @author renxin
 * @date 2024-06-20
 */
@Service
public class PsyConsultantReferralOrderServiceImpl implements IPsyConsultantReferralOrderService
{
    @Autowired
    private PsyConsultantReferralOrderMapper psyConsultantReferralOrderMapper;

    /**
     * 查询转介订单
     * 
     * @param referralId 转介订单主键
     * @return 转介订单
     */
    @Override
    public PsyConsultantReferralOrder selectPsyConsultantReferralOrderByReferralId(Long referralId)
    {
        return psyConsultantReferralOrderMapper.selectPsyConsultantReferralOrderByReferralId(referralId);
    }

    /**
     * 查询转介订单列表
     * 
     * @param psyConsultantReferralOrder 转介订单
     * @return 转介订单
     */
    @Override
    public List<PsyConsultantReferralOrder> selectPsyConsultantReferralOrderList(PsyConsultantReferralOrder psyConsultantReferralOrder)
    {
        return psyConsultantReferralOrderMapper.selectPsyConsultantReferralOrderList(psyConsultantReferralOrder);
    }

    /**
     * 新增转介订单
     * 
     * @param psyConsultantReferralOrder 转介订单
     * @return 结果
     */
    @Override
    public int insertPsyConsultantReferralOrder(PsyConsultantReferralOrder psyConsultantReferralOrder)
    {
        psyConsultantReferralOrder.setCreateTime(DateUtils.getNowDate());
        return psyConsultantReferralOrderMapper.insertPsyConsultantReferralOrder(psyConsultantReferralOrder);
    }

    /**
     * 修改转介订单
     * 
     * @param psyConsultantReferralOrder 转介订单
     * @return 结果
     */
    @Override
    public int updatePsyConsultantReferralOrder(PsyConsultantReferralOrder psyConsultantReferralOrder)
    {
        psyConsultantReferralOrder.setUpdateTime(DateUtils.getNowDate());
        return psyConsultantReferralOrderMapper.updatePsyConsultantReferralOrder(psyConsultantReferralOrder);
    }

    /**
     * 批量删除转介订单
     * 
     * @param referralIds 需要删除的转介订单主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantReferralOrderByReferralIds(Long[] referralIds)
    {
        return psyConsultantReferralOrderMapper.deletePsyConsultantReferralOrderByReferralIds(referralIds);
    }

    /**
     * 删除转介订单信息
     * 
     * @param referralId 转介订单主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantReferralOrderByReferralId(Long referralId)
    {
        return psyConsultantReferralOrderMapper.deletePsyConsultantReferralOrderByReferralId(referralId);
    }
}
