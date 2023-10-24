package com.ruoyi.onethinker.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ruoyi.common.utils.PhoneUtils;
import com.ruoyi.onethinker.domain.PlatformUser;
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

    private Logger logger = LoggerFactory.getLogger(PlatformUserSourceTypeWxServiceImpl.class);
    @Override
    public PlatformUserResDTO login(PlatformUserReqDTO reqDTO) {
        // 根据来源不同实例化不同具体实例
        logger.info("微信登录");
        return null;
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
            platformUser.setAvatarUrl(ObjectUtils.isEmpty(reqDTO.getAvatarUrl()) ? configService.selectConfigByKey(PlatformUser.DEFAULT_AVATAR_URL) : reqDTO.getAvatarUrl());
            platformUser.setNickName(ObjectUtils.isEmpty(reqDTO.getNickName()) ? configService.selectConfigByKey(PlatformUser.DEFAULT_NICK_NAME) + System.currentTimeMillis() : reqDTO.getNickName());
            platformUserMapper.insertPlatformUser(platformUser);
            // 另外保存一份网页端账号信息
            platformUserDetailService.saveEntryUserDetailByAccount(platformUser);
        }
        platformUserDetailService.saveEntryUserDetailByWx(platformUser,reqDTO);
    }
}
