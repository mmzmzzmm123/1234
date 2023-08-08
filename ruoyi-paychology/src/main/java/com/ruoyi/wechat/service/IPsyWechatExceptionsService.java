package com.ruoyi.wechat.service;

import java.util.List;
import com.ruoyi.wechat.domain.PsyWechatExceptions;

/**
 * 微信异常Service接口
 * 
 * @author ruoyi
 * @date 2023-08-08
 */
public interface IPsyWechatExceptionsService 
{

    void add(PsyWechatExceptions entity);

    PsyWechatExceptions getOne(Long id);

}
