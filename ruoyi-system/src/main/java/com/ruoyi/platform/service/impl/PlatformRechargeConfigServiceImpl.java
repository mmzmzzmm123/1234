package com.ruoyi.platform.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.constant.RedisKeyConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.PlatformRechargeConfigMapper;
import com.ruoyi.platform.domain.PlatformRechargeConfig;
import com.ruoyi.platform.service.IPlatformRechargeConfigService;

/**
 * 平台充值配置Service业务层处理
 *
 * @author Lam
 * @date 2023-10-04
 */
@Service
@RequiredArgsConstructor
public class PlatformRechargeConfigServiceImpl implements IPlatformRechargeConfigService {

    private final PlatformRechargeConfigMapper platformRechargeConfigMapper;
    private final RedisCache redisCache;

    /**
     * 查询平台充值配置
     *
     * @param id 平台充值配置主键
     * @return 平台充值配置
     */
    @Override
    public PlatformRechargeConfig selectPlatformRechargeConfigById(Long id) {
        return platformRechargeConfigMapper.selectPlatformRechargeConfigById(id);
    }

    /**
     * 查询平台充值配置列表
     *
     * @param platformRechargeConfig 平台充值配置
     * @return 平台充值配置
     */
    @Override
    public List<PlatformRechargeConfig> selectPlatformRechargeConfigList(PlatformRechargeConfig platformRechargeConfig) {
        return platformRechargeConfigMapper.selectPlatformRechargeConfigList(platformRechargeConfig);
    }

    /**
     * 新增平台充值配置
     *
     * @param platformRechargeConfig 平台充值配置
     * @return 结果
     */
    @Override
    public int insertPlatformRechargeConfig(PlatformRechargeConfig platformRechargeConfig) {
        redisCache.deleteObject(RedisKeyConstants.PLATFORM_RECHARGE_CONFIG);
        Date now = DateUtils.getNowDate();
        String loginName = SecurityUtils.getUsername();
        platformRechargeConfig.setCreateTime(now)
                .setUpdateTime(now)
                .setCreateBy(loginName)
                .setUpdateBy(loginName);
        return platformRechargeConfigMapper.insertPlatformRechargeConfig(platformRechargeConfig);
    }

    /**
     * 修改平台充值配置
     *
     * @param platformRechargeConfig 平台充值配置
     * @return 结果
     */
    @Override
    public int updatePlatformRechargeConfig(PlatformRechargeConfig platformRechargeConfig) {
        redisCache.deleteObject(RedisKeyConstants.PLATFORM_RECHARGE_CONFIG);
        platformRechargeConfig.setUpdateBy(SecurityUtils.getUsername()).setUpdateTime(DateUtils.getNowDate());
        return platformRechargeConfigMapper.updatePlatformRechargeConfig(platformRechargeConfig);
    }

    /**
     * 批量删除平台充值配置
     *
     * @param ids 需要删除的平台充值配置主键
     * @return 结果
     */
    @Override
    public int deletePlatformRechargeConfigByIds(Long[] ids) {
        redisCache.deleteObject(RedisKeyConstants.PLATFORM_RECHARGE_CONFIG);
        return platformRechargeConfigMapper.deletePlatformRechargeConfigByIds(ids);
    }

    /**
     * 删除平台充值配置信息
     *
     * @param id 平台充值配置主键
     * @return 结果
     */
    @Override
    public int deletePlatformRechargeConfigById(Long id) {
        redisCache.deleteObject(RedisKeyConstants.PLATFORM_RECHARGE_CONFIG);
        return platformRechargeConfigMapper.deletePlatformRechargeConfigById(id);
    }
}
