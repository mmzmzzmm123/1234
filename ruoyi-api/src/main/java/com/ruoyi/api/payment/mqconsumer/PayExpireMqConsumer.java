package com.ruoyi.api.payment.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.OrderStateEnums;
import com.ruoyi.common.enums.OrderTypeEnums;
import com.ruoyi.common.enums.PayStateEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OrderDetails;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderDetailsMapper;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.payment.domain.PaymentOrder;
import com.ruoyi.payment.mapper.PaymentOrderMapper;
import com.ruoyi.user.domain.UserRechargeRecord;
import com.ruoyi.user.mapper.UserRechargeRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author LAM
 * @date 2023/10/5 23:35
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_ORDER_EXPIRE, consumerGroup = MqConstants.GROUP_ORDER_EXPIRE, selectorExpression = "*")
public class PayExpireMqConsumer implements RocketMQListener<Long> {

    private final PaymentOrderMapper paymentOrderMapper;
    private final UserRechargeRecordMapper userRechargeRecordMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final OrderDetailsMapper orderDetailsMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Long payOrderId) {
        log.info("mq消费-支付订单过期处理：开始，参数：{}", payOrderId);
        PaymentOrder paymentOrder = paymentOrderMapper.selectPaymentOrderById(payOrderId);
        if (ObjectUtil.isNull(paymentOrder)) {
            throw new ServiceException("mq消费-支付订单过期处理：失败，无法查询到对应的支付订单数据");
        }
        // 判断是否已经支付完毕
        if (PayStateEnums.SUCCESS.getCode().equals(paymentOrder.getPayState())) {
            log.info("mq消费-支付订单过期处理：完成，订单已支付");
            return;
        }
        // 修改支付订单的支付状态
        PaymentOrder updatePo = new PaymentOrder();
        updatePo.setId(payOrderId)
                .setPayState(PayStateEnums.CANCEL.getCode());
        paymentOrderMapper.updatePaymentOrder(updatePo);
        log.info("mq消费-支付订单过期处理：描述，修改支付订单状态成功");
        // 根据支付订单记录的订单类型进行各业务处理
        // 充值
        if (OrderTypeEnums.RECHARGE.getCode().equals(paymentOrder.getOrderType())) {
            UserRechargeRecord updateUrr = new UserRechargeRecord();
            updateUrr.setId(paymentOrder.getOrderId())
                    .setPayState(PayStateEnums.CANCEL.getCode());
            userRechargeRecordMapper.updateUserRechargeRecord(updateUrr);
            log.info("mq消费-支付订单过期处理：描述，修改用户充值记录支付状态成功");
        } else {
            Date now = DateUtils.getNowDate();
            // 更新订单信息
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setId(paymentOrder.getOrderId())
                    .setOrderState(OrderStateEnums.CANCEL.getCode())
                    .setCanceller(Constants.SYS_NAME)
                    .setOrderCancelTime(now)
                    .setCancelRemark("系统自动取消")
                    .setUpdateTime(now);
            orderInfoMapper.updateOrderInfo(orderInfo);
            // 更新订单详情数据
            orderDetailsMapper.updateStateByOrderId(paymentOrder.getOrderId(), OrderStateEnums.CANCEL.getCode());
            log.info("mq消费-支付订单过期处理：描述，修改订单与详情记录状态成功");
        }
        log.info("mq消费-支付订单过期处理：完成");
    }

}
