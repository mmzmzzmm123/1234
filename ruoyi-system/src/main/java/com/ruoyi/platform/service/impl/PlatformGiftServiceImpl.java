package com.ruoyi.platform.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.constant.RedisKeyConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.PlatformGiftMapper;
import com.ruoyi.platform.domain.PlatformGift;
import com.ruoyi.platform.service.IPlatformGiftService;

/**
 * 平台礼物管理Service业务层处理
 *
 * @author Lam
 * @date 2023-09-12
 */
@Service
@RequiredArgsConstructor
public class PlatformGiftServiceImpl implements IPlatformGiftService {

    private final PlatformGiftMapper platformGiftMapper;
    private final RedisCache redisCache;

    /**
     * 查询平台礼物管理
     *
     * @param id 平台礼物管理主键
     * @return 平台礼物管理
     */
    @Override
    public PlatformGift selectPlatformGiftById(Long id) {
        return platformGiftMapper.selectPlatformGiftById(id);
    }

    /**
     * 查询平台礼物管理列表
     *
     * @param platformGift 平台礼物管理
     * @return 平台礼物管理
     */
    @Override
    public List<PlatformGift> selectPlatformGiftList(PlatformGift platformGift) {
        return platformGiftMapper.selectPlatformGiftList(platformGift);
    }

    /**
     * 新增平台礼物管理
     *
     * @param platformGift 平台礼物管理
     * @return 结果
     */
    @Override
    public int insertPlatformGift(PlatformGift platformGift) {
        String loginUser = SecurityUtils.getUsername();
        Date now = DateUtils.getNowDate();
        platformGift.setCreateTime(now)
                .setUpdateTime(now)
                .setUpdateBy(loginUser)
                .setCreateBy(loginUser);
        redisCache.deleteObject(RedisKeyConstants.PLATFORM_GIFT);
        return platformGiftMapper.insertPlatformGift(platformGift);
    }

    /**
     * 修改平台礼物管理
     *
     * @param platformGift 平台礼物管理
     * @return 结果
     */
    @Override
    public int updatePlatformGift(PlatformGift platformGift) {
        platformGift.setUpdateBy(SecurityUtils.getUsername())
                .setUpdateTime(DateUtils.getNowDate());
        redisCache.deleteObject(RedisKeyConstants.PLATFORM_GIFT);
        return platformGiftMapper.updatePlatformGift(platformGift);
    }

    /**
     * 批量删除平台礼物管理
     *
     * @param ids 需要删除的平台礼物管理主键
     * @return 结果
     */
    @Override
    public int deletePlatformGiftByIds(Long[] ids) {
        redisCache.deleteObject(RedisKeyConstants.PLATFORM_GIFT);
        return platformGiftMapper.deletePlatformGiftByIds(ids);
    }

    /**
     * 删除平台礼物管理信息
     *
     * @param id 平台礼物管理主键
     * @return 结果
     */
    @Override
    public int deletePlatformGiftById(Long id) {
        redisCache.deleteObject(RedisKeyConstants.PLATFORM_GIFT);
        return platformGiftMapper.deletePlatformGiftById(id);
    }
}
