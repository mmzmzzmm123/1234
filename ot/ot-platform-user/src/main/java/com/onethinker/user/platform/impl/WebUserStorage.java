package com.onethinker.user.platform.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.onethinker.common.constant.Constants;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.enums.PlatformUserTypeEnum;
import com.onethinker.common.enums.SysConfigKeyEnum;
import com.onethinker.common.exception.user.CaptchaException;
import com.onethinker.common.exception.user.CaptchaExpireException;
import com.onethinker.common.utils.MessageUtils;
import com.onethinker.framework.manager.AsyncManager;
import com.onethinker.framework.manager.factory.AsyncFactory;
import com.onethinker.framework.web.service.SysLoginService;
import com.onethinker.system.service.ISysConfigService;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import com.onethinker.user.platform.UserStorage;
import com.onethinker.user.service.IPlatformUserService;
import com.onethinker.wechat.service.IMinWechatService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author yangyouqi
 * @date 2024/5/19
 */
@Log4j2
public class WebUserStorage implements UserStorage {

    private final PlatformUserTypeEnum userTypeEnum;
    private final IPlatformUserService platformUserService;
    private final SysLoginService loginService;
    private final ISysConfigService configService;
    private final RedisCache redisCache;

    public WebUserStorage(IMinWechatService minWechatService, IPlatformUserService platformUserService, SysLoginService loginService, ISysConfigService configService,RedisCache redisCache) {
        this.platformUserService = platformUserService;
        this.loginService = loginService;
        this.configService = configService;
        this.redisCache = redisCache;
        this.userTypeEnum = PlatformUserTypeEnum.WEB;
    }
    @Override
    @Transactional
    public void register(PlatformUserReqDTO reqDTO) {
        // 参数有效性校验
        reqDTO.existsParams();
        // 验证码有效性校验
        validateCaptcha(reqDTO.getDataId(),reqDTO.getCode(),reqDTO.getUuid());
        // 保存用户明细信息
        PlatformUserDetail existsUser = platformUserService.selectPlatformUserDetailByDataId(reqDTO.getDataId());
        Assert.isTrue(ObjectUtils.isEmpty(existsUser), "账号已被注册");
        // 保存用户明细信息
        platformUserService.saveEntryUserDetail(reqDTO,PlatformUserTypeEnum.WEB);
    }

    @Override
    public PlatformUserResDTO login(PlatformUserReqDTO reqDTO) {
        // 根据来源不同实例化不同具体实例
        log.info("platform:{},dataId:{}",userTypeEnum.getMsg(),reqDTO.getCode());
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getDataId()), "账户不能为空");
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getPassword()), "密码不能为空");
        PlatformUserDetail platformUserDetail = platformUserService.selectPlatformUserDetailByDataId(reqDTO.getDataId());
        Assert.isTrue(!ObjectUtils.isEmpty(platformUserDetail), "账号/密码有误");
        Assert.isTrue(Objects.equals(reqDTO.getPassword(), platformUserDetail.getPassword()), "账号/密码有误");
        // 获取权限内容
        String token = loginService.loginFe(platformUserDetail.getDataId());
        return PlatformUserResDTO.foramtResponse(token, platformUserDetail);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            String verifyKey = CacheEnum.CAPTCHA_CODE_KEY.getCode() + com.onethinker.common.utils.StringUtils.nvl(uuid, "");
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            if (!code.equalsIgnoreCase(captcha)) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
        }
    }
}
