package com.ruoyi.api.payment.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.payment.model.vo.ApiWxPayCallbackVo;
import com.ruoyi.api.payment.mqconsumer.service.PaymentMqConsumerService;
import com.ruoyi.api.user.common.UserLevelCommonService;
import com.ruoyi.common.enums.OrderTypeEnums;
import com.ruoyi.common.enums.PayStateEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.payment.domain.PaymentOrder;
import com.ruoyi.payment.mapper.PaymentOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author LAM
 * @date 2023/10/6 1:21
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_PAY_SUCCESS_CALLBACK, consumerGroup = MqConstants.GROUP_PAY_SUCCESS_CALLBACK)
public class PaySuccessMqConsumer implements RocketMQListener<ApiWxPayCallbackVo> {

    private final PaymentMqConsumerService service;
    private final PaymentOrderMapper paymentOrderMapper;
    private final UserLevelCommonService userLevelCommonService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(ApiWxPayCallbackVo apiWxPayCallbackVo) {
        log.info("mq消费-支付回调：开始，数据：{}", apiWxPayCallbackVo);
        PaymentOrder paymentOrder = paymentOrderMapper.selectByOutTradeNo(apiWxPayCallbackVo.getOutTradeNo());
        if (ObjectUtil.isNull(paymentOrder)){
            log.warn("支mq消费-付回调：失败，无法找到对应的支付订单数据");
            throw new ServiceException("支付回调：失败，无法找到对应的支付订单数据");
        }
        Date now = DateUtils.getNowDate();
        // 修改支付订单状态信息
        PaymentOrder updatePo = new PaymentOrder();
        updatePo.setId(paymentOrder.getId())
                .setPayState(PayStateEnums.SUCCESS.getCode())
                .setTransactionId(apiWxPayCallbackVo.getTransactionId())
                .setChannelNotifyData(apiWxPayCallbackVo.getCallbackData())
                .setSuccessTime(now);
        paymentOrderMapper.updatePaymentOrder(updatePo);
        // 开始进行业务处理，根据支付订单中的订单类型进行分发业务处理

        // 充值订单
        if (OrderTypeEnums.RECHARGE.getCode().equals(paymentOrder.getOrderType())){
            // 计算等级变化
            userLevelCommonService.levelCount(paymentOrder.getUserId(), paymentOrder.getAmount());
            // 开启对应业务
            service.rechargeOrderCallback(paymentOrder.getOrderId());
        }
        // 礼物订单
        if (OrderTypeEnums.GIFT.getCode().equals(paymentOrder.getOrderType())){
            // 计算等级变化
            userLevelCommonService.levelCount(paymentOrder.getUserId(), paymentOrder.getAmount());
            // 开启对应业务
            service.giftOrderCallback(paymentOrder.getOrderId());
        }
        // 打赏订单
        if (OrderTypeEnums.REWARD.getCode().equals(paymentOrder.getOrderType())){
            // 计算等级变化
            userLevelCommonService.levelCount(paymentOrder.getUserId(), paymentOrder.getAmount());
            // 开启对应业务
            service.rewardOrderCallback(paymentOrder.getOrderId());
        }
        // 指定单
        if (OrderTypeEnums.APPOINT.getCode().equals(paymentOrder.getOrderType())){
            // 开启对应业务
            service.appointOrderCallback(paymentOrder.getOrderId());
        }
        // 随机单
        if (OrderTypeEnums.RANDOM.getCode().equals(paymentOrder.getOrderType())){
            // 开启对应业务
            service.randomOrderCallback(paymentOrder.getOrderId());
        }

        log.info("mq消费-支付回调：完成");
    }

}
