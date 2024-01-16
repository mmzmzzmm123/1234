package com.onethinker.user.factory.service.impl;

import com.ruoyi.common.enums.SysConfigKeyEnum;
import com.ruoyi.common.utils.PhoneUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.system.service.ISysConfigService;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import com.onethinker.user.factory.service.IPlatformUserService;
import com.onethinker.user.mapper.PlatformUserMapper;
import com.onethinker.user.service.IPlatformUserDetailService;
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
public class PlatformUserSourceTypeAccountServiceImpl implements IPlatformUserService {

    @Autowired
    private IPlatformUserDetailService platformUserDetailService;

    @Resource
    private PlatformUserMapper platformUserMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysLoginService loginService;

    private Logger logger = LoggerFactory.getLogger(PlatformUserSourceTypeAccountServiceImpl.class);

    @Override
    public PlatformUserResDTO login(PlatformUserReqDTO reqDTO) {
        // 根据来源不同实例化不同具体实例
        logger.info("账户密码登录{}",reqDTO.getPhone());
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getPhone()),"手机号不能为空");
        PlatformUserDetail platformUserDetail = platformUserDetailService.selectPlatformUserDetailByDataId(reqDTO.getPhone());
        Assert.isTrue(!ObjectUtils.isEmpty(platformUserDetail),"账号不存在");

        // 获取权限内容
        String token = loginService.loginFe(platformUserDetail.getDataId(),configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_NAME)),configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_PASSWORD)));
        return PlatformUserResDTO.foramtResponse(token,platformUserDetail);
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

    @Override
    public void getCodeForForgetPassword(String place, Integer flag) {

    }
}
