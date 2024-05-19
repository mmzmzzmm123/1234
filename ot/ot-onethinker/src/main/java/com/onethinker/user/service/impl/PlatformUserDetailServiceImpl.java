package com.onethinker.user.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.mapper.PlatformUserDetailMapper;
import com.onethinker.user.mapper.PlatformUserMapper;
import com.onethinker.user.service.IPlatformUserDetailService;
import com.onethinker.common.constant.BkConstants;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.enums.SysConfigKeyEnum;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.common.utils.SecurityUtils;
import com.onethinker.common.utils.StringUtils;
import com.onethinker.system.service.ISysConfigService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
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
public class PlatformUserDetailServiceImpl extends ServiceImpl<PlatformUserDetailMapper, PlatformUserDetail> implements IPlatformUserDetailService {
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
        int i = platformUserDetailMapper.updatePlatformUserDetail(platformUserDetail);
        // 删除用户信息
        redisCache.deleteObject(CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY + platformUserDetail.getDataId());
        return i;
    }

    @Override
    public PlatformUserDetail saveEntryUserDetailByAccount(PlatformUser platformUser, PlatformUserReqDTO reqDTO) {
        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setAvatarUrl(platformUser.getAvatarUrl());
        platformUserDetail.setEnabled(PlatformUserDetail.STATE_TYPE_ENABLED);
        platformUserDetail.setType(PlatformUserReqDTO.SOURCE_TYPE_ACCOUNT);
        if (StringUtils.isNotEmpty(reqDTO.getUserName())) {
            platformUserDetail.setUsername(reqDTO.getUserName());
        } else {
            platformUserDetail.setUsername(platformUser.getDataId());
        }
        String password = "";
        if (StringUtils.isNotEmpty(reqDTO.getPassword())) {
            password = reqDTO.getPassword();
        } else {
            password = sysConfigService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_PASSWORD);
        }
        platformUserDetail.setPassword(SecureUtil.aes(BkConstants.CRYPOTJS_KEY.getBytes(StandardCharsets.UTF_8)).encryptBase64(password));
//        platformUserDetail.setPassword(password);
        platformUserDetail.setNickName(platformUser.getNickName());
        platformUserDetail.setDataId(platformUser.getDataId());
        platformUserDetail.setWeight(System.currentTimeMillis());
        platformUserDetail.setCreateTime(new Date());
        platformUserDetailMapper.insertPlatformUserDetail(platformUserDetail);
        // 清理下缓存信息
        redisCache.deleteObject(CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode() + platformUser.getDataId());
        return platformUserDetail;
    }

    @Override
    public PlatformUserDetail saveEntryUserDetailByWx(PlatformUser platformUser, PlatformUserReqDTO reqDTO) {
        String redisKey = CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode() + reqDTO.getDataId();

        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setDataId(reqDTO.getDataId());
        platformUserDetail.setType(PlatformUserReqDTO.SOURCE_TYPE_WX);
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
        if (ObjectUtils.isEmpty(platformUserDetails) || platformUserDetails.isEmpty()) {
            // 保存
            platformUserDetail.setAvatarUrl(ObjectUtils.isEmpty(reqDTO.getAvatarUrl()) ? platformUser.getAvatarUrl() : reqDTO.getAvatarUrl());
            platformUserDetail.setNickName(ObjectUtils.isEmpty(reqDTO.getNickName()) ? platformUser.getNickName() : reqDTO.getNickName());
            platformUserDetail.setEnabled(PlatformUserDetail.STATE_TYPE_ENABLED);
            platformUserDetail.setType(PlatformUserReqDTO.SOURCE_TYPE_WX);
            platformUserDetail.setDataId(reqDTO.getDataId());
            platformUserDetail.setWeight(System.currentTimeMillis());
            platformUserDetail.setCreateTime(new Date());
            platformUserDetail.setLoginEndTime(System.currentTimeMillis());
            platformUserDetailMapper.insertPlatformUserDetail(platformUserDetail);
        } else {
            // 更新手机号
            platformUserDetail = platformUserDetails.get(0);
            if (!ObjectUtils.isEmpty(platformUserDetail.getDataId()) && !platformUserDetail.getDataId().equals(platformUser.getDataId())) {
                throw new RuntimeException("该微信号已绑定手机号");
            }
            platformUserDetail.setDataId(platformUser.getDataId());
            platformUserDetail.setUpdateTime(new Date());
            platformUserDetailMapper.updatePlatformUserDetail(platformUserDetail);
        }
        redisCache.setCacheObject(redisKey, platformUserDetail, 1, TimeUnit.DAYS);
        return platformUserDetail;
    }

    @Override
    public void existsWxRegister(String openId, String phone) {
        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setDataId(phone);
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
            return JSON.parseObject(redisCache.getCacheObject(redisKey).toString(), PlatformUserDetail.class);
        }
        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setDataId(dataId);
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
        if (ObjectUtils.isEmpty(platformUserDetails) || platformUserDetails.isEmpty()) {
            // 如果是空的情况下看看是否是账号信息保存
            platformUserDetail = new PlatformUserDetail();
            platformUserDetail.setUsername(dataId);
            platformUserDetails = platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
            if (ObjectUtils.isEmpty(platformUserDetails) || platformUserDetails.isEmpty()) {
                return null;
            }
        }
        redisCache.setCacheObject(redisKey, platformUserDetails.get(0), 1, TimeUnit.DAYS);
        return platformUserDetails.get(0);
    }

    @Override
    public PlatformUserDetail queryLoginUserInfo() {
        String dataId = SecurityUtils.getLoginUser().getDataId();
        PlatformUserDetail platformUserDetail = selectPlatformUserDetailByDataId(dataId);
        return platformUserDetail;
    }

    @Override
    public Map<String, String> selectUserPhoneByUserIds(List<Long> puUserIds) {
        String redisKey = CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode() + String.join(",", puUserIds.toString());
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheMap(redisKey);
        }
        Assert.isTrue(!ObjectUtils.isEmpty(puUserIds) && !puUserIds.isEmpty(), "不能查询全用户信息");
        List<PlatformUser> platformUsers = platformUserMapper.selectPlatformUserByIds(puUserIds);
        if (ObjectUtils.isEmpty(platformUsers)) {
            return Maps.newHashMap();
        }
        Map<String, String> result = platformUsers.stream().collect(Collectors.toMap(e -> e.getId().toString(), PlatformUser::getDataId));
        redisCache.setCacheMap(redisKey, result);
        redisCache.expire(redisKey, 1, TimeUnit.DAYS);
        return result;
    }

    @Override
    public PlatformUserDetail getPlatFormUserDetailByUserId(Long userId) {
        String redisKey = CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode() + userId;
        if (redisCache.hasKey(redisKey)) {
            return JSON.parseObject(redisCache.getCacheObject(redisKey).toString(), PlatformUserDetail.class);
        }
        PlatformUserDetail platformUserDetail = platformUserDetailMapper.selectPlatformUserDetailById(userId);
        Assert.isTrue(!ObjectUtils.isEmpty(platformUserDetail), "用户信息不存在");
        redisCache.setCacheObject(redisKey, platformUserDetail, 1, TimeUnit.DAYS);
        return platformUserDetail;
    }

    @Override
    public List<PlatformUserDetail> queryStatus(Integer enabled) {
        String redisKey = CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode() + "enabled_" + enabled;
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheList(redisKey);
        }
        LambdaQueryWrapper<PlatformUserDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformUserDetail::getEnabled, enabled);
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectList(queryWrapper);
        if (platformUserDetails.isEmpty()) {
            return Lists.newArrayList();
        }
        redisCache.setCacheList(redisKey, platformUserDetails);
        redisCache.expire(redisKey, 1, TimeUnit.DAYS);
        return platformUserDetails;
    }

    @Override
    public List<PlatformUserDetail> queryAllUserByType(String sourceTypeWx) {
        LambdaQueryWrapper<PlatformUserDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformUserDetail::getType, sourceTypeWx);
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectList(queryWrapper);
        return platformUserDetails;
    }
}
