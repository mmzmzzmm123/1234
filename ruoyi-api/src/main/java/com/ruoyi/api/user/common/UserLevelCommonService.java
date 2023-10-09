package com.ruoyi.api.user.common;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.user.domain.UserLevel;
import com.ruoyi.user.domain.UserLevelConfig;
import com.ruoyi.user.mapper.UserLevelConfigMapper;
import com.ruoyi.user.mapper.UserLevelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/6 11:54
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserLevelCommonService {

    private final UserLevelMapper userLevelMapper;
    private final UserLevelConfigMapper userLevelConfigMapper;

    /**
     * 用户等级计算
     *
     * @param userId 用户标识
     * @param amount 金额
     * */
    @Transactional(rollbackFor = Exception.class)
    public void levelCount(Long userId, BigDecimal amount){
        log.info("用户等级计算：开始，参数：{}，{}", userId, amount);
        UserLevel userLevel = userLevelMapper.selectByUserId(userId);
        if (ObjectUtil.isNull(userLevel)){
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
        UserLevelConfig maxLevel = userLevelConfigs.stream().max(Comparator.comparing(UserLevelConfig::getThreshold)).orElse(null);
        if (ObjectUtil.isNotNull(maxLevel)){
            if (maxLevel.getLevel().equals(userLevel.getCurrentLevel())){
                userLevel.setCurrentPoints(userLevel.getCurrentPoints().add(amount));
                userLevelMapper.updateUserLevel(userLevel);
                log.info("用户等级计算：完成，用户已达到最高等级，直接更新总积分和当前积分即可");
                return;
            }
        }

        // 开始计算用户等级数据
        // 用户的下一个级别
        UserLevelConfig nextLevelConfig = userLevelConfigs.stream().filter(item -> item.getLevel().equals((userLevel.getCurrentLevel() + 1))).findFirst().orElse(null);
        if (ObjectUtil.isNotNull(nextLevelConfig)){
            // 不升级，总积分数未超越下一个级别阈值
            if (nextLevelConfig.getThreshold().compareTo(userLevel.getTotalPoints()) > 0){
                userLevel.setCurrentPoints(userLevel.getCurrentPoints().add(amount));
            }else{
                // 升级处理
                userLevel.setCurrentLevel(nextLevelConfig.getLevel())
                        .setLevelConfigId(nextLevelConfig.getId())
                        .setCurrentPoints(nextLevelConfig.getThreshold().subtract(userLevel.getTotalPoints()));
            }
        }
        userLevelMapper.updateUserLevel(userLevel);
        log.info("用户等级计算：完成");
    }
}
