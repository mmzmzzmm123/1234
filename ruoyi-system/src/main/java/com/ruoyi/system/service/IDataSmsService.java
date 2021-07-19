package com.ruoyi.system.service;

import com.ruoyi.system.domain.model.DataCodeMsgResponse;

/**
 * 短信接口服务。对接文档请参考：http://47.97.21.146:9090/doc/sms
 * 
 * @author genius
 * @date 2021-07-13
 */
public interface IDataSmsService
{
    /**
     * 发送短信验证码（对应codemsg接口类型）
     * @param phone 手机号码
     * @param code 短信验证码
     * @return
     */
    public DataCodeMsgResponse sendVerifyCode(String phone, String code);
}
