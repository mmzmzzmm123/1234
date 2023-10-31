package com.ruoyi.api.order.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.order.service.ApiOrderService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.SysTipsConstants;
import com.ruoyi.common.enums.*;
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
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/11 2:20
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_ORDER_AUTO_CANCEL, consumerGroup = MqConstants.GROUP_ORDER_AUTO_CANCEL, selectorExpression = "*")
public class OrderAutoCancelMqConsumer implements RocketMQListener<Long> {

    private final OrderInfoMapper orderInfoMapper;
    private final OrderDetailsMapper orderDetailsMapper;
    private final PaymentOrderMapper paymentOrderMapper;
    private final PaymentRefundMapper paymentRefundMapper;
    private final ApiOrderService apiOrderService;
    private final RocketMqService rocketMqService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Long orderId) {
        log.info("mq消费-订单未接单自动取消：开始，参数：{}", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        if (ObjectUtil.isNull(orderInfo)){
            log.warn("mq消费-订单未接单自动取消：失败，订单信息为空");
            throw new ServiceException("mq消费-订单未接单自动取消：失败，订单信息为空");
        }
        // 判断订单状态是否正常
        if (OrderStateEnums.CANCEL.getCode().equals(orderInfo.getOrderState())){
            log.warn("mq消费-订单未接单自动取消：描述，订单已取消");
            return;
        }
        if (!OrderStateEnums.PAID.getCode().equals(orderInfo.getOrderState())){
            log.warn("mq消费-订单未接单自动取消：描述，订单状态不为已支付/待接单状态，直接跳过");
            return;
        }
        OrderDetails selectOrderDetails = new OrderDetails();
        selectOrderDetails.setOrderId(orderInfo.getId());
        List<OrderDetails> orderDetailsList = orderDetailsMapper.selectOrderDetailsList(selectOrderDetails);
        if (ObjectUtil.isNull(orderInfo) || ObjectUtil.isEmpty(orderDetailsList)) {
            log.warn("mq消费-订单未接单自动取消：失败，无法找到订单相关数据");
            throw new ServiceException("mq消费-订单未接单自动取消：失败，无法找到订单相关数据");
        }
        OrderDetails orderDetails = orderDetailsList.stream().findFirst().orElse(null);

        Date now = DateUtils.getNowDate();
        OrderInfo updateOi = new OrderInfo();
        updateOi.setId(orderId)
                .setOrderState(OrderStateEnums.REFUNDING.getCode())
                .setCanceller(Constants.SYS_NAME)
                .setOrderCancelTime(now)
                .setCancelRemark(SysTipsConstants.ORDER_OVER_TIME_CANCEL_TIPS)
                .setUpdateTime(now);
        orderInfo.setCancelRemark(updateOi.getCancelRemark());
        // 微信支付业务
        if (PayWayEnums.WEI_XIN_PAY.getCode().equals(orderInfo.getPayWay())){
            orderInfoMapper.updateOrderInfo(updateOi);
            // 构建退款申请单
            PaymentRefund paymentRefund = apiOrderService.buildPaymentRefund(orderInfo,orderDetails);
            paymentRefundMapper.insertPaymentRefund(paymentRefund);
            // 请求微信发起退款
            apiOrderService.wxRefund(orderInfo, paymentRefund);
        }

        // 余额退款业务
        if (PayWayEnums.AMOUNT_PAY.getCode().equals(orderInfo.getPayWay())) {
            updateOi.setOrderState(OrderStateEnums.CANCEL.getCode());
            orderInfoMapper.updateOrderInfo(updateOi);
            // 更新订单详情状态
            orderDetailsMapper.updateStateByOrderId(orderId, OrderStateEnums.REFUNDED.getCode());
            // 更新支付单状态
            PaymentOrder updatePo = new PaymentOrder();
            updatePo.setOrderId(orderId)
                    .setPayState(PayStateEnums.REFUND.getCode());
            paymentOrderMapper.updateByOrderId(updatePo);
            // 退款单号生成
            PaymentRefund paymentRefund = apiOrderService.buildPaymentRefund(orderInfo, orderDetails);
            paymentRefundMapper.insertPaymentRefund(paymentRefund);
            // 用户订单金额回退到余额中
            apiOrderService.balanceRefund(orderInfo);
            // 发送取消通知
            rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_CANCEL_NOTICE, orderInfo.getId());
            log.info("mq消费-订单未接单自动取消：描述，支付类型：余额，业务处理完毕");
        }
        log.info("mq消费-订单未接单自动取消：完成");
    }

}
