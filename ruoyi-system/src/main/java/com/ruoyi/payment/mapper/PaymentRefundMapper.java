package com.ruoyi.payment.mapper;

import java.util.List;
import com.ruoyi.payment.domain.PaymentRefund;

/**
 * 支付单退款Mapper接口
 *
 * @author Lam
 * @date 2023-09-15
 */
public interface PaymentRefundMapper {

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
     * 删除支付单退款
     *
     * @param id 支付单退款主键
     * @return 结果
     */
    public int deletePaymentRefundById(Long id);

    /**
     * 批量删除支付单退款
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePaymentRefundByIds(Long[] ids);
}
