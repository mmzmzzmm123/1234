package com.onethinker.user.service;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.onethinker.common.constant.BkConstants;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.*;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.common.utils.SecurityUtils;
import com.onethinker.common.utils.StringUtils;
import com.onethinker.common.utils.Tools;
import com.onethinker.framework.web.service.SysLoginService;
import com.onethinker.mail.service.IMailService;
import com.onethinker.system.service.ISysConfigService;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.mapper.PlatformUserDetailMapper;
import com.onethinker.user.mapper.PlatformUserMapper;
import com.onethinker.user.platform.UserStorage;
import com.onethinker.wechat.service.IMinWechatService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yangyouqi
 * @date 2024/5/19
 * 用于处理用户平台数据，对接多个第三方用户信息
 */
@Slf4j
@Data
@Service
public class IPlatformUserService {

    @Autowired
    private IMailService mailService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private PlatformUserDetailMapper platformUserDetailMapper;

    @Autowired
    private PlatformUserMapper platformUserMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private IMinWechatService wechatService;

    @Autowired
    private SysLoginService sysLoginService;


    private final String REDIS_KEY = CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode();

    /**
     * 获取对应用户平台
     */
    public <T extends UserStorage> T getUserStorage(PlatformUserTypeEnum userTypeEnum) {
        try {
            Assert.isTrue(!ObjectUtils.isEmpty(userTypeEnum),"userTypeEnum is null ");
            Class<?> clazz = Class.forName(userTypeEnum.getInterfaceClass());
            Constructor<?> constructor = clazz.getConstructor(IMinWechatService.class,IPlatformUserService.class,SysLoginService.class,ISysConfigService.class, RedisCache.class);
            constructor.setAccessible(true);
            UserStorage instance = (UserStorage) constructor.newInstance(wechatService,this,sysLoginService, configService,redisCache);
            return Tools.cast(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新平台用户信息
     * @param platformUserDetail 用户对象
     * @return 更新值
     */
    public int updatePlatformUserDetail(PlatformUserDetail platformUserDetail) {
        platformUserDetail.setUpdateTime(DateUtils.getNowDate());
        // 删除用户信息
        redisCache.deleteObject(REDIS_KEY + platformUserDetail.getDataId());
        return platformUserDetailMapper.updatePlatformUserDetail(platformUserDetail);
    }

    /**
     * 获取用户信息
     *
     * @param dataId 登录凭证
     * @return
     */
    public PlatformUserDetail selectPlatformUserDetailByDataId(String dataId) {
        String redisKey = REDIS_KEY + dataId;

        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheObject(redisKey);
        }
        LambdaQueryWrapper<PlatformUserDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformUserDetail::getDataId,dataId);
        PlatformUserDetail platformUserDetail = platformUserDetailMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(platformUserDetail)) {
            return null;
        }
        redisCache.setCacheObject(redisKey, platformUserDetail, 1, TimeUnit.DAYS);
        return platformUserDetail;
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    public PlatformUserDetail queryLoginUserInfo() {
        String dataId = SecurityUtils.getLoginUser().getDataId();
        Assert.isTrue(StringUtils.isNotEmpty(dataId),"dataId is null");
        return selectPlatformUserDetailByDataId(dataId);
    }

    /**
     * 查询对于类型下的所有用户信息
     * @param type 用户类型
     * @return
     */
    public List<PlatformUserDetail> queryAllUserByType(String type) {
        LambdaQueryWrapper<PlatformUserDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformUserDetail::getType, type);
        return platformUserDetailMapper.selectList(queryWrapper);
    }

    public PlatformUserDetail saveEntryUserDetailByWx(PlatformUser platformUser, PlatformUserReqDTO reqDTO) {
        String redisKey = REDIS_KEY + reqDTO.getDataId();

        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setDataId(reqDTO.getDataId());
        platformUserDetail.setType(PlatformUserTypeEnum.WX.name());
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
        if (ObjectUtils.isEmpty(platformUserDetails) || platformUserDetails.isEmpty()) {
            // 保存
            platformUserDetail.setAvatarUrl(ObjectUtils.isEmpty(reqDTO.getAvatarUrl()) ? platformUser.getAvatarUrl() : reqDTO.getAvatarUrl());
            platformUserDetail.setNickName(ObjectUtils.isEmpty(reqDTO.getNickName()) ? platformUser.getNickName() : reqDTO.getNickName());
            platformUserDetail.setEnabled(SysStatusTypeEnum.STATUS_TYPE_ENABLED.getCode());
            platformUserDetail.setType(PlatformUserTypeEnum.WX.name());
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

    public void existsWxRegister(String openId, String phone) {
        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setDataId(phone);
        platformUserDetail.setType(PlatformUserTypeEnum.WEB.name());
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
        if (ObjectUtils.isEmpty(platformUserDetails) || platformUserDetails.isEmpty()) {
            return;
        }
        platformUserDetail = platformUserDetails.get(0);
        if (!platformUserDetail.getDataId().equals(openId)) {
            throw new RuntimeException("手机号已被其他微信绑定");
        }
    }

    public void insertPlatformUser(PlatformUser platformUser) {
        platformUserMapper.insertPlatformUser(platformUser);
    }

    public PlatformUserDetail saveEntryUserDetail(PlatformUserReqDTO reqDTO,PlatformUserTypeEnum platformUserTypeEnum) {
        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setAvatarUrl(configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_AVATAR_URL));
        platformUserDetail.setNickName(configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_NICK_NAME) + System.currentTimeMillis());
        platformUserDetail.setEnabled(SysStatusTypeEnum.STATUS_TYPE_ENABLED.getCode());
        platformUserDetail.setType(platformUserTypeEnum.name());
        platformUserDetail.setPassword(reqDTO.getPassword());
        platformUserDetail.setDataId(reqDTO.getDataId());
        platformUserDetail.setWeight(System.currentTimeMillis());
        platformUserDetail.setCreateTime(new Date());
        platformUserDetailMapper.insertPlatformUserDetail(platformUserDetail);
        // 清理下缓存信息
        redisCache.deleteObject(REDIS_KEY + reqDTO.getDataId());
        return platformUserDetail;
    }
}
