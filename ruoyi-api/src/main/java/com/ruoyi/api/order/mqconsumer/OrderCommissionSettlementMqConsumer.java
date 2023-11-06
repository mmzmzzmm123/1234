package com.ruoyi.api.order.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.order.mqconsumer.service.OrderMqService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.StaffWalletRecordTypeEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.staff.domain.StaffWallet;
import com.ruoyi.staff.domain.StaffWalletRecord;
import com.ruoyi.staff.mapper.StaffWalletMapper;
import com.ruoyi.staff.mapper.StaffWalletRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author LAM
 * @date 2023/10/10 16:13
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = MqConstants.TOPIC_ORDER_COMMISSION_SETTLEMENT, consumerGroup = MqConstants.GROUP_ORDER_COMMISSION_SETTLEMENT, selectorExpression = "*")
public class OrderCommissionSettlementMqConsumer implements RocketMQListener<Long> {

    private final OrderInfoMapper orderInfoMapper;
    private final StaffWalletRecordMapper staffWalletRecordMapper;
    private final OrderMqService orderMqService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Long orderId) {
        log.info("订单佣金结算业务：开始，参数：{}", orderId);
        // 查询是否已结佣
        StaffWalletRecord selectSwr = new StaffWalletRecord();
        selectSwr.setOrderId(orderId);
        if (ObjectUtil.isNotEmpty(staffWalletRecordMapper.selectStaffWalletRecordList(selectSwr))){
            log.info("订单佣金结算业务：完成，该订单已完成佣金结算");
            return;
        }
        // 查询订单记录
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        if (ObjectUtil.isNull(orderInfo)){
            log.warn("订单佣金结算业务：失败，订单信息查询为空");
            throw new ServiceException("订单完成后结算店员佣金：失败，订单信息查询为空");
        }
        // 店员订单佣金结算
        orderMqService.staffOrderSettlement(orderInfo);
        // 分销佣金结算
        orderMqService.distributionCommissionSettlement(orderInfo);
        log.info("订单佣金结算业务：完成");
    }


}
