package com.onethinker.user.platform.impl;

import cn.hutool.core.lang.Assert;
import com.onethinker.common.enums.PlatformUserTypeEnum;
import com.onethinker.common.utils.StringUtils;
import com.onethinker.framework.web.service.SysLoginService;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import com.onethinker.user.platform.UserStorage;
import com.onethinker.user.service.IPlatformUserService;
import com.onethinker.wechat.service.IMinWechatService;
import lombok.extern.log4j.Log4j2;
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



    public WxUserStorage(IMinWechatService minWechatService, IPlatformUserService platformUserService, SysLoginService loginService) {
        this.minWechatService = minWechatService;
        this.platformUserService = platformUserService;
        this.loginService = loginService;
        this.userTypeEnum = PlatformUserTypeEnum.WX;
    }

    @Override
    public void register(PlatformUserReqDTO reqDTO) {
        // 微信登录注册
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getOpenId()), "用户凭证不能为空");
        PlatformUser platformUser = platformUserService.selectPlatformUserDetailByDataId(reqDTO.getDataId());
        if (!ObjectUtils.isEmpty(platformUser)) {
            // 处理更新时间
            platformUser.setUpdateTime(new Date());
            platformUserService.updatePlatformUserDetail(platformUser);
        } else {
            // 创建用户信息
            platformUserService.saveEntryUserDetail(reqDTO,PlatformUserTypeEnum.WX);
        }
    }

    @Override
    public PlatformUserResDTO login(PlatformUserReqDTO reqDTO) {
        Assert.isTrue(StringUtils.isNotEmpty(reqDTO.getCode()),"code is null");
        log.info("platform:{},code:{}",userTypeEnum.getMsg(),reqDTO.getCode());
        // 进行访问
        String dataId = minWechatService.getMinWechatOpenIdByCode(reqDTO.getCode());
        reqDTO.setDataId(dataId);
        PlatformUser platformUser = platformUserService.selectPlatformUserDetailByDataId(reqDTO.getDataId());
        if (ObjectUtils.isEmpty(platformUser)) {
            // 调用注册方法进行保存
            register(reqDTO);
        }
        // 获取权限内容
        String token = loginService.loginFe(reqDTO.getDataId());
        return PlatformUserResDTO.foramtResponse(token, platformUser);
    }
}
