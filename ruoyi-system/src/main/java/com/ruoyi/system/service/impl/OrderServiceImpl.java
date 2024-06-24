package com.ruoyi.system.service.impl;

import com.ruoyi.common.enums.OrderAssignmentStatus;
import com.ruoyi.common.enums.OrderValidityStatus;
import com.ruoyi.common.enums.PostOrderStatus;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StatusUtils;
import com.ruoyi.portal.form.BusPostOrderForm;
import com.ruoyi.system.domain.BusOrderAssignments;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.domain.BusTransactions;
import com.ruoyi.system.domain.BusWallets;
import com.ruoyi.system.manager.PayManager;
import com.ruoyi.system.service.OrderService;
import com.ruoyi.system.service.extra.BusOrderAssignmentsExtraService;
import com.ruoyi.system.service.extra.BusPostOrderExtraService;
import com.ruoyi.system.service.extra.BusWalletsExtraService;
import com.ruoyi.system.service.impl.extra.BusTransactionsExtraServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-20 16:15:56
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-20     张李鑫                     1.0         1.0 Version
 */
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private BusOrderAssignmentsExtraService busOrderAssignmentsExtraService;


    @Autowired
    private BusPostOrderExtraService postOrderExtraService;

    @Autowired
    private BusWalletsExtraService busWalletsExtraService;


    @Autowired
    private PayManager payManager;

    @Autowired
    private BusTransactionsExtraServiceImpl busTransactionsExtraService;


    /**
     * 抢单
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean takeOrder(Long orderId) {
        BusWallets busWallets = busWalletsExtraService.selectBusWalletsByUserIdForUpdate();
        BusPostOrder busPostOrder = postOrderExtraService.selectBusPostOrderByOrderId(orderId);
        hasMoney(busWallets, busPostOrder);
        BusOrderAssignments busOrderAssignments = getBusOrderAssignments(busPostOrder);
        boolean takeOrderSuccess =
                busOrderAssignmentsExtraService.takeOrder(
                        SecurityUtils.getLoginUser().getUserId(),
                        OrderAssignmentStatus.ACCEPTED.getValue(),
                        busOrderAssignments.getStatus(),
                        busOrderAssignments.getAssignmentId()
                );
        return takeOrderSuccess && busWalletsExtraService.deduction(busWallets, busPostOrder.getSecurityDeposit());
    }


    private void hasMoney(BusWallets busWallets, BusPostOrder busPostOrder) {
        Long securityDeposit = busPostOrder.getSecurityDeposit();
        if (busWallets.getBalance() < securityDeposit) {
            throw new RuntimeException("钱包余额不足");
        }
    }

    private BusOrderAssignments getBusOrderAssignments(BusPostOrder busPostOrder) {
        List<BusOrderAssignments> list = selectOrderAssignmentsByOrderIdAndMerchantId(busPostOrder);
        if (list.size() > 1) {
            throw new RuntimeException("订单异常");
        }
        if (list.isEmpty()) {
            throw new RuntimeException("抢单失败");
        }
        return list.get(0);
    }

    private List<BusOrderAssignments> selectOrderAssignmentsByOrderIdAndMerchantId(BusPostOrder busPostOrder) {
        BusOrderAssignments busOrderAssignments = new BusOrderAssignments();
        busOrderAssignments.setOrderId(busPostOrder.getOrderId());
        busOrderAssignments.setStatus(OrderAssignmentStatus.UNACCEPTED.getValue());
        return busOrderAssignmentsExtraService.selectBusOrderAssignmentsList(busOrderAssignments);
    }


    @Override
    public int confirm(Long orderId) {
        postOrderExtraService.checkUser(orderId);
        BusPostOrder busPostOrder = postOrderExtraService.selectBusPostOrderByOrderId(orderId);
        if (StatusUtils.hasStatus(busPostOrder.getStatus(), PostOrderStatus.CONFIRM_SHIPMENT.getValue())) {
            throw new RuntimeException("已同意发货");
        }
        Integer newStatus = StatusUtils.updateStatus(busPostOrder.getStatus(), PostOrderStatus.CONFIRM_SHIPMENT.getValue());
        busPostOrder = new BusPostOrder();
        busPostOrder.setStatus(newStatus);
        busPostOrder.setOrderId(orderId);
        return postOrderExtraService.updateBusPostOrder(busPostOrder);
    }

    @Override
    public int sample(Long orderId) {
        BusPostOrder busPostOrder = postOrderExtraService.selectBusPostOrderByOrderId(orderId);
        Date sampleTime = busPostOrder.getSampleTime();
        Integer orderValidityStatus = busPostOrder.getOrderValidityStatus();

        if (orderValidityStatus == OrderValidityStatus.NORMAL.getValue()) {
            // 加一天
            long time = sampleTime.getTime() + 1000 * 60 * 60 * 24;
            if (new Date().getTime() > time) {
                busPostOrder.setOrderValidityStatus(OrderValidityStatus.SAMPLE_TIMEOUT.getValue());
                postOrderExtraService.updateBusPostOrder(busPostOrder);
                return OrderValidityStatus.SAMPLE_TIMEOUT.getValue();
            }else {
                BusOrderAssignments busOrderAssignments=busOrderAssignmentsExtraService.selectByOrderId(orderId);
                busOrderAssignments.setStatus(StatusUtils.updateStatus(busOrderAssignments.getStatus(),OrderAssignmentStatus.SHOP_SAMPLE.getValue()));
                busPostOrder.setStatus(StatusUtils.updateStatus(busPostOrder.getStatus(),OrderAssignmentStatus.SHOP_SAMPLE.getValue()));
                postOrderExtraService.updateBusPostOrder(busPostOrder);
                busOrderAssignmentsExtraService.updateBusOrderAssignments(busOrderAssignments);
            }
        }
        throw new RuntimeException(OrderValidityStatus.getRemarkByValue(orderValidityStatus));
    }

    /**
     * 支付订单
     *
     * @param busPostOrderForm
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(BusPostOrderForm busPostOrderForm) {
        Integer payType = busPostOrderForm.getPayType();
        Long orderId = busPostOrderForm.getOrderId();
        BusPostOrder busPostOrder = postOrderExtraService.selectBusPostOrderByOrderId(orderId);
        postOrderExtraService.checkUser(busPostOrder);

        if (StatusUtils.hasStatus(busPostOrder.getStatus(), PostOrderStatus.PAID.getValue())) {
            throw new RuntimeException("无法支付他人订单");
        }
        BusTransactions busTransactions = new BusTransactions();
        busTransactionsExtraService.insertBusTransactions(busTransactions);
        //支付成功 更新状态 新增支付流水记录
        postOrderExtraService.updateBusPostOrder(buildPayOrder(orderId, payType, busPostOrder.getStatus()));
        boolean paySuccess = payManager.pay(payType, busPostOrder.getAmount());
        if (!paySuccess) {
            throw new RuntimeException("支付失败");
        }
        busTransactionsExtraService.updateBusTransactions(busTransactions);
    }


    private BusPostOrder buildPayOrder(Long orderId, Integer payType, Integer oldStatus) {
        BusPostOrder busPostOrder = new BusPostOrder();
        busPostOrder.setOrderId(orderId);
        busPostOrder.setPayType(payType);
        busPostOrder.setStatus(StatusUtils.updateStatus(oldStatus, PostOrderStatus.PAID.getValue()));
        return busPostOrder;
    }
}
