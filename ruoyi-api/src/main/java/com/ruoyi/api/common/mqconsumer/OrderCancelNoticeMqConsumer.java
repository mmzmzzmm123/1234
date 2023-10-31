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
 * @date 2023/10/16 22:18
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_ORDER_CANCEL_NOTICE, consumerGroup = MqConstants.GROUP_ORDER_CANCEL_NOTICE, selectorExpression = "*")
public class OrderCancelNoticeMqConsumer implements RocketMQListener<Long> {

    private final UserOfficialAccountMapper userOfficialAccountMapper;
    private final UserInfoMapper userInfoMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final WxProperties wxProperties;
    private final WxMpTemplateMassageService wxMpTemplateMassageService;

    @Override
    public void onMessage(Long orderId) {
        log.info("mq消费-订单取消通知：开始，参数：{}", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        if (ObjectUtil.isNull(orderInfo)){
            log.warn("mq消费-订单取消通知：失败，查询订单信息为空");
            throw new ServiceException("mq消费-订单取消通知：失败，查询订单信息为空");
        }
        // 客户数据通知
        String customUserUnionId = userInfoMapper.getUnionIdById(orderInfo.getCustomUserId());
        if (StringUtils.isNotBlank(customUserUnionId)) {
            String customUserOpenId = userOfficialAccountMapper.getOpenIdByUnionId(customUserUnionId);
            if (StringUtils.isNotBlank(customUserOpenId)) {
                wxMpTemplateMassageService.wxMpSendTemplateMessage(buildMessageTemplate(orderInfo, customUserOpenId, "orderPackages/page/list/index"));
            }
        }
        // 店员通知
        String staffUnionId = userInfoMapper.getUnionIdById(orderInfo.getStaffUserId());
        if (StringUtils.isNotBlank(staffUnionId)) {
            String staffOpenId = userOfficialAccountMapper.getOpenIdByUnionId(staffUnionId);
            if (StringUtils.isNotBlank(staffOpenId)) {
                wxMpTemplateMassageService.wxMpSendTemplateMessage(buildMessageTemplate(orderInfo, staffOpenId, "staffPackages/page/staffOrder/index"));
            }
        }
        log.info("mq消费-订单取消通知：完成");
    }

    /**
     * 构建公众号模板消息
     *
     * @param orderInfo 订单信息
     * @param openId 用户openid
     * @param pagePath 小程序路径
     * @return 结果
     * */
    private WxMpTemplateMessage buildMessageTemplate(OrderInfo orderInfo, String openId, String pagePath){
        OrderTypeEnums orderTypeEnums = OrderTypeEnums.getByCode(orderInfo.getOrderType());
        // 构建用户消息提醒
        WxMpTemplateMessage message = new WxMpTemplateMessage();
        // 跳转到小程序中
        WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
        miniProgram.setAppid(wxProperties.getMiNiApplet().getAppId());
        miniProgram.setPagePath(pagePath);
        message.setMiniProgram(miniProgram);
        // 模板数据
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("character_string1", orderInfo.getOrderNo());
        dataMap.put("short_thing2", ObjectUtil.isNotNull(orderTypeEnums)?orderTypeEnums.getDesc():"未知");
        dataMap.put("amount3", orderInfo.getPayAmount().toString());
        dataMap.put("time4", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM, orderInfo.getOrderCancelTime()));
        dataMap.put("thing5", StringUtils.overHide_20(orderInfo.getCancelRemark()));
        List<WxMpTemplateData> wxMpTemplateData = WxMpTemplateMassageService.MapToData(dataMap);
        message.setData(wxMpTemplateData);
        // 基本信息设置
        message.setToUser(openId);
        message.setTemplateId(WxMpTemplateIdConstants.ORDER_CANCEL);
        return message;
    }
}
