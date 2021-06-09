package com.stdiet.custom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.wechat.WxSubscribePostLog;
import com.stdiet.custom.service.ISysRedisService;
import com.stdiet.custom.service.ISysSmsConfirmServie;
import com.stdiet.custom.service.IWxSubscribePostLogService;
import com.stdiet.custom.utils.SmsUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysSmsConfirmServiceImpl implements ISysSmsConfirmServie {

    private static long CODE_EXPIRE_SECONDS = 600;    //设置验证码过期时间为600秒

    @Autowired
    ISysRedisService redisService;
    @Autowired
    IWxSubscribePostLogService wxSubscribePostLogService;


    @Override
    public Integer sendSmsCode(String phone) {
        int reCode = -1;
        try {
            String code = RandomStringUtils.randomNumeric(6);
            JSONObject paramObj = new JSONObject();
            paramObj.put("code", code);
            SendSmsResponse response = SmsUtils.sendSms(phone, paramObj.toJSONString(), SmsUtils.SMS_217025172);
            if (response.getCode().equals("OK")) {
                // 发送成功
                redisService.remove(phone);
                redisService.set(phone, code);
                redisService.expire(phone, CODE_EXPIRE_SECONDS);
                reCode = 0;
            } else if (response.getCode().equals("isv.MOBILE_NUMBER_ILLEGAL")) {
                // 非法手机号
                reCode = 1;
            }

            WxSubscribePostLog postLog = new WxSubscribePostLog();
            postLog.setPhone(phone);
            JSONObject resultObj = new JSONObject();
            resultObj.put("requestId", response.getRequestId());
            resultObj.put("bizId", response.getBizId());
            resultObj.put("code", response.getCode());
            resultObj.put("message", response.getMessage());
            postLog.setResult(resultObj);
            paramObj.put("phone", phone);
            paramObj.put("tmpCode", SmsUtils.SMS_217025172);
            paramObj.put("signName", SmsUtils.SMS_SIGN_NAME);
            postLog.setData(paramObj);
            postLog.setSendTime(DateUtils.getNowDate());
            postLog.setType(2);
            wxSubscribePostLogService.insertWxSubscribePostLog(postLog);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return reCode;
    }

    @Override
    public Integer checkSmsCode(String phone, String code) {
        String cachedCode = redisService.get(phone);
        int resCode = -1;
        if (StringUtils.isEmpty(cachedCode)) {
            // 验证码失效
            resCode = 1;
            redisService.remove(phone);
        } else if (!code.equals(cachedCode)) {
            // 验证码错误
            resCode = 2;
        } else if (code.equals(cachedCode)) {
            // 校验成功
            resCode = 0;
            redisService.remove(phone);
        }
        return resCode;
    }
}
