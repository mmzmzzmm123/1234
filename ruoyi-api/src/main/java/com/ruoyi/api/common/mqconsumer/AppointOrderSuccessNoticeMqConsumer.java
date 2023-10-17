package com.ruoyi.api.common.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.wechat.constant.WechatMediaIdConstant;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.weixin.properties.WxProperties;
import com.ruoyi.common.weixin.service.WechatMsgReplyService;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.user.mapper.UserInfoMapper;
import com.ruoyi.user.mapper.UserOfficialAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.builder.kefu.MiniProgramPageBuilder;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

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
    private final OrderInfoMapper orderInfoMapper;
    private final WxProperties wxProperties;
    private final WechatMsgReplyService wechatMsgReplyService;

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
                MiniProgramPageBuilder miniProgramPageBuilder = new MiniProgramPageBuilder();
                miniProgramPageBuilder.appId(wxProperties.getMiNiApplet().getAppId())
                        .title("指定单下单成功")
                        .pagePath("orderPackages/page/list/index")
                        .thumbMediaId(WechatMediaIdConstant.WX_MINI_INDEX_IMG);
                wechatMsgReplyService.replyMiniProgram(customUserOpenId, miniProgramPageBuilder);
            }
        }
        // 店员通知
        String staffUnionId = userInfoMapper.getUnionIdById(orderInfo.getStaffUserId());
        if (StringUtils.isNotBlank(staffUnionId)) {
            String staffOpenId = userOfficialAccountMapper.getOpenIdByUnionId(staffUnionId);
            if (StringUtils.isNotBlank(staffOpenId)) {
                MiniProgramPageBuilder miniProgramPageBuilder = new MiniProgramPageBuilder();
                miniProgramPageBuilder.appId(wxProperties.getMiNiApplet().getAppId())
                        .title("您有一笔新的指定单")
                        .pagePath("staffPackages/page/staffOrder/index")
                        .thumbMediaId(WechatMediaIdConstant.WX_MINI_INDEX_IMG);
                wechatMsgReplyService.replyMiniProgram(staffOpenId, miniProgramPageBuilder);
            }
        }
        log.info("mq消费-指定单下单完成通知用户与店员：完成");
    }


}
