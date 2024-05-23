package com.onethinker.user.service;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.onethinker.common.constant.BkConstants;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.enums.SysConfigKeyEnum;
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
import com.onethinker.common.enums.CodeTypeEnum;
import com.onethinker.common.enums.PlatformUserTypeEnum;
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
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private ISysConfigService sysConfigService;

    @Autowired
    private IMinWechatService wechatService;

    @Autowired
    private SysLoginService sysLoginService;

    /**
     * 获取对应用户平台
     */
    public <T extends UserStorage> T getUserStorage(PlatformUserTypeEnum userTypeEnum) {
        try {
            Assert.isTrue(!ObjectUtils.isEmpty(userTypeEnum),"userTypeEnum is null ");
            Class<?> clazz = Class.forName(userTypeEnum.getInterfaceClass());
            Constructor<?> constructor = clazz.getConstructor(IMinWechatService.class,IPlatformUserService.class,SysLoginService.class,ISysConfigService.class, RedisCache.class);
            constructor.setAccessible(true);
            UserStorage instance = (UserStorage) constructor.newInstance(wechatService,this,sysLoginService,sysConfigService,redisCache);
            return Tools.cast(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void getCodeForForgetPassword(String place, CodeTypeEnum flag) {
        // 随机数
        int code = new Random().nextInt(900000) + 100000;
        if (CodeTypeEnum.PHONE.equals(flag)) {
            log.info(place + "---" + "手机验证码---" + code);
        } else if (CodeTypeEnum.MAIL.equals(flag)) {
            log.info(place + "---" + "邮箱验证码---" + code);
            // 发送邮箱验证码处理
            mailService.sendMailCode(place, code);
        }
        // 保存5分钟
        redisCache.setCacheObject(CacheEnum.CAPTCHA_CODE_KEY.getCode() + place + "_" + flag, String.valueOf(code), 5, TimeUnit.MINUTES);
    }

    public int updatePlatformUserDetail(PlatformUserDetail platformUserDetail) {
        platformUserDetail.setUpdateTime(DateUtils.getNowDate());
        int i = platformUserDetailMapper.updatePlatformUserDetail(platformUserDetail);
        // 删除用户信息
        redisCache.deleteObject(CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY + platformUserDetail.getDataId());
        return i;
    }


    /**
     * 获取用户信息
     *
     * @param dataId
     * @return
     */
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

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public PlatformUserDetail queryLoginUserInfo() {
        String dataId = SecurityUtils.getLoginUser().getDataId();
        Assert.isTrue(StringUtils.isNotEmpty(dataId),"dataId is null");
        return selectPlatformUserDetailByDataId(dataId);
    }

    /**
     * 批量获取用户信息
     * @param puUserIds
     * @return
     */
    public Map<String, String> selectUserPhoneByUserIds(List<Long> puUserIds) {
        String redisKey = CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode() + String.join(",", puUserIds.toString());
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheMap(redisKey);
        }
        io.jsonwebtoken.lang.Assert.isTrue(!ObjectUtils.isEmpty(puUserIds) && !puUserIds.isEmpty(), "不能查询全用户信息");
        List<PlatformUser> platformUsers = platformUserMapper.selectPlatformUserByIds(puUserIds);
        if (ObjectUtils.isEmpty(platformUsers)) {
            return Maps.newHashMap();
        }
        Map<String, String> result = platformUsers.stream().collect(Collectors.toMap(e -> e.getId().toString(), PlatformUser::getDataId));
        redisCache.setCacheMap(redisKey, result);
        redisCache.expire(redisKey, 1, TimeUnit.DAYS);
        return result;
    }

    /**
     * 通过用户id查询用户信息
     * @param userId
     * @return
     */
    public PlatformUserDetail getPlatFormUserDetailByUserId(Long userId) {
        String redisKey = CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode() + userId;
        if (redisCache.hasKey(redisKey)) {
            return JSON.parseObject(redisCache.getCacheObject(redisKey).toString(), PlatformUserDetail.class);
        }
        PlatformUserDetail platformUserDetail = platformUserDetailMapper.selectPlatformUserDetailById(userId);
        io.jsonwebtoken.lang.Assert.isTrue(!ObjectUtils.isEmpty(platformUserDetail), "用户信息不存在");
        redisCache.setCacheObject(redisKey, platformUserDetail, 1, TimeUnit.DAYS);
        return platformUserDetail;
    }

    /**
     * 根据状态查询用户信息
     * @param enabled
     * @return
     */
    List<PlatformUserDetail> queryStatus(Integer enabled) {
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
        String redisKey = CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode() + reqDTO.getDataId();

        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setDataId(reqDTO.getDataId());
        platformUserDetail.setType(PlatformUserTypeEnum.WX.name());
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
        if (ObjectUtils.isEmpty(platformUserDetails) || platformUserDetails.isEmpty()) {
            // 保存
            platformUserDetail.setAvatarUrl(ObjectUtils.isEmpty(reqDTO.getAvatarUrl()) ? platformUser.getAvatarUrl() : reqDTO.getAvatarUrl());
            platformUserDetail.setNickName(ObjectUtils.isEmpty(reqDTO.getNickName()) ? platformUser.getNickName() : reqDTO.getNickName());
            platformUserDetail.setEnabled(PlatformUserDetail.STATE_TYPE_ENABLED);
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

    public PlatformUserDetail saveEntryUserDetailByAccount(PlatformUser platformUser, PlatformUserReqDTO reqDTO) {
        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setAvatarUrl(platformUser.getAvatarUrl());
        platformUserDetail.setEnabled(PlatformUserDetail.STATE_TYPE_ENABLED);
        platformUserDetail.setType(PlatformUserTypeEnum.WEB.name());
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
}
