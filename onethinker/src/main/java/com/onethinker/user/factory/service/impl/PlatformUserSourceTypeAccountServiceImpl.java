package com.onethinker.user.factory.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.onethinker.bk.domain.WebInfo;
import com.onethinker.bk.service.IImChatGroupService;
import com.onethinker.bk.service.IImChatGroupUserService;
import com.onethinker.bk.service.IImChatUserFriendService;
import com.onethinker.bk.service.IWeiYanService;
import com.onethinker.mail.service.IMailService;
import com.onethinker.user.enums.ServiceTypeEnum;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.CacheEnum;
import com.ruoyi.common.enums.SysConfigKeyEnum;
import com.ruoyi.common.utils.PhoneUtils;
import com.ruoyi.common.utils.SecurityUtils;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IMailService mailService;

    @Autowired
    private IWeiYanService weiYanService;

    @Autowired
    private IImChatGroupUserService imChatGroupUserService;

    @Autowired
    private IImChatUserFriendService imChatUserFriendService;

    @Autowired
    private SysLoginService sysLoginService;

    private Logger logger = LoggerFactory.getLogger(PlatformUserSourceTypeAccountServiceImpl.class);

    @Override
    public PlatformUserResDTO login(PlatformUserReqDTO reqDTO) {
        // 根据来源不同实例化不同具体实例
        logger.info("手机/账户/邮箱登录{}",reqDTO.getDataId());
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getDataId()),"凭证信息不能为空");
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getPassword()),"密码信息不能为空");
        PlatformUserDetail platformUserDetail = platformUserDetailService.selectPlatformUserDetailByDataId(reqDTO.getDataId());
        Assert.isTrue(!ObjectUtils.isEmpty(platformUserDetail),"账号不存在");
        Assert.isTrue(Objects.equals(reqDTO.getPassword(),platformUserDetail.getPassword()),"账户密码有误");
        // 获取权限内容
        String token = loginService.loginFe(platformUserDetail.getDataId(),configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_NAME)),configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_PASSWORD)));
        return PlatformUserResDTO.foramtResponse(token,platformUserDetail);
    }

    @Override
    @Transactional
    public PlatformUserResDTO register(PlatformUserReqDTO reqDTO) {
        // 参数有效性校验
        reqDTO.existsParams();
        // 验证码有效性校验
        String dataId = existsByCode(reqDTO);
        // 保存用户明细信息
        PlatformUser platformUser = new PlatformUser();
        platformUser.setDataId(dataId);
        List<PlatformUser> existsUser = platformUserMapper.selectPlatformUserList(platformUser);
        Assert.isTrue(ObjectUtils.isEmpty(existsUser) || existsUser.isEmpty(),"邮箱或账号已被注册");
        // 保存用户信息
        platformUser = new PlatformUser();
        platformUser.setDataId(dataId);
        platformUser.setEnabled(PlatformUser.STATE_TYPE_ENABLED);
        platformUser.setWeight(System.currentTimeMillis());
        platformUser.setCreateTime(new Date());
        platformUser.setAvatarUrl(configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_AVATAR_URL));
        platformUser.setNickName(configService.selectConfigByKey(SysConfigKeyEnum.DEFAULT_NICK_NAME) + System.currentTimeMillis());
        platformUserMapper.insertPlatformUser(platformUser);
        // 保存用户明细信息
        PlatformUserDetail platformUserDetail = platformUserDetailService.saveEntryUserDetailByAccount(platformUser, reqDTO);
        // 以下是业务内容，不同业务在创建用户时有不同的逻辑处理，为了不相互印象，前端这边传入对应参数处理
        if (ServiceTypeEnum.BK.getCode().equals(reqDTO.getServiceType())) {
            // 需要向WeiYan、ImChatGroupUser、ImChatUserFriend更新数据
            weiYanService.insertWeiYanByDataId(platformUser.getId());
            imChatGroupUserService.insertImChatGroupByPlUserId(platformUser.getId());
            imChatUserFriendService.insertImChatUserFriendByPlUserId(platformUser.getId());
        }
        // 获取权限内容
        String token = loginService.loginFe(platformUserDetail.getDataId(),configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_NAME)),configService.selectConfigByKey(SysConfigKeyEnum.getSysConfigKeyEnumByCode(PlatformUser.PU_USER_PASSWORD)));
        return PlatformUserResDTO.foramtResponse(token,platformUserDetail);
    }

    private String existsByCode(PlatformUserReqDTO reqDTO) {
        String redisKey = CacheEnum.CAPTCHA_CODE_KEY.getCode();
        String dataId = "";
        if (StringUtils.hasText(reqDTO.getPhone())) {
            redisKey = redisKey.concat(reqDTO.getPhone()) + "_1";
            dataId = reqDTO.getPhone();
        } else if (StringUtils.hasText(reqDTO.getEmail())) {
            redisKey = redisKey.concat(reqDTO.getEmail()) + "_2";
            dataId = reqDTO.getEmail();
        }
        String codeCache = redisCache.getCacheObject(redisKey);
//        Assert.isTrue(Objects.equals(codeCache, reqDTO.getCode()),"验证码错误！");
        redisCache.deleteObject(redisKey);
        return dataId;
    }

    @Override
    public void getCodeForForgetPassword(String place, String flag) {
        // 随机数
        int code = new Random().nextInt(900000) + 100000;
        if (PlatformUserReqDTO.PHONE_CODE.equals(flag)) {
            log.info(place + "---" + "手机验证码---" + code);
        } else if (PlatformUserReqDTO.EMAIL_CODE.equals(flag)){
            log.info(place + "---" + "邮箱验证码---" + code);
            // 发送邮箱验证码处理
            mailService.sendMailCode(place,code);
        }
        // 保存5分钟
        redisCache.setCacheObject(CacheEnum.CAPTCHA_CODE_KEY.getCode() + place + "_" + flag, String.valueOf(code), 5, TimeUnit.MINUTES);
    }
}
