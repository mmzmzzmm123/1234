package com.renxin.wechat.service.impl;

import org.springframework.stereotype.Service;
import com.renxin.wechat.mapper.PsyWechatExceptionsMapper;
import com.renxin.wechat.domain.PsyWechatExceptions;
import com.renxin.wechat.service.IPsyWechatExceptionsService;

import javax.annotation.Resource;

/**
 * 微信异常Service业务层处理
 * 
 * @author renxin
 * @date 2023-08-08
 */
@Service
public class PsyWechatExceptionsServiceImpl implements IPsyWechatExceptionsService 
{
    @Resource
    private PsyWechatExceptionsMapper psyWechatExceptionsMapper;

    @Override
    public void add(PsyWechatExceptions entity) {
        psyWechatExceptionsMapper.insert(entity);
    }

    @Override
    public PsyWechatExceptions getOne(Long id) {
        return psyWechatExceptionsMapper.selectById(id);
    }


}
