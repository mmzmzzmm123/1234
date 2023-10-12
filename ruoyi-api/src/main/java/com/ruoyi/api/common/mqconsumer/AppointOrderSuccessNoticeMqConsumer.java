package com.ruoyi.api.common.mqconsumer;

import com.ruoyi.common.constant.WeChatServiceNotifyTemplateIdConstants;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.weixin.WxService;
import com.ruoyi.common.weixin.model.dto.WxServiceNotifyDto;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.user.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LAM
 * @date 2023/10/11 2:19
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_APPOINT_ORDER_SUCCESS_NOTICE, consumerGroup = MqConstants.GROUP_APPOINT_ORDER_SUCCESS_NOTICE, selectorExpression = "*")
public class AppointOrderSuccessNoticeMqConsumer implements RocketMQListener<Long> {

    private final OrderInfoMapper orderInfoMapper;
    private final UserInfoMapper userInfoMapper;
    private final WxService wxService;

    @Override
    public void onMessage(Long orderId) {
        log.info("mq消费-指定单下单完成通知用户与店员：开始，参数：{}", orderId);
        // 查询订单信息
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);

        // 发送店员订单通知
        String staffOpenId = userInfoMapper.selectOpenIdById(orderInfo.getStaffUserId());
        if (StringUtils.isNotBlank(staffOpenId)){
            WxServiceNotifyDto staffNotifyDto = new WxServiceNotifyDto();
            staffNotifyDto.setToUser(staffOpenId)
                    .setTemplateId(WeChatServiceNotifyTemplateIdConstants.STAFF_NEW_ORDER);
            Map<String, String> staffNotifyData = new HashMap<>();
            staffNotifyData.put("number2", orderInfo.getOrderNo());
            staffNotifyData.put("amount1", "￥"+orderInfo.getPayAmount());
            staffNotifyData.put("thing4", "您有一笔新的指定单，请火速前往处理！");
            staffNotifyDto.setData(staffNotifyData);
            try {
                wxService.sendTemplateMessage(staffNotifyDto);
            }catch (Exception e){
                log.warn("mq消费-指定单下单完成通知用户与店员：异常，发送店员新订单通知异常，异常消息：{}", e.getMessage());
            }
        }

        // 发送客户订单支付成功通知
        String userOpenId = userInfoMapper.selectOpenIdById(orderInfo.getCustomUserId());
        if (StringUtils.isNotBlank(userOpenId)){
            WxServiceNotifyDto userNotifyDto = new WxServiceNotifyDto();
            userNotifyDto.setToUser(userOpenId)
                    .setTemplateId(WeChatServiceNotifyTemplateIdConstants.USER_ORDER_SUCCESS);
            Map<String, String> staffNotifyData = new HashMap<>();
            staffNotifyData.put("number2", orderInfo.getOrderNo());
            staffNotifyData.put("amount1", "￥"+orderInfo.getPayAmount());
            staffNotifyData.put("thing4", "指定单下单成功，已通知店员火速接单");
            userNotifyDto.setData(staffNotifyData);
            try {
                wxService.sendTemplateMessage(userNotifyDto);
            }catch (Exception e){
                log.warn("mq消费-指定单下单完成通知用户与店员：异常，发送用户下单成功通知异常，异常消息：{}", e.getMessage());
            }
        }
        log.info("mq消费-指定单下单完成通知用户与店员：完成");
    }


}
