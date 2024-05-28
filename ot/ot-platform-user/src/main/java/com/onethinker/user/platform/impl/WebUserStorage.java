package com.onethinker.user.platform.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.PlatformUserTypeEnum;
import com.onethinker.framework.web.service.SysLoginService;
import com.onethinker.system.service.ISysConfigService;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import com.onethinker.user.platform.UserStorage;
import com.onethinker.user.service.IPlatformUserService;
import com.onethinker.wechat.service.IMinWechatService;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

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

    public WebUserStorage(IMinWechatService minWechatService, IPlatformUserService platformUserService, SysLoginService loginService) {
        this.platformUserService = platformUserService;
        this.loginService = loginService;
        this.userTypeEnum = PlatformUserTypeEnum.WEB;
    }
    @Override
    @Transactional
    public void register(PlatformUserReqDTO reqDTO) {
        // 参数有效性校验
        reqDTO.existsParams();
        // 验证码有效性校验
        platformUserService.validateCaptcha(reqDTO.getDataId(),reqDTO.getCode(),reqDTO.getUuid());
        // 保存用户明细信息
        PlatformUser existsUser = platformUserService.selectPlatformUserDetailByDataId(reqDTO.getDataId());
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
        PlatformUser platformUser = platformUserService.selectPlatformUserDetailByDataId(reqDTO.getDataId());
        Assert.isTrue(!ObjectUtils.isEmpty(platformUser), "账号/密码有误");
        Assert.isTrue(Objects.equals(reqDTO.getPassword(), platformUser.getPassword()), "账号/密码有误");
        // 获取权限内容
        String token = loginService.loginFe(platformUser.getDataId());
        return PlatformUserResDTO.foramtResponse(token, platformUser);
    }
}
