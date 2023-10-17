package com.ruoyi.api.order.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.enums.OrderStateEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.RocketMqService;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OrderComment;
import com.ruoyi.order.domain.OrderDetails;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderCommentMapper;
import com.ruoyi.order.mapper.OrderDetailsMapper;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.user.domain.UserInfo;
import com.ruoyi.user.mapper.UserInfoMapper;
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
 * @date 2023/10/17 17:54
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_ORDER_AUTO_COMMENT, consumerGroup = MqConstants.GROUP_ORDER_AUTO_COMMENT, selectorExpression = "*")
public class OrderAutoCommentMqConsumer implements RocketMQListener<Long> {

    private final UserInfoMapper userInfoMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final OrderDetailsMapper orderDetailsMapper;
    private final OrderCommentMapper orderCommentMapper;
    private final RocketMqService rocketMqService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Long orderId) {
        log.info("mq消费-订单自动评论：开始，参数：{}", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        if (ObjectUtil.isNull(orderInfo)) {
            log.warn("mq消费-订单自动评论：失败，订单信息为空");
            throw new ServiceException("mq消费-订单自动评论：失败，订单信息为空");
        }
        // 查询订单详情信息
        List<OrderDetails> orderDetailsList = orderDetailsMapper.selectByOrderId(orderInfo.getId());
        if (ObjectUtil.isEmpty(orderDetailsList)) {
            log.warn("mq消费-订单自动评论：失败，查询订单详情失败");
            throw new ServiceException("mq消费-订单自动评论：失败，查询订单详情失败");
        }
        OrderDetails orderDetails = orderDetailsList.get(0);
        // 查询用户信息
        UserInfo userInfo = userInfoMapper.selectUserInfoById(orderInfo.getCustomUserId());
        if (ObjectUtil.isNull(userInfo)) {
            log.warn("mq消费-订单自动评论：失败，查询用户信息失败");
            throw new ServiceException("mq消费-订单自动评论：失败，查询用户信息失败");
        }
        Date now = DateUtils.getNowDate();
        // 开始构建订单
        OrderInfo updateOi = new OrderInfo();
        updateOi.setId(orderInfo.getId())
                .setOrderState(OrderStateEnums.FINISH.getCode())
                .setOrderFinshTime(now)
                .setUpdateTime(now);
        orderInfoMapper.updateOrderInfo(updateOi);
        // 评论信息
        OrderComment orderComment = new OrderComment();
        orderComment.setOrderId(orderInfo.getId())
                .setStaffUserId(orderInfo.getStaffUserId())
                .setUserAvatar(userInfo.getAvatarUrl())
                .setUserId(orderInfo.getCustomUserId())
                .setUserName(userInfo.getNickName())
                .setComment("默认好评")
                .setServiceContent(orderDetails.getDetailsTitle())
                .setCreateTime(now);
        orderCommentMapper.insertOrderComment(orderComment);
        // 发送订单完成通知
        rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_FINISH_NOTICE, orderInfo.getId());
        // 订单佣金结算
        rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_COMMISSION_SETTLEMENT, orderInfo.getId());
        log.info("mq消费-订单自动评论：完成");
    }
}
