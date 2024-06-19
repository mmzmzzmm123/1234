package com.renxin.wechat.service;

import com.renxin.wechat.domain.PsyWechatExceptions;

/**
 * 微信异常Service接口
 * 
 * @author renxin
 * @date 2023-08-08
 */
public interface IPsyWechatExceptionsService 
{

    void add(PsyWechatExceptions entity);

    PsyWechatExceptions getOne(Long id);

}
