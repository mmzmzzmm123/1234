package com.ruoyi.api.payment.mqconsumer.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.*;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.RocketMqService;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.order.domain.OrderDetails;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderDetailsMapper;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.staff.domain.StaffGiftRecord;
import com.ruoyi.staff.mapper.StaffGiftRecordMapper;
import com.ruoyi.user.domain.UserRechargeRecord;
import com.ruoyi.user.domain.UserWallet;
import com.ruoyi.user.domain.UserWalletRecord;
import com.ruoyi.user.mapper.UserRechargeRecordMapper;
import com.ruoyi.user.mapper.UserWalletMapper;
import com.ruoyi.user.mapper.UserWalletRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/6 1:23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentMqConsumerService {

    private final OrderInfoMapper orderInfoMapper;
    private final OrderDetailsMapper orderDetailsMapper;
    private final UserRechargeRecordMapper rechargeRecordMapper;
    private final UserWalletMapper userWalletMapper;
    private final UserWalletRecordMapper userWalletRecordMapper;
    private final RocketMqService rocketMqService;

    /**
     * 支付回调-充值订单
     *
     * @param orderId 充值记录标识
     */
    @Transactional(rollbackFor = Exception.class)
    public void rechargeOrderCallback(Long orderId) {
        log.info("mq消费-支付回调-充值订单：开始，参数：{}", orderId);
        // 查询用户充值记录
        UserRechargeRecord userRechargeRecord = rechargeRecordMapper.selectUserRechargeRecordById(orderId);
        if (ObjectUtil.isNull(userRechargeRecord)) {
            log.warn("mq消费-支付回调-充值订单：失败，无法找到对应的充值记录数据");
            throw new ServiceException("支付回调-充值订单：失败，无法找到对应的充值记录数据");
        }
        // 查询用户钱包数据
        UserWallet userWallet = userWalletMapper.selectByUserIdForUpdate(userRechargeRecord.getUserId());
        if (ObjectUtil.isNull(userWallet)) {
            log.warn("mq消费-支付回调-充值订单：失败，无法获取到用户的钱包数据");
            throw new ServiceException("支付回调-充值订单：失败，无法获取到用户的钱包数据");
        }

        BigDecimal amount = userRechargeRecord.getRechargeAmount();
        BigDecimal giftAmount = userRechargeRecord.getGiftAmount();

        // 先完善需要更新用户的充值记录
        UserRechargeRecord updateUrr = new UserRechargeRecord();
        updateUrr.setId(orderId)
                .setPayState(PayStateEnums.SUCCESS.getCode())
                .setRechargeAmountBefore(userWallet.getBalance().add(userWallet.getGiftBalance()))
                .setRechargeAmountAfter(updateUrr.getRechargeAmountBefore().add(amount).add(giftAmount))
                .setUpdateBy(Constants.SYS_NAME)
                .setUpdateTime(DateUtils.getNowDate());
        rechargeRecordMapper.updateUserRechargeRecord(updateUrr);

        // 开始为用户添加对应的充值金额
        UserWallet updateUw = new UserWallet();
        updateUw.setId(userWallet.getId())
                .setBalance(userWallet.getBalance().add(amount))
                .setGiftBalance(userWallet.getGiftBalance().add(giftAmount))
                .setTotalBalance(userWallet.getTotalBalance().add(amount))
                .setTotalGiftBalance(userWallet.getTotalGiftBalance().add(giftAmount))
                .setUpdateTime(DateUtils.getNowDate());
        userWalletMapper.updateUserWallet(updateUw);

        // 开始处理用户钱包记录
        UserWalletRecord insertUwr = new UserWalletRecord();
        insertUwr.setWalletRecordType(UserWalletRecordTypeEnums.RECHARGE.getCode())
                .setUserId(userWallet.getUserId())
                .setRechargeRecordId(orderId)
                .setAmount(amount.add(giftAmount))
                .setAmountBefore(updateUrr.getRechargeAmountBefore())
                .setAmountAfter(updateUrr.getRechargeAmountAfter())
                .setCreateTime(DateUtils.getNowDate());
        userWalletRecordMapper.insertUserWalletRecord(insertUwr);
        log.info("mq消费-支付回调-充值订单：完成");
    }

    /**
     * 礼物订单支付成功回调业务
     *
     * @param orderId 订单标识
     */
    @Transactional(rollbackFor = Exception.class)
    public void giftOrderCallback(Long orderId) {
        log.info("mq消费-支付回调-礼物订单支付成功回调业务：开始，参数：{}", orderId);
        PaymentMqConsumerService paymentMqConsumerService = SpringUtils.getBean(PaymentMqConsumerService.class);
        // 处理订单和详情数据
        paymentMqConsumerService.updateOrderInfoAndDetails(orderId, OrderTypeEnums.GIFT.getCode());
        // 发送mq消息处理赠送礼物后的业务
        rocketMqService.asyncSend(MqConstants.TOPIC_GIVE_GIFT_SUCCESS, orderId);
        log.info("礼物订单支付成功回调业务：完成");
    }

    /**
     * 打赏订单支付成功回调业务
     */
    @Transactional(rollbackFor = Exception.class)
    public void rewardOrderCallback(Long orderId) {
        log.info("mq消费-支付回调-打赏订单支付成功回调业务：开始，参数：{}", orderId);
        PaymentMqConsumerService paymentMqConsumerService = SpringUtils.getBean(PaymentMqConsumerService.class);
        // 处理订单和详情状态
        paymentMqConsumerService.updateOrderInfoAndDetails(orderId, OrderTypeEnums.REWARD.getCode());
        // 发送mq消息处理打赏成功后业务
        rocketMqService.asyncSend(MqConstants.TOPIC_REWARD_SUCCESS, orderId);
        log.info("mq消费-支付回调-打赏订单支付成功回调业务：完成");
    }

    /**
     * 指定单支付成功回调业务
     * */
    public void appointOrderCallback(Long orderId) {
        log.info("指定单支付成功回调业务：开始，参数：{}", orderId);
        PaymentMqConsumerService paymentMqConsumerService = SpringUtils.getBean(PaymentMqConsumerService.class);
        // 处理订单和详情状态
        paymentMqConsumerService.updateOrderInfoAndDetails(orderId, OrderTypeEnums.APPOINT.getCode());
        // 发送订阅消息通知客户和店员
        rocketMqService.asyncSend(MqConstants.TOPIC_APPOINT_ORDER_SUCCESS_NOTICE, orderId);
        // 发送延时消息自动退单（5分钟）
        rocketMqService.delayedSend(MqConstants.TOPIC_ORDER_AUTO_CANCEL, orderId, MqDelayLevelEnums.level_9);
        log.info("指定单支付成功回调业务：完成");
    }

    /**
     * 随机单
     *
     * @param orderId   订单标识
     * */
    public void randomOrderCallback(Long orderId) {
        log.info("随机单支付成功回调业务：开始，参数：{}", orderId);
        PaymentMqConsumerService paymentMqConsumerService = SpringUtils.getBean(PaymentMqConsumerService.class);
        // 处理订单和详情状态
        paymentMqConsumerService.updateOrderInfoAndDetails(orderId, OrderTypeEnums.RANDOM.getCode());
        // TODO 随机单支付完成的相关业务
        log.info("随机单支付成功回调业务：完成");
    }

    /**
     * 订单信息和详情状态修改
     *
     * @param orderId   订单标识
     * @param orderType 订单类型
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderInfoAndDetails(Long orderId, String orderType) {
        log.info("mq消费-订单信息和详情状态修改：开始，参数：{}，{}", orderId, orderType);
        // 构建订单支付完成后的数据
        String autoSuccessTime = DateUtils.rollMinute(7, DateUtils.YYYY_MM_DD_HH_MM_SS);
        Date now = DateUtils.getNowDate();
        OrderInfo updateOrderInfo = new OrderInfo();
        updateOrderInfo.setId(orderId)
                .setOrderState(OrderStateEnums.PAID.getCode())
                .setAutoFinshTime(DateUtils.parseDate(autoSuccessTime))
                .setUpdateTime(now);
        // 订单类型是礼物，直接全部业务时间设置为当前
        if (OrderTypeEnums.GIFT.getCode().equals(orderType) || OrderTypeEnums.REWARD.getCode().equals(orderType)) {
            updateOrderInfo.setOrderState(OrderStateEnums.FINISH.getCode())
                    .setOrderReceivingTime(now)
                    .setOrderServiceTime(now)
                    .setOrderFinshTime(now);
        }
        orderInfoMapper.updateOrderInfo(updateOrderInfo);
        // 修改订单详情数据
        orderDetailsMapper.updateStateByOrderId(orderId, OrderStateEnums.FINISH.getCode());
        log.info("mq消费-订单信息和详情状态修改：完成");
    }

}
