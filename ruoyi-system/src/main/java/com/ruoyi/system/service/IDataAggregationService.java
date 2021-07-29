package com.ruoyi.system.service;

import com.ruoyi.system.domain.model.DataCodeMsgResponse;

/**
 * 泉州市汇聚
 * 
 * @author genius
 * @date 2021-07-29
 */
public interface IDataAggregationService
{
    /**
     * 发送短信验证码（对应codemsg接口类型）
     * @param phone 手机号码
     * @param code 短信验证码
     * @return
     */
    public DataCodeMsgResponse untrustworthyPersonnel(String phone, String code);
}
