package com.ruoyi.api.staff.mqconsumer;

import com.ruoyi.common.rocketmq.RocketMqService;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author LAM
 * @date 2023/10/10 21:52
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_REWARD_SUCCESS, consumerGroup = MqConstants.GROUP_REWARD_SUCCESS, selectorExpression = "*")
public class RewardSuccessMqConsumer implements RocketMQListener<Long> {

    private final RocketMqService rocketMqService;

    @Override
    public void onMessage(Long orderId) {
        log.info("mq消费-打赏成功业务处理：开始，参数：{}", orderId);
        //  发送模板消息通知用户和员工
        rocketMqService.asyncSend(MqConstants.TOPIC_REWARD_SUCCESS_NOTICE, orderId);
        // 订单佣金结算
        rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_COMMISSION_SETTLEMENT, orderId);
        log.info("mq消费-打赏成功业务处理：完成");
    }
}
