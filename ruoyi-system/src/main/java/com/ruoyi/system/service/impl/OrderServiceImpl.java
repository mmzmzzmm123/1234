package com.ruoyi.system.service.impl;

import com.ruoyi.common.enums.OrderAssignmentStatus;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.BusOrderAssignments;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.domain.BusWallets;
import com.ruoyi.system.service.OrderService;
import com.ruoyi.system.service.extra.BusOrderAssignmentsExtraService;
import com.ruoyi.system.service.extra.BusPostOrderExtraService;
import com.ruoyi.system.service.extra.BusWalletsExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
