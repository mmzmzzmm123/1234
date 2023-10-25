package com.ruoyi.onethinker.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ruoyi.common.enums.SysConfigKeyEnum;
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
public class PlatformUserSourceTypeAccountServiceImpl implements IPlatformUserService {

    @Autowired
    private IPlatformUserDetailService platformUserDetailService;

    @Autowired
    private PlatformUserMapper platformUserMapper;

    @Autowired
    private ISysConfigService configService;

    private Logger logger = LoggerFactory.getLogger(PlatformUserSourceTypeAccountServiceImpl.class);

    @Override
    public PlatformUserResDTO login(PlatformUserReqDTO reqDTO) {
        // 根据来源不同实例化不同具体实例
        logger.info("账户密码登录");
        return null;
    }

    @Override
    public void register(PlatformUserReqDTO reqDTO) {
        Assert.isTrue(PhoneUtils.isMobiPhoneNum(reqDTO.getPhone()),"手机号有误");
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPhone(reqDTO.getPhone());
        List<PlatformUser> existsUser = platformUserMapper.selectPlatformUserList(platformUser);
        Assert.isTrue(ObjectUtils.isEmpty(existsUser) || existsUser.isEmpty(),"手机号已注册");
        // 保存用户信息
        platformUser = new PlatformUser();
        platformUser.setPhone(reqDTO.getPhone());
        platformUser.setEnabled(PlatformUser.STATE_TYPE_ENABLED);
        platformUser.setWeight(System.currentTimeMillis());
        platformUser.setCreateTime(new Date());
        platformUser.setAvatarUrl(configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_AVATAR_URL));
        platformUser.setNickName(configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_NICK_NAME) + System.currentTimeMillis());
        platformUserMapper.insertPlatformUser(platformUser);
        // 保存用户明细信息
        platformUserDetailService.saveEntryUserDetailByAccount(platformUser);
    }
}
