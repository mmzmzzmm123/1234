package com.ruoyi.platform.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.constant.RedisKeyConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.PlatformBannerMapper;
import com.ruoyi.platform.domain.PlatformBanner;
import com.ruoyi.platform.service.IPlatformBannerService;

/**
 * 平台广告图Service业务层处理
 *
 * @author Lam
 * @date 2023-09-13
 */
@Service
@RequiredArgsConstructor
public class PlatformBannerServiceImpl implements IPlatformBannerService {

    private final PlatformBannerMapper platformBannerMapper;
    private final RedisCache redisCache;

    /**
     * 查询平台广告图
     *
     * @param id 平台广告图主键
     * @return 平台广告图
     */
    @Override
    public PlatformBanner selectPlatformBannerById(Long id) {
        return platformBannerMapper.selectPlatformBannerById(id);
    }

    /**
     * 查询平台广告图列表
     *
     * @param platformBanner 平台广告图
     * @return 平台广告图
     */
    @Override
    public List<PlatformBanner> selectPlatformBannerList(PlatformBanner platformBanner) {
        return platformBannerMapper.selectPlatformBannerList(platformBanner);
    }

    /**
     * 新增平台广告图
     *
     * @param platformBanner 平台广告图
     * @return 结果
     */
    @Override
    public int insertPlatformBanner(PlatformBanner platformBanner) {
        if (StringUtils.isNotBlank(platformBanner.getPosition())){
            redisCache.deleteObject(RedisKeyConstants.APP_BANNER_DATA+platformBanner.getPosition());
        }
        String loginUser = SecurityUtils.getUsername();
        Date now = DateUtils.getNowDate();
        platformBanner.setCreateTime(now)
                .setUpdateTime(now)
                .setUpdateBy(loginUser)
                .setCreateBy(loginUser);
        return platformBannerMapper.insertPlatformBanner(platformBanner);
    }

    /**
     * 修改平台广告图
     *
     * @param platformBanner 平台广告图
     * @return 结果
     */
    @Override
    public int updatePlatformBanner(PlatformBanner platformBanner) {
        if (StringUtils.isNotBlank(platformBanner.getPosition())){
            redisCache.deleteObject(RedisKeyConstants.APP_BANNER_DATA+platformBanner.getPosition());
        }
        platformBanner.setUpdateBy(SecurityUtils.getUsername());
        platformBanner.setUpdateTime(DateUtils.getNowDate());
        return platformBannerMapper.updatePlatformBanner(platformBanner);
    }

    /**
     * 批量删除平台广告图
     *
     * @param ids 需要删除的平台广告图主键
     * @return 结果
     */
    @Override
    public int deletePlatformBannerByIds(Long[] ids) {
        redisCache.likeDelObject(RedisKeyConstants.APP_BANNER_DATA+"*");
        return platformBannerMapper.deletePlatformBannerByIds(ids);
    }

    /**
     * 删除平台广告图信息
     *
     * @param id 平台广告图主键
     * @return 结果
     */
    @Override
    public int deletePlatformBannerById(Long id) {
        redisCache.likeDelObject(RedisKeyConstants.APP_BANNER_DATA+"*");
        return platformBannerMapper.deletePlatformBannerById(id);
    }
}
