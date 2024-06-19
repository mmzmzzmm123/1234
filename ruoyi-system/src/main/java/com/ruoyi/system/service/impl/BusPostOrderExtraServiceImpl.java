package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.OrderValidityStatus;
import com.ruoyi.common.enums.PostOrderStatus;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StatusUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.portal.form.BusPostOrderForm;
import com.ruoyi.portal.form.PayForm;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.domain.BusTransactions;
import com.ruoyi.system.manager.PayManager;
import com.ruoyi.system.mapper.BusPostOrderExtraMapper;
import com.ruoyi.system.service.BusPostOrderExtraService;
import com.wechat.pay.java.service.profitsharing.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Service("BusPostOrderExtraServiceImpl")
@Primary
public class BusPostOrderExtraServiceImpl extends BusPostOrderServiceImpl implements BusPostOrderExtraService {

    @Autowired
    private PayManager payManager;

    @Autowired
    private BusTransactionsExtraServiceImpl busTransactionsExtraService;


    @Autowired
    private BusPostOrderExtraMapper busPostOrderExtraMapper;

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
        busPostOrder.setOrderId(null);
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
        check(busPostOrder);


        BusTransactions busTransactions = new BusTransactions();
        busTransactionsExtraService.insertBusTransactions(busTransactions);
        //支付成功 更新状态 新增支付流水记录
        BusPostOrder busPostOrder1 = new BusPostOrder();
        busPostOrder1.setOrderId(orderId);
        busPostOrder.setStatus(StatusUtils.updateStatus(busPostOrder.getStatus(), PostOrderStatus.pay.getValue()));
        updateBusPostOrder(busPostOrder1);

        boolean paySuccess = payManager.pay(payType, busPostOrder.getAmount());
        if (!paySuccess) {
            throw new RuntimeException("支付失败");
        }

        busTransactionsExtraService.updateBusTransactions(busTransactions);

    }

    @Override
    public int updateOrderByUserId(BusPostOrderForm busPostOrderForm) {
        Long userId = SecurityUtils.getUserId();
        Long orderId = busPostOrderForm.getOrderId();
        BusPostOrder busPostOrder = super.selectBusPostOrderByOrderId(orderId);
        if (!busPostOrder.getMerchantId().equals(userId)) {
            throw new RuntimeException("无法修改他们订单");
        }

        if (StatusUtils.hasStatus(busPostOrder.getStatus(), PostOrderStatus.ORDER_RECEIVED.getValue())) {
            throw new RuntimeException("已被接单 无法修改");
        }
        busPostOrder = new BusPostOrder();
        BeanUtils.copyProperties(busPostOrderForm, busPostOrder);
        return super.updateBusPostOrder(busPostOrder);
    }


    @Override
    public List<BusPostOrder> findOrderListByUserId(BusPostOrderForm busPostOrderForm) {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            throw new RuntimeException("用户信息异常");
        }
        PageUtils.startPage();
        Boolean orderPending = busPostOrderForm.getOrderPending();
        //是否已经接单   如果没有状态字段查询所有订单 如果有的话根据状态查询
        List<Integer> statusList = orderPending == null ? new ArrayList<>() : (orderPending ? getPendingStatus() : getReceivedStatus());
        return busPostOrderExtraMapper.findOrderListByUserId(statusList, userId);
    }

    /**
     * 获取未接单的状态
     *
     * @return
     */
    public List<Integer> getPendingStatus() {
        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(PostOrderStatus.init.getValue());//创建
        statusList.add(StatusUtils.updateStatus(PostOrderStatus.init.getValue(),PostOrderStatus.pay.getValue()));//已支付
        return statusList;
    }

    /**
     * 获取已接单的数据
     *
     * @return
     */
    public List<Integer> getReceivedStatus() {
        return StatusUtils.getStatusListWithIncluded(PostOrderStatus.values().length,PostOrderStatus.ORDER_RECEIVED.getValue());
    }


    private void check(BusPostOrder busPostOrder) {
        Long merchantId = busPostOrder.getMerchantId();
        Long userId = SecurityUtils.getUserId();
        if (merchantId.equals(userId)) {
            throw new RuntimeException("无法支付他人订单");
        }
        if (StatusUtils.hasStatus(busPostOrder.getStatus(), PostOrderStatus.pay.getValue())) {
            throw new RuntimeException("无法支付他人订单");
        }
    }
}
