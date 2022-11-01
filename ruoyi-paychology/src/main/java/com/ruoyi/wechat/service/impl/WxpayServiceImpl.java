package com.ruoyi.wechat.service.impl;

import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.domain.dto.WxPayDTO;
import com.ruoyi.common.enums.GaugeStatus;
import com.ruoyi.common.enums.OrderPayStatus;
import com.ruoyi.common.enums.OrderStatus;
import com.ruoyi.common.utils.OrderIdUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.domain.PsyOrderPay;
import com.ruoyi.gauge.mapper.PsyOrderMapper;
import com.ruoyi.gauge.service.IPsyOrderPayService;
import com.ruoyi.gauge.service.IPsyOrderService;
import com.ruoyi.wechat.service.IWxpayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @User hogan
 * @Time 2022/10/24 17:22
 * @e-mail hkcugwh@163.com
 **/
@Service
@Slf4j
public class WxpayServiceImpl implements IWxpayService {

    @Autowired
    private IPsyOrderService psyOrderService;

    @Autowired
    private IPsyOrderPayService psyOrderPayService;

    @Override
    public void pay(WxPayDTO wxPayDTO, LoginDTO loginUser) {

        String orderId = OrderIdUtils.getOrderId();

        //生成订单
        int id = generateOrder(wxPayDTO, loginUser, orderId);

        //生成订单支付信息
        generatePay(wxPayDTO, loginUser, id);

    }

    private void generatePay(WxPayDTO wxPayDTO, LoginDTO loginUser, int id) {
        PsyOrderPay psyOrderPay = PsyOrderPay.builder()
                .orderId(Long.valueOf(id))
                .amount(wxPayDTO.getAmount())
                .payStatus(OrderPayStatus.NEED_PAY.getValue())
                .payId(UUID.randomUUID().toString())
                .build();
        psyOrderPay.setCreateBy(loginUser.getUserId());
        psyOrderPayService.insertPsyOrderPay(psyOrderPay);
    }

    private int generateOrder(WxPayDTO wxPayDTO, LoginDTO loginUser, String orderId) {
        PsyOrder psyOrder = PsyOrder.builder()
                .orderId(orderId)
                .amount(wxPayDTO.getAmount())
                .orderStatus(OrderStatus.CREATE.getValue())
                .gaugeStatus(GaugeStatus.UNFINISHED.getValue())
                .gaugeId(wxPayDTO.getGaugeId())
                .build();
        psyOrder.setCreateBy(loginUser.getUserId());
        int id = psyOrderService.insertPsyOrder(psyOrder);
        return id;
    }


}
