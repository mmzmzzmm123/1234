package com.onethinker.user.factory.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import com.onethinker.user.factory.service.IPlatformUserService;
import com.onethinker.user.mapper.PlatformUserMapper;
import com.onethinker.user.service.IPlatformUserDetailService;
import com.onethinker.wechat.service.IMinWechatService;
import com.onethinker.common.enums.SysConfigKeyEnum;
import com.onethinker.common.utils.PhoneUtils;
import com.onethinker.framework.web.service.SysLoginService;
import com.onethinker.system.service.ISysConfigService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 平台用户Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
@Service
@Log4j2
public class PlatformUserSourceTypeWxServiceImpl implements IPlatformUserService {
    @Resource
    private PlatformUserMapper platformUserMapper;
    @Autowired
    private IPlatformUserDetailService platformUserDetailService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private SysLoginService loginService;
    @Autowired
    private IMinWechatService IMinWechatService;
    private Logger logger = LoggerFactory.getLogger(PlatformUserSourceTypeWxServiceImpl.class);

    @Override
    public PlatformUserResDTO login(PlatformUserReqDTO reqDTO) {
        // 根据来源不同实例化不同具体实例
        logger.info("微信登录~" + reqDTO.getCode());
        // 进行访问
        WxMaJscode2SessionResult sessionInfo = IMinWechatService.getSessionInfo(reqDTO.getCode());
        reqDTO.setDataId(sessionInfo.getOpenid());
        PlatformUserDetail platformUserDetail = platformUserDetailService.selectPlatformUserDetailByDataId(reqDTO.getDataId());
        if (ObjectUtils.isEmpty(platformUserDetail)) {
            // 微信登录保存相关信息
            platformUserDetail = platformUserDetailService.saveEntryUserDetailByWx(new PlatformUser(), reqDTO);
        }
        // 获取权限内容
        String token = loginService.loginFe(reqDTO.getDataId(), configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_NAME)), configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_PASSWORD)));
        return PlatformUserResDTO.foramtResponse(token, platformUserDetail);
    }

    @Override
    public PlatformUserResDTO register(PlatformUserReqDTO reqDTO) {
        // 微信登录注册
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getOpenId()), "用户凭证不能为空");
        Assert.isTrue(PhoneUtils.isMobiPhoneNum(reqDTO.getPhone()), "手机号有误");
        // 校验手机号是否被绑定
        platformUserDetailService.existsWxRegister(reqDTO.getOpenId(), reqDTO.getPhone());
        PlatformUser platformUserParams = new PlatformUser();
        platformUserParams.setDataId(reqDTO.getPhone());
        List<PlatformUser> platformUsers = platformUserMapper.selectPlatformUserList(platformUserParams);
        PlatformUser platformUser = new PlatformUser();
        if (!ObjectUtils.isEmpty(platformUsers) && !platformUsers.isEmpty()) {
            platformUser = platformUsers.get(0);
            platformUser.setUpdateTime(new Date());
            platformUserMapper.updatePlatformUser(platformUser);
        } else {
            platformUser.setEnabled(PlatformUser.STATE_TYPE_ENABLED);
            platformUser.setDataId(reqDTO.getPhone());
            platformUser.setWeight(System.currentTimeMillis());
            platformUser.setCreateTime(new Date());
            platformUser.setAvatarUrl(ObjectUtils.isEmpty(reqDTO.getAvatarUrl()) ? configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_AVATAR_URL) : reqDTO.getAvatarUrl());
            platformUser.setNickName(ObjectUtils.isEmpty(reqDTO.getNickName()) ? configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_NICK_NAME) + System.currentTimeMillis() : reqDTO.getNickName());
            platformUserMapper.insertPlatformUser(platformUser);
            // 另外保存一份网页端账号信息
            platformUserDetailService.saveEntryUserDetailByAccount(platformUser, reqDTO);
        }
        PlatformUserDetail platformUserDetail = platformUserDetailService.saveEntryUserDetailByWx(platformUser, reqDTO);
        // 获取权限内容
        String token = loginService.loginFe(platformUser.getDataId(), configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_NAME)), configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_PASSWORD)));
        return PlatformUserResDTO.foramtResponse(token, platformUserDetail);
    }

    @Override
    public void getCodeForForgetPassword(String place, String flag) {
        throw new RuntimeException("暂不支持");
    }
}
