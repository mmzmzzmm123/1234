package com.renxin.psychology.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renxin.psychology.constant.ConsultConstant;
import org.springframework.stereotype.Service;
import com.renxin.psychology.mapper.PsyConsultPayMapper;
import com.renxin.psychology.domain.PsyConsultPay;
import com.renxin.psychology.service.IPsyConsultPayService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 心理咨询订单支付信息Service业务层处理
 * 
 * @author renxin
 * @date 2023-06-30
 */
@Service
public class PsyConsultPayServiceImpl implements IPsyConsultPayService 
{
    @Resource
    private PsyConsultPayMapper psyConsultPayMapper;

    @Override
    public PsyConsultPay getOneByOrder(String outTradeNo) {
        LambdaQueryWrapper<PsyConsultPay> wp = new LambdaQueryWrapper<>();
        wp.eq(PsyConsultPay::getOutTradeNo, outTradeNo);
        wp.eq(PsyConsultPay::getStatus, ConsultConstant.PAY_STATUE_PENDING);
        wp.last("ORDER BY create_time DESC LIMIT 1");
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
