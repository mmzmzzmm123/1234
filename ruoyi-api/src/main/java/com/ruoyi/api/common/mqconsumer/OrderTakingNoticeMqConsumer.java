package com.ruoyi.api.common.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.wechat.constant.WechatMediaIdConstant;
import com.ruoyi.common.constant.WxMpTemplateIdConstants;
import com.ruoyi.common.enums.OrderTypeEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.weixin.WxMpTemplateMassageService;
import com.ruoyi.common.weixin.properties.WxProperties;
import com.ruoyi.common.weixin.service.WechatMsgReplyService;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.user.mapper.UserInfoMapper;
import com.ruoyi.user.mapper.UserOfficialAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.builder.kefu.MiniProgramPageBuilder;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
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

    private final UserOfficialAccountMapper userOfficialAccountMapper;
    private final UserInfoMapper userInfoMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final WxProperties wxProperties;
    private final WxMpTemplateMassageService wxMpTemplateMassageService;


    @Override
    public void onMessage(Long orderId) {
        log.info("mq消费-订单接单通知：开始，参数：{}", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        if (ObjectUtil.isNull(orderInfo)){
            log.warn("mq消费-订单接单通知：失败，查询订单信息为空");
            throw new ServiceException("mq消费-订单接单通知：失败，查询订单信息为空");
        }
        // 客户数据通知
        String customUserUnionId = userInfoMapper.getUnionIdById(orderInfo.getCustomUserId());
        if (StringUtils.isNotBlank(customUserUnionId)) {
            String customUserOpenId = userOfficialAccountMapper.getOpenIdByUnionId(customUserUnionId);
            if (StringUtils.isNotBlank(customUserOpenId)) {
                // 构建用户消息提醒
                WxMpTemplateMessage userMessage = new WxMpTemplateMessage();
                // 模板数据
                Map<String,String> dataMap = new HashMap<>();
                dataMap.put("character_string1", orderInfo.getOrderNo());
                dataMap.put("time2", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM, orderInfo.getOrderReceivingTime()));
                List<WxMpTemplateData> wxMpTemplateData = WxMpTemplateMassageService.MapToData(dataMap);
                // 跳转到小程序中
                WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
                miniProgram.setPagePath("orderPackages/page/list/index");
                miniProgram.setAppid(wxProperties.getMiNiApplet().getAppId());
                userMessage.setToUser(customUserOpenId);
                userMessage.setTemplateId(WxMpTemplateIdConstants.ORDER_TAKING);
                userMessage.setMiniProgram(miniProgram);
                userMessage.setData(wxMpTemplateData);
                wxMpTemplateMassageService.wxMpSendTemplateMessage(userMessage);
            }
        }
        log.info("mq消费-订单接单通知：完成");
    }


}
