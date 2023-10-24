package com.ruoyi.api.common.mqconsumer;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.wechat.constant.WechatMediaIdConstant;
import com.ruoyi.common.enums.OrderStateEnums;
import com.ruoyi.common.enums.OrderTypeEnums;
import com.ruoyi.common.enums.StaffStateEnums;
import com.ruoyi.common.enums.SysYesNoEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.weixin.properties.WxProperties;
import com.ruoyi.common.weixin.service.WechatMsgReplyService;
import com.ruoyi.order.domain.OrderDetails;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderDetailsMapper;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.staff.domain.StaffInfo;
import com.ruoyi.staff.mapper.StaffInfoMapper;
import com.ruoyi.user.mapper.UserInfoMapper;
import com.ruoyi.user.mapper.UserOfficialAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.builder.kefu.MiniProgramPageBuilder;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LAM
 * @date 2023/10/16 22:50
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_RANDOM_ORDER_SUCCESS_NOTICE, consumerGroup = MqConstants.GROUP_RANDOM_ORDER_SUCCESS_NOTICE, selectorExpression = "*")
public class RandomOrderSuccessNoticeMqConsumer implements RocketMQListener<Long> {

    private final UserOfficialAccountMapper userOfficialAccountMapper;
    private final UserInfoMapper userInfoMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final OrderDetailsMapper orderDetailsMapper;
    private final WxProperties wxProperties;
    private final WechatMsgReplyService wechatMsgReplyService;
    private final StaffInfoMapper staffInfoMapper;

    @Override
    public void onMessage(Long orderId) {
        log.info("mq消费-随机单下单完成通知用户与店员：开始，参数：{}", orderId);
        // 查询订单信息
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        if (ObjectUtil.isNull(orderInfo)) {
            log.warn("mq消费-随机单下单完成通知用户与店员：失败，查询订单信息为空");
            throw new ServiceException("mq消费-随机单下单完成通知用户与店员：失败，查询订单信息为空");
        }
        // 查询订单详情数据
        List<OrderDetails> orderDetailsList = orderDetailsMapper.selectByOrderId(orderId);
        if (ObjectUtil.isEmpty(orderDetailsList)) {
            log.warn("mq消费-随机单下单完成通知用户与店员：失败，无法查询订单详情数据");
            throw new ServiceException("mq消费-随机单下单完成通知用户与店员：失败，无法查询订单详情数据");
        }
        OrderDetails orderDetails = orderDetailsList.get(0);
        // 客户数据通知
        String customUserUnionId = userInfoMapper.getUnionIdById(orderInfo.getCustomUserId());
        if (StringUtils.isNotBlank(customUserUnionId)) {
            String customUserOpenId = userOfficialAccountMapper.getOpenIdByUnionId(customUserUnionId);
            if (StringUtils.isNotBlank(customUserOpenId)) {
                MiniProgramPageBuilder miniProgramPageBuilder = new MiniProgramPageBuilder();
                miniProgramPageBuilder.appId(wxProperties.getMiNiApplet().getAppId())
                        .title("随机单下单成功")
                        .pagePath("orderPackages/page/list/index")
                        .thumbMediaId(WechatMediaIdConstant.WX_MINI_INDEX_IMG);
                wechatMsgReplyService.replyMiniProgram(customUserOpenId, miniProgramPageBuilder);
            }
        }

        // 匹配条件对应的店员进行发送通知
        StaffInfo selectStaff = new StaffInfo();
        selectStaff.setState(StaffStateEnums.NORMAL.getCode())
                .setIfOnline(SysYesNoEnums.YES.getCode())
                .setSex(orderInfo.getChooseStaffSex())
                .setGreaterThanOrEqualLevel(orderInfo.getStaffLevel());
        List<StaffInfo> staffInfoList = staffInfoMapper.customSelect(selectStaff);
        // 过滤掉服务类型不一样的店员
        List<StaffInfo> matchList = staffInfoList.stream().filter(staffInfo -> (ObjectUtil.isNotEmpty(staffInfo.getServiceIdList()) && staffInfo.getServiceIdList().contains(orderDetails.getServiceId()))).collect(Collectors.toList());
        // 判断是否需要过滤掉服务过的店员
        if (SysYesNoEnums.YES.getCode().equals(orderInfo.getFilterServedStaff())) {
            // 查询该用户已的订单记录
            List<Long> servedStaffIds = orderInfoMapper.selectStaffUserIdByCustomUserId(orderInfo.getCustomUserId());
            if (ObjectUtil.isNotEmpty(servedStaffIds)) {
                matchList = matchList.stream().filter(staffInfo -> !servedStaffIds.contains(staffInfo.getUserId())).collect(Collectors.toList());
            }
        }
        // 过滤掉目前已接随机单并状态未完成的数据
        if (ObjectUtil.isNotEmpty(matchList)) {
            List<Long> notMatchUserIdList = new ArrayList<>();
            String[] tempStateArr = {OrderStateEnums.WAIT_SERVICE.getCode(), OrderStateEnums.SERVICE_ING.getCode()};
            for (int i = 0; i < matchList.size(); i++){
                StaffInfo tempStaffInfo = matchList.get(i);
                List<Long> orderIds = orderInfoMapper.selectIdByStaffIdAndTypeAndStateList(tempStaffInfo.getUserId(), OrderTypeEnums.RANDOM.getCode(), ListUtil.toList(tempStateArr));
                if (ObjectUtil.isNotEmpty(orderIds)){
                    notMatchUserIdList.add(tempStaffInfo.getUserId());
                }
            }
            // 再判断过滤一下是否存在不可接单的用户id
            if (ObjectUtil.isNotEmpty(notMatchUserIdList)){
                matchList = matchList.stream().filter(item -> !notMatchUserIdList.contains(item.getUserId())).collect(Collectors.toList());
            }
            // 最终通过的都是可接单人
            matchList.forEach(item -> {
                String staffUnionId = userInfoMapper.getUnionIdById(item.getUserId());
                if (StringUtils.isNotBlank(staffUnionId)){
                    String staffOpenId = userOfficialAccountMapper.getOpenIdByUnionId(staffUnionId);
                    if (StringUtils.isNotBlank(staffOpenId)) {
                        MiniProgramPageBuilder miniProgramPageBuilder = new MiniProgramPageBuilder();
                        miniProgramPageBuilder.appId(wxProperties.getMiNiApplet().getAppId())
                                .title("有一笔随机单待接单")
                                .pagePath("staffPackages/page/staffOrder/index")
                                .thumbMediaId(WechatMediaIdConstant.WX_MINI_INDEX_IMG);
                        wechatMsgReplyService.replyMiniProgram(staffOpenId, miniProgramPageBuilder);
                    }
                }
            });
        }
        log.info("mq消费-随机单下单完成通知用户与店员：完成");
    }
}
