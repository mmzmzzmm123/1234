package com.ruoyi.wechat.service.impl;

import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.domain.dto.WxPayDTO;
import com.ruoyi.common.enums.GaugeStatus;
import com.ruoyi.common.enums.OrderStatus;
import com.ruoyi.common.utils.OrderIdUtils;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.mapper.PsyOrderMapper;
import com.ruoyi.wechat.service.IWxpayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @User hogan
 * @Time 2022/10/24 17:22
 * @e-mail hkcugwh@163.com
 **/
@Service
@Slf4j
public class WxpayServiceImpl implements IWxpayService {

    @Resource
    private PsyOrderMapper psyOrderMapper;


    @Override
    public void pay(WxPayDTO wxPayDTO , LoginDTO loginUser) {

        //生成订单
        PsyOrder psyOrder = PsyOrder.builder()
                .orderId(OrderIdUtils.getOrderId())
                .amount(wxPayDTO.getAmount())
                .orderStatus(OrderStatus.CREATE.getValue())
                .gaugeStatus(GaugeStatus.UNFINISHED.getValue())
                .gaugeId(wxPayDTO.getGaugeId())
                .build();
        psyOrder.setCreateTime(new Date());
        psyOrder.setCreateBy(loginUser.getUserId());
        psyOrderMapper.insertPsyOrder(psyOrder);

        //todo 针对订单进行支付

    }

}
