package com.ruoyi.psychology.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.psychology.mapper.PsyConsultPayMapper;
import com.ruoyi.psychology.domain.PsyConsultPay;
import com.ruoyi.psychology.service.IPsyConsultPayService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 心理咨询订单支付信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-30
 */
@Service
public class PsyConsultPayServiceImpl implements IPsyConsultPayService 
{
    @Resource
    private PsyConsultPayMapper psyConsultPayMapper;

    @Override
    public PsyConsultPay getOneByOrder(Long id) {
        LambdaQueryWrapper<PsyConsultPay> wp = new LambdaQueryWrapper<>();
        wp.eq(PsyConsultPay::getOrderId, id);
        return psyConsultPayMapper.selectOne(wp);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(PsyConsultPay req) {
        psyConsultPayMapper.insert(req);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PsyConsultPay req) {
        psyConsultPayMapper.updateById(req);
    }
}
