package com.onethinker.user.platform.impl;

import com.onethinker.common.enums.PlatformUserTypeEnum;
import com.onethinker.framework.web.service.SysLoginService;
import com.onethinker.system.service.ISysConfigService;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import com.onethinker.user.platform.UserStorage;
import com.onethinker.user.service.IPlatformUserService;
import com.onethinker.wechat.service.IMinWechatService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yangyouqi
 * @date 2024/5/19
 */
public class WebUserStorage implements UserStorage {

    private final PlatformUserTypeEnum userTypeEnum;
    private final IMinWechatService minWechatService;
    private final IPlatformUserService platformUserService;
    private final SysLoginService loginService;
    private final ISysConfigService configService;



    public WebUserStorage(@Autowired IMinWechatService minWechatService, IPlatformUserService platformUserService, SysLoginService loginService, ISysConfigService configService) {
        this.minWechatService = minWechatService;
        this.platformUserService = platformUserService;
        this.loginService = loginService;
        this.configService = configService;
        this.userTypeEnum = PlatformUserTypeEnum.WEB;
    }
    @Override
    public PlatformUserResDTO register(PlatformUserReqDTO reqDTO) {
        return null;
    }

    @Override
    public PlatformUserResDTO login(PlatformUserReqDTO userReqDTO) {
        return null;
    }
}
