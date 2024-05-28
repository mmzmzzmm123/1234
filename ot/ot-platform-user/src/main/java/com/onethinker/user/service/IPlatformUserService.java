package com.onethinker.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.onethinker.common.constant.Constants;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.*;
import com.onethinker.common.exception.user.CaptchaException;
import com.onethinker.common.exception.user.CaptchaExpireException;
import com.onethinker.common.utils.*;
import com.onethinker.framework.manager.AsyncManager;
import com.onethinker.framework.manager.factory.AsyncFactory;
import com.onethinker.framework.web.service.SysLoginService;
import com.onethinker.mail.service.IMailService;
import com.onethinker.system.service.ISysConfigService;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.dto.PlatformUserReqDTO;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
            Assert.isTrue(!ObjectUtils.isEmpty(userTypeEnum), "userTypeEnum is null ");
            Class<?> clazz = Class.forName(userTypeEnum.getInterfaceClass());
            Constructor<?> constructor = clazz.getConstructor(IMinWechatService.class, IPlatformUserService.class, SysLoginService.class);
            constructor.setAccessible(true);
            UserStorage instance = (UserStorage) constructor.newInstance(wechatService, this, sysLoginService);
            return Tools.cast(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新平台用户信息
     *
     * @param platformUser 用户对象
     * @return 更新值
     */
    public int updatePlatformUserDetail(PlatformUser platformUser) {
        platformUser.setUpdateTime(DateUtils.getNowDate());
        // 删除用户信息
        redisCache.deleteObject(REDIS_KEY + platformUser.getDataId());
        return platformUserMapper.updatePlatformUserDetail(platformUser);
    }

    /**
     * 获取用户信息
     *
     * @param dataId 登录凭证
     * @return
     */
    public PlatformUser selectPlatformUserDetailByDataId(String dataId) {
        if (dataId == null) {
            return null;
        }
        String redisKey = REDIS_KEY + dataId;

        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheObject(redisKey);
        }
        LambdaQueryWrapper<PlatformUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformUser::getDataId, dataId);
        PlatformUser platformUser = platformUserMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(platformUser)) {
            return null;
        }
        redisCache.setCacheObject(redisKey, platformUser, 1, TimeUnit.DAYS);
        return platformUser;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public PlatformUser queryLoginUserInfo() {
        String dataId = SecurityUtils.getLoginUser().getDataId();
        Assert.isTrue(StringUtils.isNotEmpty(dataId), "dataId is null");
        return selectPlatformUserDetailByDataId(dataId);
    }

    /**
     * 查询对于类型下的所有用户信息
     *
     * @param type 用户类型
     * @return
     */
    public List<PlatformUser> queryAllUserByType(String type) {
        LambdaQueryWrapper<PlatformUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformUser::getType, type);
        return platformUserMapper.selectList(queryWrapper);
    }

    /**
     * 保存账号信息
     *
     * @param reqDTO
     * @param platformUserTypeEnum
     * @return
     */
    public PlatformUser saveEntryUserDetail(PlatformUserReqDTO reqDTO, PlatformUserTypeEnum platformUserTypeEnum) {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setAvatarUrl(configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_AVATAR_URL));
        platformUser.setNickName(configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_NICK_NAME) + System.currentTimeMillis());
        platformUser.setEnabled(SysStatusTypeEnum.STATUS_TYPE_ENABLED.getCode());
        platformUser.setType(platformUserTypeEnum.name());
        platformUser.setPassword(reqDTO.getPassword());
        platformUser.setDataId(reqDTO.getDataId());
        platformUser.setCreateTime(new Date());
        platformUserMapper.insertPlatformUserDetail(platformUser);
        // 清理下缓存信息
        redisCache.deleteObject(REDIS_KEY + reqDTO.getDataId());
        return platformUser;
    }

    /**
     * 绑定手机号或邮箱
     * @param reqDTO
     * @param codeTypeEnum 邮箱
     */
    public void bindPhoneOrEmail(PlatformUserReqDTO reqDTO, CodeTypeEnum codeTypeEnum) {
        // 校验参数有效值
        reqDTO.existsParamsByBindPhoneOrEmail(codeTypeEnum);
        String phoneOrEmailValue = CodeTypeEnum.PHONE.equals(codeTypeEnum) ? reqDTO.getPhone() : reqDTO.getEmail();
        // 验证码是否有效
        validateCaptcha(phoneOrEmailValue,reqDTO.getCode(),reqDTO.getUuid());
        PlatformUser platformUser = selectPlatformUserDetailByDataId(reqDTO.getDataId());
        LambdaQueryWrapper<PlatformUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformUser::getType,platformUser.getType());
        queryWrapper.ne(PlatformUser::getEnabled,SysStatusTypeEnum.STATUS_TYPE_DELETE.getCode());
        // 校验账号是否被绑定
        if (CodeTypeEnum.PHONE.equals(codeTypeEnum)) {
            queryWrapper.eq(PlatformUser::getPhone,reqDTO.getPhone());
            platformUser.setPhone(reqDTO.getPhone());
        } else if (CodeTypeEnum.MAIL.equals(codeTypeEnum)) {
            queryWrapper.eq(PlatformUser::getEmail,reqDTO.getEmail());
            platformUser.setEmail(reqDTO.getEmail());
        }
        PlatformUser existsPlatformUser = platformUserMapper.selectOne(queryWrapper);
        if (Objects.nonNull(existsPlatformUser) && !existsPlatformUser.getDataId().equals(platformUser.getDataId())) {
          throw new RuntimeException(codeTypeEnum.getMsg() + "已被其他平台用户绑定");
        }
        // 更新用户信息
        platformUser.setUpdateTime(new Date());
        platformUserMapper.updatePlatformUserDetail(platformUser);
    }


    /**
     * 校验验证码
     *
     * @param dataId 各种登录相关凭证
     * @param code   验证码
     * @param uuid   唯一标识
     * @return 结果
     */
    public void validateCaptcha(String dataId, String code, String uuid) {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            String verifyKey = CacheEnum.CAPTCHA_CODE_KEY.getCode() + com.onethinker.common.utils.StringUtils.nvl(uuid, "");
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(dataId, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            if (!code.equalsIgnoreCase(captcha)) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(dataId, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
        }
    }
}
