package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.OrderValidityStatus;
import com.ruoyi.common.enums.PostOrderStatus;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StatusUtils;
import com.ruoyi.portal.form.PayForm;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.domain.BusTransactions;
import com.ruoyi.system.manager.PayManager;
import com.ruoyi.system.service.BusPostOrderExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 17:21:05
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
@Component("busPostOrderExtraServiceImpl")
public class BusPostOrderExtraServiceImpl extends BusPostOrderServiceImpl implements BusPostOrderExtraService {

    @Autowired
    private PayManager payManager;

    @Autowired
    private BusTransactionsExtraServiceImpl busTransactionsExtraService;


    /**
     * 新增订单
     *
     * @param busPostOrder 订单
     * @return 结果
     */
    @Override
    public int insertBusPostOrder(BusPostOrder busPostOrder) {
        busPostOrder.setCreatedAt(new Date());
        busPostOrder.setUpdatedAt(new Date());
        return super.insertBusPostOrder(busPostOrder);
    }

    /**
     * 修改订单
     *
     * @param busPostOrder 订单
     * @return 结果
     */
    @Override
    public int updateBusPostOrder(BusPostOrder busPostOrder) {
        busPostOrder.setUpdatedAt(new Date());
        return super.updateBusPostOrder(busPostOrder);
    }

    /**
     * 发布订单
     *
     * @param busPostOrder
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int postOrder(BusPostOrder busPostOrder) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        Long userId = user.getUserId();
        busPostOrder.setMerchantId(userId);
        busPostOrder.setStatus(PostOrderStatus.init.getValue());
        busPostOrder.setOrderValidityStatus(OrderValidityStatus.NORMAL.getValue());
        return insertBusPostOrder(busPostOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(PayForm payForm) {
        Integer payType = payForm.getPayType();
        Long orderId = payForm.getOrderId();
        BusPostOrder busPostOrder = selectBusPostOrderByOrderId(orderId);
        boolean paySuccess = payManager.pay(payType, busPostOrder.getAmount());
        if (!paySuccess) {
            throw new RuntimeException("支付失败");
        }
        //支付成功 更新状态 新增支付流水记录
        BusPostOrder busPostOrder1 = new BusPostOrder();
        busPostOrder1.setOrderId(orderId);
        busPostOrder.setStatus(StatusUtils.updateStatus(busPostOrder.getStatus(), PostOrderStatus.pay.getValue()));
        updateBusPostOrder(busPostOrder1);

        BusTransactions busTransactions = new BusTransactions();


//        busTransactionsExtraService.insertBusTransactions();
    }

}
