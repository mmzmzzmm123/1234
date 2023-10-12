package com.ruoyi.api.order.mqconsumer;

import cn.hutool.core.util.ObjectUtil;
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
    private final StaffWalletMapper staffWalletMapper;
    private final StaffWalletRecordMapper staffWalletRecordMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Long orderId) {
        log.info("订单完成后结算店员佣金：开始，参数：{}", orderId);
        Date now = DateUtils.getNowDate();
        String sysName = Constants.SYS_NAME;
        // 查询订单记录
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        if (ObjectUtil.isNull(orderInfo)){
            log.warn("订单完成后结算店员佣金：失败，订单信息查询为空");
            throw new ServiceException("订单完成后结算店员佣金：失败，订单信息查询为空");
        }
        // 查询店员钱包数据
        StaffWallet staffWallet = staffWalletMapper.selectByUserIdForUpdate(orderInfo.getStaffUserId());
        if (ObjectUtil.isNull(staffWallet)){
            log.warn("订单完成后结算店员佣金：失败，无法找到店员钱包数据，id:{}", orderInfo.getStaffUserId());
            throw new ServiceException("订单完成后结算店员佣金：失败，无法找到店员钱包数据");
        }
        // 本订单佣金
        BigDecimal commission = orderInfo.getPayAmount().multiply(orderInfo.getCommissionRatio());
        // 开始修改店员钱包佣金数据
        staffWallet.setWaitCommission(staffWallet.getWaitCommission().add(commission))
                .setTotalOrderPrice(staffWallet.getTotalOrderPrice().add(commission))
                .setUpdateBy(sysName)
                .setUpdateTime(now);
        staffWalletMapper.updateStaffWallet(staffWallet);
        // 店员钱包记录
        StaffWalletRecord insetSwr = new StaffWalletRecord();
        insetSwr.setStaffUserId(orderInfo.getStaffUserId())
                .setOrderId(orderId)
                .setStaffWalletRecordType(StaffWalletRecordTypeEnums.ENTRY.getCode())
                .setAmount(commission)
                .setCreateBy(sysName)
                .setUpdateBy(sysName)
                .setCreateTime(now)
                .setUpdateTime(now);
        staffWalletRecordMapper.insertStaffWalletRecord(insetSwr);
        log.info("订单完成后结算店员佣金：完成");
    }
}
