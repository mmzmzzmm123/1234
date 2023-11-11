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
import com.ruoyi.user.domain.UserLevel;
import com.ruoyi.user.mapper.UserInfoMapper;
import com.ruoyi.user.mapper.UserLevelMapper;
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
 * @date 2023/10/11 2:19
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_APPOINT_ORDER_SUCCESS_NOTICE, consumerGroup = MqConstants.GROUP_APPOINT_ORDER_SUCCESS_NOTICE, selectorExpression = "*")
public class AppointOrderSuccessNoticeMqConsumer implements RocketMQListener<Long> {

    private final UserOfficialAccountMapper userOfficialAccountMapper;
    private final UserInfoMapper userInfoMapper;
    private final UserLevelMapper userLevelMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final WxProperties wxProperties;
    private final WxMpTemplateMassageService wxMpTemplateMassageService;

    @Override
    public void onMessage(Long orderId) {
        log.info("mq消费-指定单下单完成通知用户与店员：开始，参数：{}", orderId);
        // 查询订单信息
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        if (ObjectUtil.isNull(orderInfo)){
            log.warn("mq消费-指定单下单完成通知用户与店员：失败，查询订单信息为空");
            throw new ServiceException("mq消费-指定单下单完成通知用户与店员：失败，查询订单信息为空");
        }
        // 客户数据通知
        String customUserUnionId = userInfoMapper.getUnionIdById(orderInfo.getCustomUserId());
        if (StringUtils.isNotBlank(customUserUnionId)) {
            String customUserOpenId = userOfficialAccountMapper.getOpenIdByUnionId(customUserUnionId);
            if (StringUtils.isNotBlank(customUserOpenId)) {
                // 构建用户消息提醒
                WxMpTemplateMessage userMessage = new WxMpTemplateMessage();
                // 跳转到小程序中
                WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
                miniProgram.setAppid(wxProperties.getMiNiApplet().getAppId());
                miniProgram.setPagePath("orderPackages/page/list/index");
                userMessage.setMiniProgram(miniProgram);
                // 模板数据
                Map<String,String> dataMap = new HashMap<>();
                dataMap.put("number1", orderInfo.getOrderNo());
                dataMap.put("phrase2", OrderTypeEnums.APPOINT.getDesc());
                dataMap.put("amount3", orderInfo.getPayAmount().toString());
                dataMap.put("time4", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM, orderInfo.getCreateTime()));
                List<WxMpTemplateData> wxMpTemplateData = WxMpTemplateMassageService.MapToData(dataMap);
                userMessage.setData(wxMpTemplateData);
                userMessage.setTemplateId(WxMpTemplateIdConstants.PAY_ORDER_SUCCESS);
                userMessage.setToUser(customUserOpenId);
                wxMpTemplateMassageService.wxMpSendTemplateMessage(userMessage);
            }
        }
        UserLevel userLevel = userLevelMapper.selectByUserId(orderInfo.getCustomUserId());
        String remark = OrderTypeEnums.APPOINT.getDesc();
        if (ObjectUtil.isNotNull(userLevel)){
            remark += "-vip"+userLevel.getCurrentLevel();
        }
        if (StringUtils.isNotBlank(orderInfo.getRemark())){
            remark += "，备注："+orderInfo.getRemark();
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
                miniProgram.setPagePath("staffPackages/page/staffOrder/index");
                miniProgram.setAppid(wxProperties.getMiNiApplet().getAppId());
                staffMessage.setMiniProgram(miniProgram);
                // 模板数据
                Map<String,String> dataMap = new HashMap<>();
                dataMap.put("character_string2", orderInfo.getOrderNo());
                dataMap.put("thing9", StringUtils.overHide_20(remark));
                dataMap.put("amount3", orderInfo.getPayAmount().toString());
                dataMap.put("time5", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM, orderInfo.getCreateTime()));
                List<WxMpTemplateData> wxMpTemplateData = WxMpTemplateMassageService.MapToData(dataMap);
                staffMessage.setData(wxMpTemplateData);
                wxMpTemplateMassageService.wxMpSendTemplateMessage(staffMessage);
            }
        }
        log.info("mq消费-指定单下单完成通知用户与店员：完成");
    }


}
