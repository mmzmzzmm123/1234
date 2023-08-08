package com.ruoyi.wechat.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wechat.mapper.PsyWechatExceptionsMapper;
import com.ruoyi.wechat.domain.PsyWechatExceptions;
import com.ruoyi.wechat.service.IPsyWechatExceptionsService;

import javax.annotation.Resource;

/**
 * 微信异常Service业务层处理
 * 
 * @author ruoyi
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
