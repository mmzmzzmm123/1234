package com.ruoyi.payment.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.payment.mapper.PaymentRefundMapper;
import com.ruoyi.payment.domain.PaymentRefund;
import com.ruoyi.payment.service.IPaymentRefundService;

/**
 * 支付单退款Service业务层处理
 *
 * @author Lam
 * @date 2023-09-15
 */
@Service
@RequiredArgsConstructor
public class PaymentRefundServiceImpl implements IPaymentRefundService {

    private final PaymentRefundMapper paymentRefundMapper;

    /**
     * 查询支付单退款
     *
     * @param id 支付单退款主键
     * @return 支付单退款
     */
    @Override
    public PaymentRefund selectPaymentRefundById(Long id) {
        return paymentRefundMapper.selectPaymentRefundById(id);
    }

    /**
     * 查询支付单退款列表
     *
     * @param paymentRefund 支付单退款
     * @return 支付单退款
     */
    @Override
    public List<PaymentRefund> selectPaymentRefundList(PaymentRefund paymentRefund) {
        return paymentRefundMapper.selectPaymentRefundList(paymentRefund);
    }

    /**
     * 新增支付单退款
     *
     * @param paymentRefund 支付单退款
     * @return 结果
     */
    @Override
    public int insertPaymentRefund(PaymentRefund paymentRefund) {
        paymentRefund.setCreateTime(DateUtils.getNowDate());
        return paymentRefundMapper.insertPaymentRefund(paymentRefund);
    }

    /**
     * 修改支付单退款
     *
     * @param paymentRefund 支付单退款
     * @return 结果
     */
    @Override
    public int updatePaymentRefund(PaymentRefund paymentRefund) {
        return paymentRefundMapper.updatePaymentRefund(paymentRefund);
    }

    /**
     * 批量删除支付单退款
     *
     * @param ids 需要删除的支付单退款主键
     * @return 结果
     */
    @Override
    public int deletePaymentRefundByIds(Long[] ids) {
        return paymentRefundMapper.deletePaymentRefundByIds(ids);
    }

    /**
     * 删除支付单退款信息
     *
     * @param id 支付单退款主键
     * @return 结果
     */
    @Override
    public int deletePaymentRefundById(Long id) {
        return paymentRefundMapper.deletePaymentRefundById(id);
    }
}
