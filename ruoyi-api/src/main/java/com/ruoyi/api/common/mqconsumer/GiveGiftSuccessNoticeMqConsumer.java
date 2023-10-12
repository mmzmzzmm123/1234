package com.ruoyi.api.common.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.WeChatServiceNotifyTemplateIdConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.weixin.WxService;
import com.ruoyi.common.weixin.model.dto.WxServiceNotifyDto;
import com.ruoyi.order.domain.OrderDetails;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderDetailsMapper;
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
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author LAM
 * @date 2023/10/10 17:27
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_GIVE_GIFT_SUCCESS_NOTICE, consumerGroup = MqConstants.GROUP_GIVE_GIFT_SUCCESS_NOTICE, selectorExpression = "*")
public class GiveGiftSuccessNoticeMqConsumer implements RocketMQListener<Long> {

    private final UserInfoMapper userInfoMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final OrderDetailsMapper orderDetailsMapper;
    private final StaffInfoMapper staffInfoMapper;
    private final WxService wxService;

    @Override
    public void onMessage(Long orderId) {
        log.info("礼物赠送成功通知：开始，参数：{}", orderId);
        // 查询订单信息
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        // 查询订单详情数据
        OrderDetails selectOrderDetails = new OrderDetails();
        selectOrderDetails.setOrderId(orderId);
        List<OrderDetails> orderDetailsList = orderDetailsMapper.selectOrderDetailsList(selectOrderDetails);
        if (ObjectUtil.isNull(orderInfo) || ObjectUtil.isEmpty(orderDetailsList)) {
            log.warn("礼物赠送成功通知：失败，无法找到订单相关数据");
            throw new ServiceException("礼物赠送成功通知：失败，无法找到订单相关数据");
        }
        OrderDetails orderDetails = orderDetailsList.stream().findFirst().orElse(null);
        // 查询店员信息
        StaffInfo staffInfo = staffInfoMapper.selectStaffInfoByUserId(orderInfo.getStaffUserId());
        if (ObjectUtil.isNull(staffInfo)){
            log.warn("礼物赠送成功通知：失败，无法查询对应店员数据");
            throw new ServiceException("礼物赠送成功通知：失败，无法查询对应店员数据");
        }
        // 构建消息数据
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("thing2", staffInfo.getNickName());
        dataMap.put("thing1", StringUtils.split(orderDetails.getDetailsTitle(),"-")[1]);
        dataMap.put("number3", orderDetails.getNum()+"");
        dataMap.put("thing5", "店员将在看到后尽快给您道谢，祝生活愉快");

        // 客户openid
        String openId = userInfoMapper.selectOpenIdById(orderInfo.getCustomUserId());
        if (StringUtils.isBlank(openId)){
            log.warn("礼物赠送成功通知：失败，无法查询对应客户openid");
            throw new ServiceException("礼物赠送成功通知：失败，无法查询对应客户openid");
        }
        WxServiceNotifyDto wxServiceNotifyDto = new WxServiceNotifyDto();
        wxServiceNotifyDto.setToUser(openId)
                .setTemplateId(WeChatServiceNotifyTemplateIdConstants.USER_GIVE_GIFT_SUCCESS)
                .setData(dataMap);
        try {
            wxService.sendTemplateMessage(wxServiceNotifyDto);
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("礼物赠送成功通知：完成");
    }
}
