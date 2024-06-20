package com.renxin.psychology.service.impl;

import java.util.List;
import com.renxin.common.utils.DateUtils;
import com.renxin.psychology.domain.PsyConsultantWithdrawalOrder;
import com.renxin.psychology.mapper.PsyConsultantWithdrawalOrderMapper;
import com.renxin.psychology.service.IPsyConsultantWithdrawalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 提现订单Service业务层处理
 * 
 * @author renxin
 * @date 2024-06-20
 */
@Service
public class PsyConsultantWithdrawalOrderServiceImpl implements IPsyConsultantWithdrawalOrderService
{
    @Autowired
    private PsyConsultantWithdrawalOrderMapper psyConsultantWithdrawalOrderMapper;

    /**
     * 查询提现订单
     * 
     * @param withdrawalNo 提现订单主键
     * @return 提现订单
     */
    @Override
    public PsyConsultantWithdrawalOrder selectPsyConsultantWithdrawalOrderByWithdrawalNo(Long withdrawalNo)
    {
        return psyConsultantWithdrawalOrderMapper.selectPsyConsultantWithdrawalOrderByWithdrawalNo(withdrawalNo);
    }

    /**
     * 查询提现订单列表
     * 
     * @param psyConsultantWithdrawalOrder 提现订单
     * @return 提现订单
     */
    @Override
    public List<PsyConsultantWithdrawalOrder> selectPsyConsultantWithdrawalOrderList(PsyConsultantWithdrawalOrder psyConsultantWithdrawalOrder)
    {
        return psyConsultantWithdrawalOrderMapper.selectPsyConsultantWithdrawalOrderList(psyConsultantWithdrawalOrder);
    }

    /**
     * 新增提现订单
     * 
     * @param psyConsultantWithdrawalOrder 提现订单
     * @return 结果
     */
    @Override
    public int insertPsyConsultantWithdrawalOrder(PsyConsultantWithdrawalOrder psyConsultantWithdrawalOrder)
    {
        psyConsultantWithdrawalOrder.setCreateTime(DateUtils.getNowDate());
        return psyConsultantWithdrawalOrderMapper.insertPsyConsultantWithdrawalOrder(psyConsultantWithdrawalOrder);
    }

    /**
     * 修改提现订单
     * 
     * @param psyConsultantWithdrawalOrder 提现订单
     * @return 结果
     */
    @Override
    public int updatePsyConsultantWithdrawalOrder(PsyConsultantWithdrawalOrder psyConsultantWithdrawalOrder)
    {
        psyConsultantWithdrawalOrder.setUpdateTime(DateUtils.getNowDate());
        return psyConsultantWithdrawalOrderMapper.updatePsyConsultantWithdrawalOrder(psyConsultantWithdrawalOrder);
    }

    /**
     * 批量删除提现订单
     * 
     * @param withdrawalNos 需要删除的提现订单主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantWithdrawalOrderByWithdrawalNos(Long[] withdrawalNos)
    {
        return psyConsultantWithdrawalOrderMapper.deletePsyConsultantWithdrawalOrderByWithdrawalNos(withdrawalNos);
    }

    /**
     * 删除提现订单信息
     * 
     * @param withdrawalNo 提现订单主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantWithdrawalOrderByWithdrawalNo(Long withdrawalNo)
    {
        return psyConsultantWithdrawalOrderMapper.deletePsyConsultantWithdrawalOrderByWithdrawalNo(withdrawalNo);
    }
}
