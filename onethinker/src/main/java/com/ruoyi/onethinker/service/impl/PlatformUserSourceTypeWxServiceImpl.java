package com.ruoyi.onethinker.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.enums.SysConfigKeyEnum;
import com.ruoyi.common.utils.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ruoyi.common.utils.PhoneUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.onethinker.domain.PlatformUser;
import com.ruoyi.onethinker.domain.PlatformUserDetail;
import com.ruoyi.onethinker.dto.PlatformUserReqDTO;
import com.ruoyi.onethinker.dto.PlatformUserResDTO;
import com.ruoyi.onethinker.mapper.PlatformUserMapper;
import com.ruoyi.onethinker.service.IPlatformUserDetailService;
import com.ruoyi.onethinker.service.IPlatformUserService;
import com.ruoyi.system.service.ISysConfigService;
import io.jsonwebtoken.lang.Assert;

/**
 * 平台用户Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
@Service
public class PlatformUserSourceTypeWxServiceImpl implements IPlatformUserService {

    @Autowired
    private PlatformUserMapper platformUserMapper;
    @Autowired
    private IPlatformUserDetailService platformUserDetailService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private SysLoginService loginService;

    private Logger logger = LoggerFactory.getLogger(PlatformUserSourceTypeWxServiceImpl.class);
    @Override
    public PlatformUserResDTO login(PlatformUserReqDTO reqDTO) {
        // 根据来源不同实例化不同具体实例
        logger.info("微信登录~" + reqDTO.getCode());
        // 进行访问
        WeixinUtil weixinUtil = new WeixinUtil(configService.selectConfigByKey(SysConfigKeyEnum.APPID),configService.selectConfigByKey(SysConfigKeyEnum.APPSECRET));
        String openId = weixinUtil.code2Session(reqDTO.getCode());
        reqDTO.setOpenId(openId);
        PlatformUserDetail platformUserDetail = platformUserDetailService.selectPlatformUserDetailByDataId(openId);
        if (ObjectUtils.isEmpty(platformUserDetail)) {
            // 微信登录保存相关信息
            platformUserDetailService.saveEntryUserDetailByWx(new PlatformUser(),reqDTO);
        }
        // 获取权限内容
        String token = loginService.loginFe(reqDTO.getOpenId(),configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_NAME)),configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_PASSWORD)));
        return PlatformUserResDTO.foramtResponse(token,platformUserDetail);
    }

    @Override
    public void register(PlatformUserReqDTO reqDTO) {
        // 微信登录注册
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getOpenId()),"用户凭证不能为空");
        Assert.isTrue(PhoneUtils.isMobiPhoneNum(reqDTO.getPhone()),"手机号有误");
        // 校验手机号是否被绑定
        platformUserDetailService.existsWxRegister(reqDTO.getOpenId(),reqDTO.getPhone());
        PlatformUser platformUserParams = new PlatformUser();
        platformUserParams.setPhone(reqDTO.getPhone());
        List<PlatformUser> platformUsers = platformUserMapper.selectPlatformUserList(platformUserParams);
        PlatformUser platformUser = new PlatformUser();
        if (!ObjectUtils.isEmpty(platformUsers) && !platformUsers.isEmpty()) {
            platformUser = platformUsers.get(0);
            platformUser.setUpdateTime(new Date());
            platformUserMapper.updatePlatformUser(platformUser);
        } else {
            platformUser.setEnabled(PlatformUser.STATE_TYPE_ENABLED);
            platformUser.setPhone(reqDTO.getPhone());
            platformUser.setWeight(System.currentTimeMillis());
            platformUser.setCreateTime(new Date());
            platformUser.setAvatarUrl(ObjectUtils.isEmpty(reqDTO.getAvatarUrl()) ? configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_AVATAR_URL) : reqDTO.getAvatarUrl());
            platformUser.setNickName(ObjectUtils.isEmpty(reqDTO.getNickName()) ? configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_NICK_NAME) + System.currentTimeMillis() : reqDTO.getNickName());
            platformUserMapper.insertPlatformUser(platformUser);
            // 另外保存一份网页端账号信息
            platformUserDetailService.saveEntryUserDetailByAccount(platformUser);
        }
        platformUserDetailService.saveEntryUserDetailByWx(platformUser,reqDTO);
    }
}
