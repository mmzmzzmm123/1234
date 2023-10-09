package com.ruoyi.api.payment.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.enums.OrderTypeEnums;
import com.ruoyi.common.enums.PayStateEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.constants.MqConstants;
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

/**
 * @author LAM
 * @date 2023/10/5 23:35
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_RECHARGE, consumerGroup = MqConstants.GROUP_RECHARGE, selectorExpression = "*")
public class PayExpireMqConsumer implements RocketMQListener<Long> {

    private final PaymentOrderMapper paymentOrderMapper;
    private final UserRechargeRecordMapper userRechargeRecordMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Long payOrderId) {
        log.info("mq消费-支付订单过期处理：开始，参数：{}", payOrderId);
        PaymentOrder paymentOrder = paymentOrderMapper.selectPaymentOrderById(payOrderId);
        if (ObjectUtil.isNull(paymentOrder)){
            throw new ServiceException("mq消费-支付订单过期处理：失败，无法查询到对应的支付订单数据");
        }
        // 判断是否已经支付完毕
        if (PayStateEnums.SUCCESS.getCode().equals(paymentOrder.getPayState())){
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
        if (OrderTypeEnums.RECHARGE.getCode().equals(paymentOrder.getOrderType())){
            UserRechargeRecord updateUrr = new UserRechargeRecord();
            updateUrr.setId(paymentOrder.getOrderId())
                    .setPayState(PayStateEnums.CANCEL.getCode());
            userRechargeRecordMapper.updateUserRechargeRecord(updateUrr);
            log.info("mq消费-支付订单过期处理：描述，修改用户充值记录支付状态成功");
        }
        // 接下来的是其他订单的处理


        log.info("mq消费-支付订单过期处理：完成");
    }

}
