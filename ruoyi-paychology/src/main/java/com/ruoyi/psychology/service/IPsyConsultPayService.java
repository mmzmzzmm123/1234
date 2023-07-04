package com.ruoyi.psychology.service;

import java.util.List;
import com.ruoyi.psychology.domain.PsyConsultPay;

/**
 * 心理咨询订单支付信息Service接口
 * 
 * @author ruoyi
 * @date 2023-06-30
 */
public interface IPsyConsultPayService 
{

    PsyConsultPay getOneByOrder(String outTradeNo);

    void add(PsyConsultPay req);

    void update(PsyConsultPay req);

}
