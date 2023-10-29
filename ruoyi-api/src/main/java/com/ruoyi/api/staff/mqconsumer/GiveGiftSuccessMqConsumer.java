package com.ruoyi.api.staff.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.RocketMqService;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OrderDetails;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderDetailsMapper;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.staff.domain.StaffGiftRecord;
import com.ruoyi.staff.mapper.StaffGiftRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/10 15:48
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_GIVE_GIFT_SUCCESS, consumerGroup = MqConstants.GROUP_GIVE_GIFT_SUCCESS, selectorExpression = "*")
public class GiveGiftSuccessMqConsumer implements RocketMQListener<Long> {


    private final OrderInfoMapper orderInfoMapper;
    private final OrderDetailsMapper orderDetailsMapper;
    private final StaffGiftRecordMapper staffGiftRecordMapper;
    private final RocketMqService rocketMqService;

    @Override
    public void onMessage(Long orderId) {
        log.info("mq消费-赠送礼物成功业务处理：开始，参数：{}", orderId);
        // 查询订单信息
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        // 查询订单详情数据
        OrderDetails selectOrderDetails = new OrderDetails();
        selectOrderDetails.setOrderId(orderId);
        List<OrderDetails> orderDetailsList = orderDetailsMapper.selectOrderDetailsList(selectOrderDetails);
        if (ObjectUtil.isNull(orderInfo) || ObjectUtil.isEmpty(orderDetailsList)) {
            log.warn("mq消费-礼物订单支付成功回调业务：失败，无法找到订单相关数据");
            throw new ServiceException("礼物订单支付成功回调业务：失败，无法找到订单相关数据");
        }
        OrderDetails orderDetails = orderDetailsList.stream().findFirst().orElse(null);

        Date now = DateUtils.getNowDate();

        // 店员收取礼物记录业务处理
        StaffGiftRecord selectStaffGiftRecord = new StaffGiftRecord();
        selectStaffGiftRecord.setGiftId(orderDetails.getGiftId())
                .setStaffUserId(orderInfo.getStaffUserId());
        List<StaffGiftRecord> staffGiftRecordList = staffGiftRecordMapper.selectStaffGiftRecordList(selectStaffGiftRecord);
        if (ObjectUtil.isNotEmpty(staffGiftRecordList)) {
            StaffGiftRecord staffGiftRecord = staffGiftRecordList.stream().findFirst().orElse(null);
            staffGiftRecord.setGiftNum(staffGiftRecord.getGiftNum() + orderDetails.getNum())
                    .setUpdateTime(now);
            staffGiftRecordMapper.updateStaffGiftRecord(staffGiftRecord);
        } else {
            StaffGiftRecord insertSgr = new StaffGiftRecord();
            insertSgr.setStaffUserId(orderInfo.getStaffUserId())
                    .setGiftId(orderDetails.getGiftId())
                    .setGiftNum(orderDetails.getNum())
                    .setCreateTime(now)
                    .setUpdateTime(now);
            staffGiftRecordMapper.insertStaffGiftRecord(insertSgr);
        }
        // 订单佣金结算
        rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_COMMISSION_SETTLEMENT, orderId);

        // 发送赠送礼物成功通知
        rocketMqService.asyncSend(MqConstants.TOPIC_GIVE_GIFT_SUCCESS_NOTICE, orderId);

        log.info("mq消费-赠送礼物成功业务处理：完成");
    }


}
