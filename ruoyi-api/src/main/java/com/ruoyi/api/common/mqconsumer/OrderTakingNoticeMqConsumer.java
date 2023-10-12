package com.ruoyi.api.common.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.WeChatServiceNotifyTemplateIdConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.weixin.WxService;
import com.ruoyi.common.weixin.model.dto.WxServiceNotifyDto;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.staff.domain.StaffInfo;
import com.ruoyi.staff.mapper.StaffInfoMapper;
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
 * @date 2023/10/12 2:02
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_ORDER_TAKING_NOTICE, consumerGroup = MqConstants.GROUP_ORDER_TAKING_NOTICE, selectorExpression = "*")
public class OrderTakingNoticeMqConsumer implements RocketMQListener<Long> {

    private final UserInfoMapper userInfoMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final StaffInfoMapper staffInfoMapper;
    private final WxService wxService;

    @Override
    public void onMessage(Long orderId) {
        log.info("mq消费-订单接单通知：开始，参数：{}", orderId);

        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        if (ObjectUtil.isNull(orderInfo)){
            log.warn("mq消费-订单接单通知：失败，查询订单信息为空");
            throw new ServiceException("mq消费-订单接单通知：失败，查询订单信息为空");
        }

        StaffInfo staffInfo = staffInfoMapper.selectStaffInfoByUserId(orderInfo.getStaffUserId());
        if (ObjectUtil.isNull(staffInfo)){
            log.warn("mq消费-订单接单通知：失败，查询店员信息为空");
            throw new ServiceException("mq消费-订单接单通知：失败，查询店员信息为空");
        }

        String openId = userInfoMapper.selectOpenIdById(orderInfo.getCustomUserId());
        if (StringUtils.isBlank(openId)){
            log.warn("mq消费-订单接单通知：失败，查询用户openId为空");
            throw new ServiceException("mq消费-订单接单通知：失败，查询用户openId为空");
        }

        // 构建消息数据
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("number1", orderInfo.getOrderNo());
        dataMap.put("thing2", staffInfo.getNickName());
        dataMap.put("thing4", "您的订单已被店员接单，请留意消息哟！");

        WxServiceNotifyDto wxServiceNotifyDto = new WxServiceNotifyDto();
        wxServiceNotifyDto.setToUser(openId)
                .setTemplateId(WeChatServiceNotifyTemplateIdConstants.USER_RECEIVED_ORDER)
                .setData(dataMap);
        try {
            wxService.sendTemplateMessage(wxServiceNotifyDto);
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("mq消费-订单接单通知：完成");
    }
}
