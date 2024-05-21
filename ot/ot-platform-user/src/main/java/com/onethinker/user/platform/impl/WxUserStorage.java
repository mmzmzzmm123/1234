package com.onethinker.user.platform.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.lang.Assert;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.SysConfigKeyEnum;
import com.onethinker.common.utils.PhoneUtils;
import com.onethinker.framework.web.service.SysLoginService;
import com.onethinker.system.service.ISysConfigService;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import com.onethinker.common.enums.PlatformUserTypeEnum;
import com.onethinker.user.platform.UserStorage;
import com.onethinker.user.service.IPlatformUserService;
import com.onethinker.wechat.service.IMinWechatService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author yangyouqi
 * @date 2024/5/19
 */
@Log4j2
public class WxUserStorage implements UserStorage {
    private final PlatformUserTypeEnum userTypeEnum;
    private final IMinWechatService minWechatService;
    private final IPlatformUserService platformUserService;
    private final SysLoginService loginService;
    private final ISysConfigService configService;



    public WxUserStorage(IMinWechatService minWechatService, IPlatformUserService platformUserService, SysLoginService loginService, ISysConfigService configService, RedisCache redisCache) {
        this.minWechatService = minWechatService;
        this.platformUserService = platformUserService;
        this.loginService = loginService;
        this.configService = configService;
        this.userTypeEnum = PlatformUserTypeEnum.WX;
    }

    @Override
    public PlatformUserResDTO register(PlatformUserReqDTO reqDTO) {
        // 微信登录注册
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getOpenId()), "用户凭证不能为空");
        Assert.isTrue(PhoneUtils.isMobiPhoneNum(reqDTO.getPhone()), "手机号有误");
        // 校验手机号是否被绑定
        platformUserService.existsWxRegister(reqDTO.getOpenId(), reqDTO.getPhone());
        PlatformUserDetail platformUserDetail = platformUserService.selectPlatformUserDetailByDataId(reqDTO.getPhone());
        if (!ObjectUtils.isEmpty(platformUserDetail)) {
            // 处理更新时间
            platformUserDetail.setUpdateTime(new Date());
            platformUserService.updatePlatformUserDetail(platformUserDetail);
        } else {
            // 创建用户信息
            PlatformUser platformUser = new PlatformUser();
            platformUser.setEnabled(PlatformUser.STATE_TYPE_ENABLED);
            platformUser.setDataId(reqDTO.getPhone());
            platformUser.setWeight(System.currentTimeMillis());
            platformUser.setCreateTime(new Date());
            platformUser.setAvatarUrl(ObjectUtils.isEmpty(reqDTO.getAvatarUrl()) ? configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_AVATAR_URL) : reqDTO.getAvatarUrl());
            platformUser.setNickName(ObjectUtils.isEmpty(reqDTO.getNickName()) ? configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_NICK_NAME) + System.currentTimeMillis() : reqDTO.getNickName());
            platformUserService.insertPlatformUser(platformUser);
            // 另外保存一份网页端账号信息
            platformUserService.saveEntryUserDetailByAccount(platformUser, reqDTO);
            // 保存微信账号信息
            platformUserDetail = platformUserService.saveEntryUserDetailByWx(platformUser, reqDTO);
        }
        // 获取权限内容
        String token = loginService.loginFe(reqDTO.getPhone(), configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_NAME)), configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_PASSWORD)));
        return PlatformUserResDTO.foramtResponse(token, platformUserDetail);
    }

    @Override
    public PlatformUserResDTO login(PlatformUserReqDTO reqDTO) {
        Assert.isTrue(StringUtil.isNotBlank(reqDTO.getCode()),"code is null");
        log.info("platform:{},code:{}",userTypeEnum.getMsg(),reqDTO.getCode());
        // 进行访问
        WxMaJscode2SessionResult sessionInfo = minWechatService.getSessionInfo(reqDTO.getCode());
        reqDTO.setDataId(sessionInfo.getOpenid());
        PlatformUserDetail platformUserDetail = platformUserService.selectPlatformUserDetailByDataId(reqDTO.getDataId());
        if (ObjectUtils.isEmpty(platformUserDetail)) {
            // 微信登录保存相关信息
            platformUserDetail = platformUserService.saveEntryUserDetailByWx(new PlatformUser(), reqDTO);
        }
        // 获取权限内容
        String token = loginService.loginFe(reqDTO.getDataId(), configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_NAME)), configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_PASSWORD)));
        return PlatformUserResDTO.foramtResponse(token, platformUserDetail);
    }
}
