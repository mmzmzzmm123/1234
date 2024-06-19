package com.renxin.psychology.service;

import com.renxin.psychology.domain.PsyConsultPay;

/**
 * 心理咨询订单支付信息Service接口
 * 
 * @author renxin
 * @date 2023-06-30
 */
public interface IPsyConsultPayService 
{

    PsyConsultPay getOneByOrder(String outTradeNo);

    void add(PsyConsultPay req);

    void update(PsyConsultPay req);

}
