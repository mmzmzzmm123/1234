package com.ruoyi.thirdparty.sms.service;

import com.ruoyi.thirdparty.sms.AliyunSmsUtil;
import com.ruoyi.thirdparty.sms.SMSConstant;
import com.ruoyi.thirdparty.sms.SmsResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 * @author : yelifeng
 * @date : 2020/10/17 0017 15:05 xuc5566@gmail.com
 */
@Service
public class SmsService {

    @Value("${accessKeyId}")
    private String accessKeyId = "yourAccessKeyId";

    @Value("${accessKeySecret}")
    private String accessKeySecret = "yourAccessKeySecret";

    @Value("${sms.isopen}")
    private Integer smsIsOpen;

    public void sendV4ValidateCode(String mobile) {

        if (SMSConstant.SMS_SWITCH_OPEN != smsIsOpen) {
            return;
        }
        // 生成6位数验证码 ，失效时间 90s
        String verifyCode = String.valueOf(new Random().nextInt(8999) + 1000);


        AliyunSmsUtil smsUtil = AliyunSmsUtil.init(accessKeyId, accessKeySecret);
        SmsResult result = smsUtil.sendMsg(SMSConstant.SMS_SIGN, SMSConstant.SMS_TEMPLATE_CODE, "{\"code\":\"" + verifyCode + "\"}", mobile);

    }
}
