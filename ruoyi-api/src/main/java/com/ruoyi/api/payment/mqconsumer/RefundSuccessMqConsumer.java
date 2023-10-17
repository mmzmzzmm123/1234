package com.ruoyi.api.payment.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.payment.model.vo.ApiWxRefundCallbackVo;
import com.ruoyi.common.enums.OrderStateEnums;
import com.ruoyi.common.enums.PayStateEnums;
import com.ruoyi.common.enums.RefundStateEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.RocketMqService;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OrderDetails;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderDetailsMapper;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.payment.domain.PaymentOrder;
import com.ruoyi.payment.domain.PaymentRefund;
import com.ruoyi.payment.mapper.PaymentOrderMapper;
import com.ruoyi.payment.mapper.PaymentRefundMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author LAM
 * @date 2023/5/11 15:04
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_REFUND_SUCCESS_CALLBACK, consumerGroup = MqConstants.GROUP_REFUND_SUCCESS_CALLBACK)
public class RefundSuccessMqConsumer implements RocketMQListener<ApiWxRefundCallbackVo> {

    private final OrderInfoMapper orderInfoMapper;
    private final OrderDetailsMapper orderDetailsMapper;
    private final PaymentOrderMapper paymentOrderMapper;
    private final PaymentRefundMapper paymentRefundMapper;
    private final RocketMqService rocketMqService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(ApiWxRefundCallbackVo wxRefundCallbackVo) {
        log.info("mq消费-微信退款成功回调：开始，参数：{}", wxRefundCallbackVo);
        PaymentRefund paymentRefund = paymentRefundMapper.selectByRefundNo(wxRefundCallbackVo.getOutTradeNo());
        if (ObjectUtil.isNull(paymentRefund)){
            log.warn("mq消费-微信退款成功回调：失败，无法找到对应的退款单");
            throw new ServiceException("mq消费-微信退款成功回调：失败，无法找到对应的退款单");
        }
        Date now = DateUtils.getNowDate();
        // 更新订单状态
        OrderInfo updateOi = new OrderInfo();
        updateOi.setId(paymentRefund.getOrderId())
                .setOrderState(OrderStateEnums.REFUNDED.getCode())
                .setUpdateTime(now);
        orderInfoMapper.updateOrderInfo(updateOi);
        // 更新订单详情状态
        orderDetailsMapper.updateStateByOrderId(updateOi.getId(), OrderStateEnums.REFUNDED.getCode());
        // 更新支付单状态
        PaymentOrder updatePo = new PaymentOrder();
        updatePo.setOrderId(updateOi.getId())
                .setPayState(PayStateEnums.REFUND.getCode())
                .setUpdateTime(now);
        paymentOrderMapper.updateByOrderId(updatePo);
        //更新退款单状态
        PaymentRefund updatePr = new PaymentRefund();
        updatePr.setId(paymentRefund.getId())
                .setStart(RefundStateEnums.SUCCESS.getCode());
        paymentRefundMapper.updatePaymentRefund(updatePr);
        // 发送退款成功通知
        rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_CANCEL_NOTICE, paymentRefund.getOrderId());
        log.info("mq消费-微信退款成功回调：完成");
    }
}
