package com.ruoyi.api.user.common;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.user.domain.UserLevel;
import com.ruoyi.user.domain.UserLevelConfig;
import com.ruoyi.user.mapper.UserLevelConfigMapper;
import com.ruoyi.user.mapper.UserLevelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LAM
 * @date 2023/10/6 11:54
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserLevelCommonService {

    private final OrderInfoMapper orderInfoMapper;
    private final UserLevelMapper userLevelMapper;
    private final UserLevelConfigMapper userLevelConfigMapper;

    /**
     * 用户等级计算
     *
     * @param userId 用户标识
     * @param amount 金额
     */
    @Transactional(rollbackFor = Exception.class)
    public void levelCount(Long userId, BigDecimal amount) {
        log.info("用户等级计算：开始，参数：{}，{}", userId, amount);
        UserLevel userLevel = userLevelMapper.selectByUserId(userId);
        if (ObjectUtil.isNull(userLevel)) {
            log.warn("用户等级计算：失败，无法查询到用户等级数据");
            throw new ServiceException("用户等级计算：失败，无法查询到用户等级数据");
        }
        Date now = DateUtils.getNowDate();
        // 先更新用户等级的总积分
        userLevel.setTotalPoints(userLevel.getTotalPoints().add(amount))
                .setUpdateTime(now);
        // 查询到用户等级配置数据
        List<UserLevelConfig> userLevelConfigs = userLevelConfigMapper.selectUserLevelConfigList(new UserLevelConfig());
        // 最高等级判断
        if (ifMaxLevel(userLevel, userLevelConfigs)) {
            userLevel.setCurrentPoints(userLevel.getCurrentPoints().add(amount));
            userLevelMapper.updateUserLevel(userLevel);
            log.info("用户等级计算：完成，用户已达到最高等级，直接更新总积分和当前积分即可");
            return;
        }
        // 开始计算用户等级数据
        handleUserLevelData(userLevel, userLevelConfigs);
        userLevelMapper.updateUserLevel(userLevel);
        log.info("用户等级计算：完成");
    }

    /**
     * 用户等级退款减扣
     *
     * @param orderId 订单标识
     */
    @Transactional(rollbackFor = Exception.class)
    public void levelDeduction(Long orderId) {
        log.info("用户等级退款减扣：开始，参数：{}", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderId);
        if (ObjectUtil.isNull(orderInfo)) {
            log.warn("用户等级退款减扣：失败，无法找到对应的订单信息");
            throw new ServiceException("用户等级退款减扣：失败，无法找到对应的订单信息");
        }
        UserLevel userLevel = userLevelMapper.selectByUserId(orderInfo.getCustomUserId());
        if (ObjectUtil.isNull(userLevel)) {
            log.warn("用户等级退款减扣：失败，无法查询到用户等级数据");
            throw new ServiceException("用户等级退款减扣：失败，无法查询到用户等级数据");
        }
        Date now = DateUtils.getNowDate();
        // 先更新用户等级的总积分
        userLevel.setTotalPoints(userLevel.getTotalPoints().subtract(orderInfo.getPayAmount()))
                .setUpdateTime(now);
        // 查询到用户等级配置数据
        List<UserLevelConfig> userLevelConfigs = userLevelConfigMapper.selectUserLevelConfigList(new UserLevelConfig());
        // 如果最高等级直接修改即可
        if (ifMaxLevel(userLevel, userLevelConfigs)) {
            userLevel.setCurrentPoints(userLevel.getCurrentPoints().subtract(orderInfo.getPayAmount()));
            userLevelMapper.updateUserLevel(userLevel);
            log.info("用户等级退款减扣：完成，用户已达到最高等级，直接更新总积分和当前积分即可");
            return;
        }
        // 开始计算用户等级数据
        handleUserLevelData(userLevel, userLevelConfigs);
        userLevelMapper.updateUserLevel(userLevel);
        log.info("用户等级退款减扣：完成");
    }

    /**
     * 处理用户等级计算
     *
     * @param userLevel        用户等级
     * @param userLevelConfigs 等级配置
     */
    private void handleUserLevelData(UserLevel userLevel, List<UserLevelConfig> userLevelConfigs) {
        for (int i = userLevelConfigs.size() - 1; i >= 0; i--) {
            UserLevelConfig level = userLevelConfigs.get(i);
            if (userLevel.getTotalPoints().compareTo(level.getThreshold()) >= 0) {
                userLevel.setLevelConfigId(level.getId())
                        .setCurrentLevel(level.getLevel())
                        .setCurrentPoints(userLevel.getTotalPoints().subtract(level.getThreshold()));
                return;
            }
        }
    }

    /**
     * 当前是否最高等级
     *
     * @param userLevel        用户等级
     * @param userLevelConfigs 等级配置
     * @return 结果
     */
    private Boolean ifMaxLevel(UserLevel userLevel, List<UserLevelConfig> userLevelConfigs) {
        // 最高等级判断
        UserLevelConfig maxLevel = userLevelConfigs.stream().max(Comparator.comparing(UserLevelConfig::getThreshold)).orElse(null);
        if (ObjectUtil.isNotNull(maxLevel)) {
            if (maxLevel.getLevel().equals(userLevel.getCurrentLevel())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}
