package com.ruoyi.api.payment.mqconsumer.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.PayStateEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
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

/**
 * @author LAM
 * @date 2023/10/6 1:23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentMqConsumerService {

    private final UserRechargeRecordMapper rechargeRecordMapper;
    private final UserWalletMapper userWalletMapper;
    private final UserWalletRecordMapper userWalletRecordMapper;

    /**
     * 支付回调-充值订单
     *
     * @param orderId 充值记录标识
     * */
    @Transactional(rollbackFor = Exception.class)
    public void rechargeOrderCallback(Long orderId) {
        log.info("支付回调-充值订单：开始，参数：{}", orderId);
        // 查询用户充值记录
        UserRechargeRecord userRechargeRecord = rechargeRecordMapper.selectUserRechargeRecordById(orderId);
        if (ObjectUtil.isNull(userRechargeRecord)){
            log.warn("支付回调-充值订单：失败，无法找到对应的充值记录数据");
            throw new ServiceException("支付回调-充值订单：失败，无法找到对应的充值记录数据");
        }
        // 查询用户钱包数据
        UserWallet userWallet = userWalletMapper.selectByUserIdForUpdate(userRechargeRecord.getUserId());
        if (ObjectUtil.isNull(userWallet)){
            log.warn("支付回调-充值订单：失败，无法获取到用户的钱包数据");
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
        insertUwr.setUserId(userWallet.getUserId())
                .setRechargeRecordId(orderId)
                .setAmount(amount.add(giftAmount))
                .setAmountBefore(updateUrr.getRechargeAmountBefore())
                .setAmountAfter(updateUrr.getRechargeAmountAfter())
                .setCreateTime(DateUtils.getNowDate());
        userWalletRecordMapper.insertUserWalletRecord(insertUwr);
        log.info("支付回调-充值订单：完成");
    }
}
