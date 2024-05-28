package com.onethinker.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.onethinker.common.constant.SystemConst;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.enums.IntegralTypeEnum;
import com.onethinker.common.enums.SysStatusTypeEnum;
import com.onethinker.common.utils.SecurityUtils;
import com.onethinker.framework.web.domain.server.Sys;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.domain.PlatformUserIntegral;
import com.onethinker.user.domain.PlatformUserIntegralHistory;
import com.onethinker.user.event.ActionUserIntegralSuccessEvent;
import com.onethinker.user.service.IPlatformUserIntegralService;
import com.onethinker.user.service.IPlatformUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author yangyouqi
 * @date 2024/5/28
 */
@Service
@Log4j2
public class PlatformUserIntegralServiceImpl implements IPlatformUserIntegralService {
    @Autowired
    private IPlatformUserService platformUserService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ApplicationContext applicationContext;

    private final String redisKey = CacheEnum.QUERY_USER_INTEGRAL_KEY.getCode();

    @Override
    public boolean addIntegral(String dataId, Long integral, IntegralTypeEnum integralType) {
        return addIntegral(dataId, integral, SystemConst.SYSTEM, integralType);
    }

    @Override
    public boolean addIntegral(String dataId, Long integral, String activityId, IntegralTypeEnum integralType) {
        if (!IntegralTypeEnum.RECHARGE.equals(integralType) && !IntegralTypeEnum.ACTIVITY_REWARD.equals(integralType)) {
            throw new RuntimeException("办理类型有误，无法添加用户积分" + integralType.getMsg());
        }
        PlatformUser platformUser = platformUserService.selectPlatformUserDetailByDataId(dataId);
        Assert.isTrue(Objects.nonNull(platformUser), "用户信息异常");
        Assert.isTrue(integral > 0, "添加积分无效");
        // 查询当前用户是否存在积分信息
        PlatformUserIntegral userIntegral = getIntegral(dataId, activityId);

        // 查redis看是否存在更新中的数据
        String key = redisKey + "addIntegral:" + dataId + ":" + integral;
        if (redisCache.hasKey(key)) {
            throw new RuntimeException("当前存在相同的积分数据，请稍后再添加吧");
        }
        redisCache.setCacheObject(key, LocalDateTime.now(), 1, TimeUnit.HOURS);
        try {
            if (Objects.isNull(userIntegral)) {
                // 创建积分信息
                userIntegral = new PlatformUserIntegral();
                userIntegral.setDataId(dataId);
                userIntegral.setBatchNo(activityId);
                userIntegral.setCreateTime(new Date());
                userIntegral.setCreateBy(SecurityUtils.getLoginUser().getUsername());
                userIntegral.setEnabled(SysStatusTypeEnum.STATUS_TYPE_ENABLED.getCode());
                userIntegral.setTotalIntegral(Long.valueOf(integral));
                userIntegral.setResidualIntegral(Long.valueOf(integral));
                // 调用保存接口
            } else {
                userIntegral.setUpdateTime(new Date());
                userIntegral.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
                userIntegral.setTotalIntegral(userIntegral.getTotalIntegral() + integral);
                userIntegral.setResidualIntegral(userIntegral.getResidualIntegral() + integral);
                // 调用更新接口
            }
            // 保存历史记录接口
            applicationContext.publishEvent(new ActionUserIntegralSuccessEvent(userIntegral, integralType, integral));
        } catch (Exception e) {
            log.error("添加积分有误", e);
            throw e;
        } finally {
            redisCache.deleteObject(key);
        }
        // 删除当前用户相关积分缓存信息
        redisCache.deleteObject(redisKey + activityId + ":" + dataId);
        return true;
    }

    @EventListener
    @Async
    public void actionUserIntegralSuccessEvent(ActionUserIntegralSuccessEvent actionUserIntegralSuccessEvent) {
        PlatformUserIntegral platformUserIntegral = (PlatformUserIntegral) actionUserIntegralSuccessEvent.getSource();
        Long integral = actionUserIntegralSuccessEvent.getIntegral();
        Integer integralType = actionUserIntegralSuccessEvent.getIntegralType().getCode();
        String key = redisKey + "save_integral:" + platformUserIntegral.getId() + ":" + integralType + ":" + integral;
        if (redisCache.hasKey(key)) {
            return;
        }
        redisCache.setCacheObject(key, LocalDateTime.now(), 1, TimeUnit.HOURS);
        try {
            PlatformUserIntegralHistory platformUserIntegralHistory = new PlatformUserIntegralHistory();
            platformUserIntegralHistory.setCreateTime(new Date());
            platformUserIntegralHistory.setCreateBy(SecurityUtils.getLoginUser().getUsername());
            platformUserIntegralHistory.setUpdateTime(new Date());
            platformUserIntegralHistory.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
            platformUserIntegralHistory.setIntegral(integral);
            platformUserIntegralHistory.setDataId(platformUserIntegral.getDataId());
            platformUserIntegralHistory.setBatchNo(platformUserIntegral.getBatchNo());
            platformUserIntegralHistory.setEnabled(SysStatusTypeEnum.STATUS_TYPE_ENABLED.getCode());
            platformUserIntegralHistory.setType(integralType);
            platformUserIntegralHistory.setSysUserId(SecurityUtils.getUserId());
            // 保存接口
        } catch (Exception e) {
            log.error("保存历史接口数据异常", e);
        } finally {
            redisCache.deleteObject(key);
        }

    }

    @Override
    public boolean deductIntegral(String dataId, Long integral, IntegralTypeEnum integralType) {
        return deductIntegral(dataId, integral, SystemConst.SYSTEM, integralType);
    }

    @Override
    public boolean deductIntegral(String dataId, Long integral, String activityId, IntegralTypeEnum integralType) {
        if (!IntegralTypeEnum.ACTIVITY_THRESHOLD_FEE.equals(integralType) && !IntegralTypeEnum.CASH_WITHDRAWAL.equals(integralType)) {
            throw new RuntimeException("类型有误，无法扣除用户积分" + integralType.getMsg());
        }
        PlatformUser platformUser = platformUserService.selectPlatformUserDetailByDataId(dataId);
        Assert.isTrue(Objects.nonNull(platformUser), "用户信息异常");
        Assert.isTrue(integral > 0, "扣除积分无效");
        // 查询当前用户是否存在积分信息
        PlatformUserIntegral userIntegral = getIntegral(dataId, activityId);
        // 查redis看是否存在更新中的数据
        String key = redisKey + "deductIntegral:" + dataId + ":" + integral;
        if (redisCache.hasKey(key))  throw new RuntimeException("当前存在相同的积分数据，请稍后再扣除吧");
        redisCache.setCacheObject(key, LocalDateTime.now(), 1, TimeUnit.HOURS);
        try {
            if (Objects.isNull(userIntegral)) {
                throw new RuntimeException("用户积分不足");
            } else {
                Assert.isTrue(userIntegral.getResidualIntegral() > integral,"用户积分不足");
                userIntegral.setUpdateTime(new Date());
                userIntegral.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
                userIntegral.setResidualIntegral(userIntegral.getResidualIntegral() - integral);
                // 调用更新接口
            }
            // 保存历史记录接口
            applicationContext.publishEvent(new ActionUserIntegralSuccessEvent(userIntegral, integralType, integral));
        } catch (Exception e) {
            log.error("添加积分有误", e);
            throw e;
        } finally {
            redisCache.deleteObject(key);
        }
        return true;
    }

    @Override
    public PlatformUserIntegral getIntegral(String dataId) {
        return getIntegral(dataId, SystemConst.SYSTEM);
    }

    @Override
    public PlatformUserIntegral getIntegral(String dataId, String activityId) {
        String key = redisKey + activityId + ":" + dataId;
        if (redisCache.hasKey(key)) {
            return redisCache.getCacheObject(key);
        }
        LambdaQueryWrapper<PlatformUserIntegral> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformUserIntegral::getDataId, dataId).eq(PlatformUserIntegral::getBatchNo,activityId).eq(PlatformUserIntegral::getEnabled, SysStatusTypeEnum.STATUS_TYPE_ENABLED.getCode());
        // 查询返回
        return null;
    }

    @Override
    public PlatformUserIntegralHistory getIntegralHistory(String dataId) {
        return getIntegralHistory(dataId, SystemConst.SYSTEM);
    }

    @Override
    public PlatformUserIntegralHistory getIntegralHistory(String dataId, String activityId) {
        String key = redisKey + "getIntegralHistory:" + activityId + ":" + dataId;
        if (redisCache.hasKey(key)) {
            return redisCache.getCacheObject(key);
        }
        LambdaQueryWrapper<PlatformUserIntegralHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformUserIntegralHistory::getDataId, dataId).eq(PlatformUserIntegralHistory::getBatchNo,activityId).eq(PlatformUserIntegralHistory::getEnabled, SysStatusTypeEnum.STATUS_TYPE_ENABLED.getCode());
        // 查询返回
        return null;
    }
}
