package com.ruoyi.payment.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.payment.mapper.PaymentOrderMapper;
import com.ruoyi.payment.domain.PaymentOrder;
import com.ruoyi.payment.service.IPaymentOrderService;

/**
 * 支付订单Service业务层处理
 *
 * @author Lam
 * @date 2023-09-15
 */
@Service
@RequiredArgsConstructor
public class PaymentOrderServiceImpl implements IPaymentOrderService {

    private final PaymentOrderMapper paymentOrderMapper;

    /**
     * 查询支付订单
     *
     * @param id 支付订单主键
     * @return 支付订单
     */
    @Override
    public PaymentOrder selectPaymentOrderById(Long id) {
        return paymentOrderMapper.selectPaymentOrderById(id);
    }

    /**
     * 查询支付订单列表
     *
     * @param paymentOrder 支付订单
     * @return 支付订单
     */
    @Override
    public List<PaymentOrder> selectPaymentOrderList(PaymentOrder paymentOrder) {
        return paymentOrderMapper.selectPaymentOrderList(paymentOrder);
    }

    /**
     * 新增支付订单
     *
     * @param paymentOrder 支付订单
     * @return 结果
     */
    @Override
    public int insertPaymentOrder(PaymentOrder paymentOrder) {
        paymentOrder.setCreateTime(DateUtils.getNowDate());
        return paymentOrderMapper.insertPaymentOrder(paymentOrder);
    }

    /**
     * 修改支付订单
     *
     * @param paymentOrder 支付订单
     * @return 结果
     */
    @Override
    public int updatePaymentOrder(PaymentOrder paymentOrder) {
        return paymentOrderMapper.updatePaymentOrder(paymentOrder);
    }

    /**
     * 批量删除支付订单
     *
     * @param ids 需要删除的支付订单主键
     * @return 结果
     */
    @Override
    public int deletePaymentOrderByIds(Long[] ids) {
        return paymentOrderMapper.deletePaymentOrderByIds(ids);
    }

    /**
     * 删除支付订单信息
     *
     * @param id 支付订单主键
     * @return 结果
     */
    @Override
    public int deletePaymentOrderById(Long id) {
        return paymentOrderMapper.deletePaymentOrderById(id);
    }
}
