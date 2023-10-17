package com.ruoyi.api.common.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.wechat.constant.WechatMediaIdConstant;
import com.ruoyi.common.constant.WeChatServiceNotifyTemplateIdConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.weixin.WxService;
import com.ruoyi.common.weixin.model.dto.WxServiceNotifyDto;
import com.ruoyi.common.weixin.properties.WxProperties;
import com.ruoyi.common.weixin.service.WechatMsgReplyService;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.staff.domain.StaffInfo;
import com.ruoyi.staff.mapper.StaffInfoMapper;
import com.ruoyi.user.mapper.UserInfoMapper;
import com.ruoyi.user.mapper.UserOfficialAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.builder.kefu.MiniProgramPageBuilder;
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

    private final UserOfficialAccountMapper userOfficialAccountMapper;
    private final UserInfoMapper userInfoMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final WxProperties wxProperties;
    private final WechatMsgReplyService wechatMsgReplyService;


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
                MiniProgramPageBuilder miniProgramPageBuilder = new MiniProgramPageBuilder();
                miniProgramPageBuilder.appId(wxProperties.getMiNiApplet().getAppId())
                        .title("订单接单通知")
                        .pagePath("orderPackages/page/list/index")
                        .thumbMediaId(WechatMediaIdConstant.WX_MINI_INDEX_IMG);
                wechatMsgReplyService.replyMiniProgram(customUserOpenId, miniProgramPageBuilder);
            }
        }
        log.info("mq消费-订单接单通知：完成");
    }


}
