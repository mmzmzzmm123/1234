package com.ruoyi.api.order.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.enums.MqDelayLevelEnums;
import com.ruoyi.common.enums.OrderStateEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.RocketMqService;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author LAM
 * @date 2023/10/17 15:51
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_ORDER_AUTO_SUCCESS, consumerGroup = MqConstants.GROUP_ORDER_AUTO_SUCCESS, selectorExpression = "*")
public class OrderAutoSuccessMqConsumer implements RocketMQListener<Long> {

    private final OrderInfoMapper orderInfoMapper;
    private final RocketMqService rocketMqService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Long orderId) {
        log.info("mq消费-订单自动完成：开始，参数：{}", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        if (ObjectUtil.isNull(orderInfo)){
            log.warn("mq消费-订单自动完成：失败，订单信息为空");
            throw new ServiceException("mq消费-订单自动完成：失败，订单信息为空");
        }
        // 判断订单状态是否服务中
        if (!OrderStateEnums.SERVICE_ING.getCode().equals(orderInfo.getOrderState())){
            log.warn("mq消费-订单自动完成：完成，订单状态不为服务中，直接省略，订单信息：{}", orderInfo);
            return;
        }
        // 判断订单自动完成时间是否小于当前时间
        Date autoFinishTime = orderInfo.getAutoFinshTime();
        if (autoFinishTime.getTime() > DateUtils.getNowDate().getTime()){
            log.info("mq消费-订单自动完成：描述，当前的订单达到自动完成时间");
            rocketMqService.delayedSend(MqConstants.TOPIC_ORDER_AUTO_SUCCESS, orderId, MqDelayLevelEnums.level_17);
            return;
        }
        // 此处可以使订单进入下一个状态
        OrderInfo updateOi = new OrderInfo();
        updateOi.setId(orderId)
                .setOrderState(OrderStateEnums.WAIT_COMMENT.getCode())
                .setUpdateTime(DateUtils.getNowDate());
        orderInfoMapper.updateOrderInfo(updateOi);
        // 发送自动评论mq过期时间，默认两小时后自动评论
        rocketMqService.delayedSend(MqConstants.TOPIC_ORDER_AUTO_COMMENT, orderId, MqDelayLevelEnums.level_18);
        log.info("mq消费-订单自动完成：完成");
    }
}
