package com.ruoyi.api.common.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.WxMpTemplateIdConstants;
import com.ruoyi.common.enums.OrderTypeEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.weixin.WxMpTemplateMassageService;
import com.ruoyi.common.weixin.properties.WxProperties;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.user.mapper.UserInfoMapper;
import com.ruoyi.user.mapper.UserOfficialAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * @author LAM
 * @date 2023/10/10 17:27
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_GIVE_GIFT_SUCCESS_NOTICE, consumerGroup = MqConstants.GROUP_GIVE_GIFT_SUCCESS_NOTICE, selectorExpression = "*")
public class GiveGiftSuccessNoticeMqConsumer implements RocketMQListener<Long> {

    private final UserOfficialAccountMapper userOfficialAccountMapper;
    private final UserInfoMapper userInfoMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final WxMpTemplateMassageService wxMpTemplateMassageService;
    private final WxProperties wxProperties;

    @Override
    public void onMessage(Long orderId) {
        log.info("mq消费-礼物赠送成功通知：开始，参数：{}", orderId);
        // 查询订单信息
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        if (ObjectUtil.isNull(orderInfo)){
            log.warn("mq消费-礼物赠送成功通知：失败，查询订单信息为空");
            throw new ServiceException("mq消费-礼物赠送成功通知：失败，查询订单信息为空");
        }
        // 客户数据通知
        String customUserUnionId = userInfoMapper.getUnionIdById(orderInfo.getCustomUserId());
        if (StringUtils.isNotBlank(customUserUnionId)) {
            String customUserOpenId = userOfficialAccountMapper.getOpenIdByUnionId(customUserUnionId);
            if (StringUtils.isNotBlank(customUserOpenId)) {
                // 构建用户消息提醒
                WxMpTemplateMessage userMessage = new WxMpTemplateMessage();
                userMessage.setToUser(customUserOpenId);
                userMessage.setTemplateId(WxMpTemplateIdConstants.PAY_ORDER_SUCCESS);
                // 跳转到小程序中
                WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
                miniProgram.setAppid(wxProperties.getMiNiApplet().getAppId());
                miniProgram.setPagePath("orderPackages/page/list/index");
                userMessage.setMiniProgram(miniProgram);
                // 模板数据
                Map<String,String> dataMap = new HashMap<>();
                dataMap.put("number1", orderInfo.getOrderNo());
                dataMap.put("phrase2", OrderTypeEnums.GIFT.getDesc());
                dataMap.put("amount3", orderInfo.getPayAmount().toString());
                dataMap.put("time4", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM, orderInfo.getCreateTime()));
                List<WxMpTemplateData> wxMpTemplateData = WxMpTemplateMassageService.MapToData(dataMap);
                userMessage.setData(wxMpTemplateData);
                wxMpTemplateMassageService.wxMpSendTemplateMessage(userMessage);
            }
        }
        // 店员通知
        String staffUnionId = userInfoMapper.getUnionIdById(orderInfo.getStaffUserId());
        if (StringUtils.isNotBlank(staffUnionId)) {
            String staffOpenId = userOfficialAccountMapper.getOpenIdByUnionId(staffUnionId);
            if (StringUtils.isNotBlank(staffOpenId)) {
                // 构建消息模板数据
                WxMpTemplateMessage staffMessage = new WxMpTemplateMessage();
                staffMessage.setToUser(staffOpenId);
                staffMessage.setTemplateId(WxMpTemplateIdConstants.NEW_ORDER_TIPS_ID);
                // 跳转到小程序中
                WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
                miniProgram.setAppid(wxProperties.getMiNiApplet().getAppId());
                miniProgram.setPagePath("staffPackages/page/staffOrder/index");
                staffMessage.setMiniProgram(miniProgram);
                // 模板数据
                Map<String,String> dataMap = new HashMap<>();
                dataMap.put("character_string2", orderInfo.getOrderNo());
                dataMap.put("thing9", StringUtils.overHide_20(OrderTypeEnums.GIFT.getDesc()+"，备注："+(StringUtils.isNotBlank(orderInfo.getRemark())?orderInfo.getRemark():"无")));
                dataMap.put("amount3", orderInfo.getPayAmount().toString());
                dataMap.put("time5", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM, orderInfo.getCreateTime()));
                List<WxMpTemplateData> wxMpTemplateData = WxMpTemplateMassageService.MapToData(dataMap);
                staffMessage.setData(wxMpTemplateData);
                wxMpTemplateMassageService.wxMpSendTemplateMessage(staffMessage);
            }
        }
        log.info("mq消费-礼物赠送成功通知：完成");
    }

}
