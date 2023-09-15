package com.ruoyi.payment.service;

import java.util.List;
import com.ruoyi.payment.domain.PaymentRefund;

/**
 * 支付单退款Service接口
 *
 * @author Lam
 * @date 2023-09-15
 */
public interface IPaymentRefundService {

    /**
     * 查询支付单退款
     *
     * @param id 支付单退款主键
     * @return 支付单退款
     */
    public PaymentRefund selectPaymentRefundById(Long id);

    /**
     * 查询支付单退款列表
     *
     * @param paymentRefund 支付单退款
     * @return 支付单退款集合
     */
    public List<PaymentRefund> selectPaymentRefundList(PaymentRefund paymentRefund);

    /**
     * 新增支付单退款
     *
     * @param paymentRefund 支付单退款
     * @return 结果
     */
    public int insertPaymentRefund(PaymentRefund paymentRefund);

    /**
     * 修改支付单退款
     *
     * @param paymentRefund 支付单退款
     * @return 结果
     */
    public int updatePaymentRefund(PaymentRefund paymentRefund);

    /**
     * 批量删除支付单退款
     *
     * @param ids 需要删除的支付单退款主键集合
     * @return 结果
     */
    public int deletePaymentRefundByIds(Long[] ids);

    /**
     * 删除支付单退款信息
     *
     * @param id 支付单退款主键
     * @return 结果
     */
    public int deletePaymentRefundById(Long id);
}
