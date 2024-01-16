package com.onethinker.user.service.impl;

import com.google.common.collect.Maps;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.CacheEnum;
import com.ruoyi.common.enums.SysConfigKeyEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.mapper.PlatformUserDetailMapper;
import com.onethinker.user.mapper.PlatformUserMapper;
import com.onethinker.user.service.IPlatformUserDetailService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 平台用户详情信息Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
@Service
@Log4j2
public class PlatformUserDetailServiceImpl implements IPlatformUserDetailService {
    @Resource
    private PlatformUserDetailMapper platformUserDetailMapper;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private RedisCache redisCache;

    @Resource
    private PlatformUserMapper platformUserMapper;

    /**
     * 修改平台用户详情信息
     *
     * @param platformUserDetail 平台用户详情信息
     * @return 结果
     */
    @Override
    public int updatePlatformUserDetail(PlatformUserDetail platformUserDetail) {
        platformUserDetail.setUpdateTime(DateUtils.getNowDate());
        return platformUserDetailMapper.updatePlatformUserDetail(platformUserDetail);
    }

    @Override
    public PlatformUserDetail saveEntryUserDetailByAccount(PlatformUser platformUser) {
        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setAvatarUrl(platformUser.getAvatarUrl());
        platformUserDetail.setEnabled(PlatformUserDetail.STATE_TYPE_ENABLED);
        platformUserDetail.setType(PlatformUserReqDTO.SOURCE_TYPE_ACCOUNT);
        platformUserDetail.setPhone(platformUser.getPhone());
        platformUserDetail.setUsername(platformUser.getPhone());
        platformUserDetail.setNickName(platformUser.getNickName());
        platformUserDetail.setPassword(sysConfigService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_PASSWORD));
        platformUserDetail.setDataId(platformUser.getPhone());
        platformUserDetail.setWeight(System.currentTimeMillis());
        platformUserDetail.setCreateTime(new Date());
        platformUserDetailMapper.insertPlatformUserDetail(platformUserDetail);
        return platformUserDetail;
    }

    @Override
    public PlatformUserDetail saveEntryUserDetailByWx(PlatformUser platformUser, PlatformUserReqDTO reqDTO) {
        String redisKey = CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode() + reqDTO.getOpenId();

        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setDataId(reqDTO.getOpenId());
        platformUserDetail.setType(PlatformUserReqDTO.SOURCE_TYPE_WX);
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
        if (ObjectUtils.isEmpty(platformUserDetails) || platformUserDetails.isEmpty()) {
            // 保存
            platformUserDetail.setAvatarUrl(ObjectUtils.isEmpty(reqDTO.getAvatarUrl()) ? platformUser.getAvatarUrl() : reqDTO.getAvatarUrl());
            platformUserDetail.setNickName(ObjectUtils.isEmpty(reqDTO.getNickName()) ? platformUser.getNickName() : reqDTO.getNickName());
            platformUserDetail.setEnabled(PlatformUserDetail.STATE_TYPE_ENABLED);
            platformUserDetail.setType(PlatformUserReqDTO.SOURCE_TYPE_WX);
            platformUserDetail.setPhone(platformUser.getPhone());
            platformUserDetail.setDataId(reqDTO.getOpenId());
            platformUserDetail.setWeight(System.currentTimeMillis());
            platformUserDetail.setCreateTime(new Date());
            platformUserDetail.setLoginEndTime(System.currentTimeMillis());
            platformUserDetailMapper.insertPlatformUserDetail(platformUserDetail);
        } else {
            // 更新手机号
            platformUserDetail = platformUserDetails.get(0);
            if (!ObjectUtils.isEmpty(platformUserDetail.getPhone()) && !platformUserDetail.getPhone().equals(platformUser.getPhone())) {
                throw new RuntimeException("该微信号已绑定手机号");
            }
            platformUserDetail.setPhone(platformUser.getPhone());
            platformUserDetail.setUpdateTime(new Date());
            platformUserDetailMapper.updatePlatformUserDetail(platformUserDetail);
        }
        redisCache.setCacheObject(redisKey,platformUserDetail,1,TimeUnit.DAYS);
        return platformUserDetail;
    }

    @Override
    public void existsWxRegister(String openId, String phone) {
        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setPhone(phone);
        platformUserDetail.setType(PlatformUserReqDTO.SOURCE_TYPE_WX);
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
        if (ObjectUtils.isEmpty(platformUserDetails) || platformUserDetails.isEmpty()) {
            return;
        }
        platformUserDetail = platformUserDetails.get(0);
        if (!platformUserDetail.getDataId().equals(openId)) {
            throw new RuntimeException("手机号已被其他微信绑定");
        }

    }

    @Override
    public PlatformUserDetail selectPlatformUserDetailByDataId(String dataId) {
        String redisKey = CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode() + dataId;

        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheObject(redisKey);
        }
        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setDataId(dataId);
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
        if (ObjectUtils.isEmpty(platformUserDetails) || platformUserDetails.isEmpty()) {
            return null;
        }
        redisCache.setCacheObject(redisKey,platformUserDetails.get(0),1, TimeUnit.DAYS);
        return platformUserDetails.get(0);
    }

    @Override
    public PlatformUserDetail queryLoginUserInfo() {
        String dataId = SecurityUtils.getLoginUser().getDataId();
        PlatformUserDetail platformUserDetail = selectPlatformUserDetailByDataId(dataId);
        return platformUserDetail;
    }

    @Override
    public PlatformUser queryUserByPhone(String phone) {
        String redisKey = CacheEnum.QUERY_USER_PHONE_KEY.getCode() + phone;

        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheObject(redisKey);
        }
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPhone(phone);
        List<PlatformUser> platformUsers = platformUserMapper.selectPlatformUserList(platformUser);
        if (ObjectUtils.isEmpty(platformUsers) || platformUsers.isEmpty()) {
            return null;
        }
        redisCache.setCacheObject(redisKey,platformUsers.get(0),1, TimeUnit.DAYS);
        return platformUsers.get(0);
    }

    @Override
    public Map<Long, String> selectUserPhoneByUserIds(List<Long> puUserIds) {
        Assert.isTrue(!ObjectUtils.isEmpty(puUserIds) && !puUserIds.isEmpty(),"不能查询全用户信息");
        List<PlatformUser> platformUsers = platformUserMapper.selectPlatformUserByIds(puUserIds);
        if (ObjectUtils.isEmpty(platformUsers)) {
            return Maps.newHashMap();
        }
        return platformUsers.stream().collect(Collectors.toMap(PlatformUser::getId, PlatformUser::getPhone));
    }
}
