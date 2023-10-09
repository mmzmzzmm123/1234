package com.ruoyi.api.platform.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.platform.model.vo.ApiGetBannerVo;
import com.ruoyi.api.platform.model.vo.ApiGiftVo;
import com.ruoyi.api.platform.model.vo.ApiRechargeConfigVo;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.constant.RedisKeyConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.SysShowHideEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.LongUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.platform.domain.PlatformBanner;
import com.ruoyi.platform.domain.PlatformGift;
import com.ruoyi.platform.domain.PlatformRechargeConfig;
import com.ruoyi.platform.domain.PlatformTextContent;
import com.ruoyi.platform.mapper.PlatformBannerMapper;
import com.ruoyi.platform.mapper.PlatformGiftMapper;
import com.ruoyi.platform.mapper.PlatformRechargeConfigMapper;
import com.ruoyi.platform.mapper.PlatformTextContentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author LAM
 * @date 2023/9/24 21:49
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiPlatformService {

    private final PlatformTextContentMapper textContentMapper;
    private final PlatformGiftMapper giftMapper;
    private final PlatformRechargeConfigMapper rechargeConfigMapper;
    private final PlatformBannerMapper bannerMapper;
    private final RedisCache redisCache;

    /**
     * 根据类型获取平台文本内容
     *
     * @param type 类型
     * @return 结果
     */
    public String selectTextContentByType(String type) {
        log.info("根据类型获取平台文本内容：开始，参数：{}", type);
        String vo = new String("亲爱的，暂未找到记录哟");
        String key = RedisKeyConstants.PLATFORM_TEXT_CONTENT + type;
        // 存在缓存数据
        if (redisCache.hasKey(key)) {
            vo = redisCache.getCacheObject(key);
            log.info("根据类型获取平台文本内容：完成，存在缓存，直接返回");
            return vo;
        }
        // 查询数据库
        PlatformTextContent select = new PlatformTextContent();
        select.setType(type);
        List<PlatformTextContent> platformTextContents = textContentMapper.selectPlatformTextContentList(select);
        if (ObjectUtil.isNotNull(platformTextContents)) {
            vo = platformTextContents.get(0).getContent();
            redisCache.setCacheObject(key, vo, LongUtils.generateRandomNumber(1, 10).intValue(), TimeUnit.HOURS);
        }
        log.info("根据类型获取平台文本内容：完成，返回数据：{}", vo);
        return vo;
    }

    /**
     * 查询礼物列表
     *
     * @return 结果
     */
    public List<ApiGiftVo> selectGiftList() {
        log.info("查询礼物列表：开始");
        List<ApiGiftVo> voList = new ArrayList<>();
        String key = RedisKeyConstants.PLATFORM_GIFT;
        if (redisCache.hasKey(key)) {
            voList = redisCache.getCacheList(key);
            if (ObjectUtil.isNotEmpty(voList)) {
                log.info("查询礼物列表：完成，存在缓存，返回数据：{}", voList);
                return voList;
            }
        }
        PlatformGift select = new PlatformGift();
        select.setState(SysShowHideEnums.SHOW.getCode());
        List<PlatformGift> platformGifts = giftMapper.selectPlatformGiftList(select);
        for (PlatformGift item : platformGifts) {
            ApiGiftVo vo = new ApiGiftVo();
            BeanUtils.copyBeanProp(vo, item);
            voList.add(vo);
        }
        redisCache.setCacheList(key, voList);
        redisCache.expire(key, LongUtils.generateRandomNumber(10, 20), TimeUnit.HOURS);
        log.info("查询礼物列表：完成，返回数据：{}", voList);
        return voList;
    }

    /**
     * 查询充值配置
     *
     * @return 结果
     */
    public List<ApiRechargeConfigVo> selectRechargeConfig() {
        log.info("查询充值配置：开始");
        List<ApiRechargeConfigVo> voList = new ArrayList<>();
        String key = RedisKeyConstants.PLATFORM_RECHARGE_CONFIG;
        if (redisCache.hasKey(key)) {
            log.info("查询充值配置：描述，存在缓存");
            voList = redisCache.getCacheList(key);
        }
        // 如果缓存空，那就直接查询db
        if (ObjectUtil.isEmpty(voList)) {
            List<PlatformRechargeConfig> platformRechargeConfigs = rechargeConfigMapper.selectPlatformRechargeConfigList(new PlatformRechargeConfig());
            if (ObjectUtil.isNotEmpty(platformRechargeConfigs)) {
                for (PlatformRechargeConfig item : platformRechargeConfigs) {
                    ApiRechargeConfigVo vo = new ApiRechargeConfigVo();
                    BeanUtils.copyBeanProp(vo, item);
                    voList.add(vo);
                }
                redisCache.setCacheList(key, voList);
                redisCache.expire(key, LongUtils.generateRandomNumber(1, 24), TimeUnit.HOURS);
            }
        }
        log.info("查询充值配置：完成，返回数据：{}", voList);
        return voList;
    }

    /**
     * 根据投放位置获取到相应广告图数据
     *
     * @param position 位置标识
     * @return 结果
     */
    public List<ApiGetBannerVo> getBannerByPosition(String position) {
        log.info("根据投放位置获取到相应广告图数据：开始，参数：{}", position);
        if (StringUtils.isBlank(position)) {
            log.warn("根据投放位置获取到相应广告图数据：失败，参数不能为空");
            throw new ServiceException("根据投放位置获取到相应广告图数据：失败，参数不能为空", HttpStatus.ERROR);
        }
        List<ApiGetBannerVo> voList = new ArrayList<>();
        String redisKey = RedisKeyConstants.APP_BANNER_DATA + position;
        // 从缓存获取数据
        if (redisCache.hasKey(redisKey)) {
            log.info("根据投放位置获取到相应广告图数据：存在缓存");
            voList = redisCache.getCacheList(redisKey);
        }
        // 从db获取数据
        if (ObjectUtil.isEmpty(voList)) {
            PlatformBanner select = new PlatformBanner();
            select.setPosition(position);
            List<PlatformBanner> platformBanners = bannerMapper.selectPlatformBannerList(select);
            for (PlatformBanner item : platformBanners) {
                ApiGetBannerVo vo = new ApiGetBannerVo();
                BeanUtils.copyBeanProp(vo, item);
                voList.add(vo);
            }
            redisCache.setCacheList(redisKey, voList);
            redisCache.expire(redisKey, LongUtils.generateRandomNumber(1,24), TimeUnit.HOURS);
        }
        log.info("根据投放位置获取到相应广告图数据：成功，返回数据：{}", voList);
        return voList;
    }
}
