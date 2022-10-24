package com.ruoyi.login.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.gauge.sms.CodeUtil;
import com.ruoyi.login.service.ILoginService;
import com.ruoyi.psychology.domain.PsyUser;
import com.ruoyi.psychology.mapper.PsyUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @User hogan
 * @Time 2022/10/18 16:02
 * @e-mail hkcugwh@163.com
 **/
@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {

    @Resource
    private RedisCache redisCache;

    @Autowired
    private CodeUtil codeUtil;
    /**
     * 验证码过期时间
     **/
    @Value("${login.sms.codeExpireTime:60}")
    private Integer codeExpireTime;

    @Resource
    private PsyUserMapper psyUserMapper;

    @Override
    public AjaxResult getSmsCode(LoginDTO loginDTO) {

        //生成6位验证码
        String code = CodeUtil.vcode();
        log.debug("手机短信发送的验证码为：【{}】" ,code);
        try {
            SendSmsResponse res = codeUtil.phoneNumberCode(loginDTO.getAccount(), code);
            log.debug("code_res:{} ,code:{}", res.getMessage(), res.getCode());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        //通过手机号缓存，并设置失效时间
        redisCache.setCacheObject(UserConstants.LOGIN_SMS_CODE + loginDTO.getAccount(), code, codeExpireTime, TimeUnit.SECONDS);
        return AjaxResult.success(RespMessageConstants.SMS_SEND_SUCCESS);
    }

    @Override
    public boolean verifyCode(LoginDTO loginDTO) {
        String cacheCode = redisCache.getCacheObject(UserConstants.LOGIN_SMS_CODE + loginDTO.getAccount());
        if(cacheCode == null){
            throw new ServiceException(RespMessageConstants.SMS_CODE_EXPIRED);
        }
        if(!cacheCode.equals(loginDTO.getValidStr())){
            throw new ServiceException(RespMessageConstants.SMS_CODE_NOT_MATCH);
        }
        //向用户表插入一条数据
        psyUserMapper.insertOrUpdate(PsyUser.builder().phone(loginDTO.getAccount()).build());
        return true;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        return null;
    }
}
