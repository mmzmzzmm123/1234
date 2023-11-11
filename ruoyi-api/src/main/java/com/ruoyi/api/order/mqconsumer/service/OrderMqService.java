package com.ruoyi.api.order.mqconsumer.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.CommissionRecordTypeEnums;
import com.ruoyi.common.enums.DistributionLevelEnums;
import com.ruoyi.common.enums.StaffWalletRecordTypeEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.distribution.domain.DistributionCommissionRate;
import com.ruoyi.distribution.domain.DistributionCommissionRecord;
import com.ruoyi.distribution.domain.DistributionRelation;
import com.ruoyi.distribution.mapper.DistributionCommissionRateMapper;
import com.ruoyi.distribution.mapper.DistributionCommissionRecordMapper;
import com.ruoyi.distribution.mapper.DistributionRelationMapper;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.staff.domain.StaffWallet;
import com.ruoyi.staff.domain.StaffWalletRecord;
import com.ruoyi.staff.mapper.StaffWalletMapper;
import com.ruoyi.staff.mapper.StaffWalletRecordMapper;
import com.ruoyi.user.domain.UserWallet;
import com.ruoyi.user.mapper.UserWalletMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author LAM
 * @date 2023/11/3 23:49
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderMqService {

    private final UserWalletMapper userWalletMapper;
    private final StaffWalletMapper staffWalletMapper;
    private final StaffWalletRecordMapper staffWalletRecordMapper;
    private final DistributionRelationMapper distributionRelationMapper;
    private final DistributionCommissionRecordMapper distributionCommissionRecordMapper;
    private final DistributionCommissionRateMapper distributionCommissionRateMapper;


    /**
     * 店员订单佣金结算
     *
     * @param orderInfo 订单数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void staffOrderSettlement(OrderInfo orderInfo) {
        log.info("店员订单佣金结算：开始，订单数据：{}", orderInfo);
        Date now = DateUtils.getNowDate();
        String sysName = Constants.SYS_NAME;
        // 查询店员钱包数据
        StaffWallet staffWallet = staffWalletMapper.selectByUserIdForUpdate(orderInfo.getStaffUserId());
        if (ObjectUtil.isNull(staffWallet)) {
            log.warn("店员订单佣金结算：失败，无法找到店员钱包数据，id:{}", orderInfo.getStaffUserId());
            throw new ServiceException("店员订单佣金结算：失败，无法找到店员钱包数据");
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
                .setOrderId(orderInfo.getId())
                .setStaffWalletRecordType(StaffWalletRecordTypeEnums.ENTRY.getCode())
                .setAmount(commission)
                .setCreateBy(sysName)
                .setUpdateBy(sysName)
                .setCreateTime(now)
                .setUpdateTime(now);
        staffWalletRecordMapper.insertStaffWalletRecord(insetSwr);
        log.info("店员订单佣金结算：完成");
    }

    /**
     * 分销佣金结算
     *
     * @param orderInfo 订单数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void distributionCommissionSettlement(OrderInfo orderInfo) {
        log.info("分销佣金结算：开始，订单数据：{}", orderInfo);
        // 查询对应的分销佣金比例
        List<DistributionCommissionRate> distributionCommissionRates = distributionCommissionRateMapper.selectDistributionCommissionRateList(new DistributionCommissionRate());
        if (ObjectUtil.isNull(distributionCommissionRates)) {
            log.info("分销佣金结算：完毕，无法查询到分销比例数据");
            return;
        }
        // 一级分销佣金下发
        DistributionRelation selectOneLevel = new DistributionRelation();
        selectOneLevel.setLevel(Long.parseLong(DistributionLevelEnums.ONE_LEVEL.getCode()))
                .setCId(orderInfo.getCustomUserId());
        List<DistributionRelation> oneDistributionRelations = distributionRelationMapper.selectDistributionRelationList(selectOneLevel);
        if (ObjectUtil.isNotEmpty(oneDistributionRelations)) {
            DistributionRelation oneLevelRelation = oneDistributionRelations.get(0);
            DistributionCommissionRate oneLevelRate = distributionCommissionRates.stream().filter(item -> item.getLevel().equals(oneLevelRelation.getLevel())).findFirst().orElse(null);
            if (ObjectUtil.isNotNull(oneLevelRate)) {
                // 一级佣金
                BigDecimal oneLevelCommission = orderInfo.getPayAmount().multiply(oneLevelRate.getRate());
                UserWallet oneLevelUserWallet = userWalletMapper.selectByUserIdForUpdate(oneLevelRelation.getPId());
                // 更新一级分销佣金数据
                if (ObjectUtil.isNotNull(oneLevelUserWallet)) {
                    // 用户钱包佣金数据
                    UserWallet updateOneLevelWallet = buildNewUserWallet(oneLevelUserWallet, oneLevelCommission);
                    userWalletMapper.updateUserWallet(updateOneLevelWallet);
                    // 佣金记录
                    DistributionCommissionRecord insertOneLevelRecord = buildNewCommissionRecord(orderInfo, oneLevelRelation, oneLevelRate, oneLevelCommission);
                    distributionCommissionRecordMapper.insertDistributionCommissionRecord(insertOneLevelRecord);
                }
            }
        }
        // 二级分销佣金下发
        DistributionCommissionRate twoLevelRate = distributionCommissionRates.stream().filter(item -> item.getLevel().equals(Long.parseLong(DistributionLevelEnums.TWO_LEVEL.getCode()))).findFirst().orElse(null);
        if (ObjectUtil.isNotNull(twoLevelRate)) {
            DistributionRelation selectTwoLevel = new DistributionRelation();
            selectTwoLevel.setLevel(Long.parseLong(DistributionLevelEnums.TWO_LEVEL.getCode()))
                    .setCId(orderInfo.getCustomUserId());
            List<DistributionRelation> twoDistributionRelations = distributionRelationMapper.selectDistributionRelationList(selectTwoLevel);
            if (ObjectUtil.isNotEmpty(twoDistributionRelations)) {
                DistributionRelation twoLevelRelation = twoDistributionRelations.get(0);
                // 二级佣金
                UserWallet twoLevelUserWallet = userWalletMapper.selectByUserIdForUpdate(twoLevelRelation.getPId());
                BigDecimal twoLevelCommission = orderInfo.getPayAmount().multiply(twoLevelRate.getRate());
                // 更新二级分销佣金数据
                if (ObjectUtil.isNotNull(twoLevelUserWallet)) {
                    // 用户钱包佣金数据
                    UserWallet updateOneLevelWallet = buildNewUserWallet(twoLevelUserWallet, twoLevelCommission);
                    userWalletMapper.updateUserWallet(updateOneLevelWallet);
                    // 佣金记录
                    DistributionCommissionRecord insertOneLevelRecord = buildNewCommissionRecord(orderInfo, twoLevelRelation, twoLevelRate, twoLevelCommission);
                    distributionCommissionRecordMapper.insertDistributionCommissionRecord(insertOneLevelRecord);
                }
            }
        }
        log.info("分销佣金结算：完成");
    }

    /**
     * 构建新的佣金记录数据
     *
     * @param orderInfo                  订单数据
     * @param distributionRelation       分销关系
     * @param distributionCommissionRate 分销比例
     * @param commission                 佣金
     * @return 结果
     */
    private DistributionCommissionRecord buildNewCommissionRecord(OrderInfo orderInfo, DistributionRelation distributionRelation, DistributionCommissionRate distributionCommissionRate, BigDecimal commission) {
        DistributionCommissionRecord newCommissionRecord = new DistributionCommissionRecord();
        newCommissionRecord.setUserId(distributionRelation.getPId())
                .setCommissionRecordType(CommissionRecordTypeEnums.ENTRY.getCode())
                .setOrderId(orderInfo.getId())
                .setOrderAmount(orderInfo.getPayAmount())
                .setDistributionUserId(distributionRelation.getCId())
                .setDistributionLevel(distributionRelation.getLevel())
                .setDistributionRate(distributionCommissionRate.getRate())
                .setDistributionCommission(commission)
                .setCreateTime(DateUtils.getNowDate());
        return newCommissionRecord;
    }

    /**
     * 构建新的用户钱包数据
     *
     * @param userWallet 数据
     * @param commission 佣金
     * @return 结果
     */
    private UserWallet buildNewUserWallet(UserWallet userWallet, BigDecimal commission) {
        UserWallet newUserWallet = new UserWallet();
        newUserWallet.setId(userWallet.getId())
                .setDistributionCommission(userWallet.getDistributionCommission().add(commission))
                .setTotalDistributionCommission(userWallet.getTotalDistributionCommission().add(commission))
                .setUpdateTime(DateUtils.getNowDate());
        return newUserWallet;
    }
}
