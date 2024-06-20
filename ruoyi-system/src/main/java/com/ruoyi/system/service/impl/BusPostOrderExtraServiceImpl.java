package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.OrderSampleType;
import com.ruoyi.common.enums.OrderValidityStatus;
import com.ruoyi.common.enums.PostOrderStatus;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StatusUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.portal.form.BusPostOrderForm;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.domain.BusTransactions;
import com.ruoyi.system.manager.PayManager;
import com.ruoyi.system.mapper.BusPostOrderExtraMapper;
import com.ruoyi.system.orderTask.SampleTask;
import com.ruoyi.system.service.BusOrderAssignmentsExtraService;
import com.ruoyi.system.service.BusPostOrderExtraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private static final Logger log = LoggerFactory.getLogger(BusPostOrderExtraServiceImpl.class);

    @Autowired
    private PayManager payManager;

    @Autowired
    private BusTransactionsExtraServiceImpl busTransactionsExtraService;


    @Autowired
    private BusPostOrderExtraMapper busPostOrderExtraMapper;

    @Autowired
    private BusOrderAssignmentsExtraService busOrderAssignmentsExtraService;

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
        int orderCount = insertBusPostOrder(busPostOrder);
        int orderAssignmentCount = busOrderAssignmentsExtraService.createByOrder(busPostOrder);
        //只要有不成功的返回的都是0
        return orderCount * orderAssignmentCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(BusPostOrderForm busPostOrderForm) {
        Integer payType = busPostOrderForm.getPayType();
        Long orderId = busPostOrderForm.getOrderId();
        BusPostOrder busPostOrder = selectBusPostOrderByOrderId(orderId);
        checkUser(busPostOrder);

        if (StatusUtils.hasStatus(busPostOrder.getStatus(), PostOrderStatus.pay.getValue())) {
            throw new RuntimeException("无法支付他人订单");
        }

        BusTransactions busTransactions = new BusTransactions();
        busTransactionsExtraService.insertBusTransactions(busTransactions);
        //支付成功 更新状态 新增支付流水记录
        updateBusPostOrder(buildPayOrder(orderId, payType, busPostOrder.getStatus()));

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
        busPostOrder.setStatus(StatusUtils.updateStatus(oldStatus, PostOrderStatus.pay.getValue()));
        return busPostOrder;
    }

    @Override
    public int updateOrderByUserId(BusPostOrderForm busPostOrderForm) {
        Long orderId = busPostOrderForm.getOrderId();
        BusPostOrder busPostOrder = selectBusPostOrderByOrderId(orderId);
        checkUser(busPostOrder);
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

    @Override
    public int confirm(Long orderId) {
        checkUser(orderId);
        BusPostOrder busPostOrder = selectBusPostOrderByOrderId(orderId);
//        if (!StatusUtils.hasStatus(busPostOrder.getStatus(), PostOrderStatus.IN_PRODUCTION_COMPLETED.getValue())) {
//            throw new RuntimeException("制作未完成");
//        }

        if (StatusUtils.hasStatus(busPostOrder.getStatus(), PostOrderStatus.CONFIRM.getValue())) {
            throw new RuntimeException("已同意发货");
        }
        Integer newStatus = StatusUtils.updateStatus(busPostOrder.getStatus(), PostOrderStatus.CONFIRM.getValue());
        busPostOrder = new BusPostOrder();
        busPostOrder.setStatus(newStatus);
        busPostOrder.setOrderId(orderId);
        return updateBusPostOrder(busPostOrder);
    }

    /**
     * 查询需要打样切还没打样的订单数据
     *
     * @return
     */
    @Override
    public List<BusPostOrder> findSampleOrder() {
        BusPostOrder busPostOrder = new BusPostOrder();
        busPostOrder.setSample(OrderSampleType.SAMPLING_REQUIRED.getValue());//需要打样
        busPostOrder.setOrderValidityStatus(OrderValidityStatus.NORMAL.getValue());//订单正常
        List<Integer> statusListExcluded = StatusUtils.getStatusListExcluded(PostOrderStatus.SHOP_SAMPLE.getValue());//没有打样
        return busPostOrderExtraMapper.findSampleOrder(busPostOrder, statusListExcluded);
    }

    /**
     * 订单检查 如果超时订单已经打样 什么都不做 如果没有打样更新订单异常状态
     * @param orderId 订单id
     * @return 订单是否超时
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusPostOrder checkOrderTimeoutAndSampling(Long orderId) {
        BusPostOrder oldOrder = selectBusPostOrderByOrderId(orderId);
        Integer status = oldOrder.getStatus();
        if (!StatusUtils.hasStatus(status, PostOrderStatus.SHOP_SAMPLE.getValue())) {
            log.info("订单打样超时 id:{} 超时", orderId);
            updateOrderValidity(orderId);
        }
        return oldOrder;
    }

    private void updateOrderValidity(Long orderId){
        BusPostOrder busPostOrder = new BusPostOrder();
        busPostOrder.setOrderId(orderId);
        busPostOrder.setOrderValidityStatus(OrderValidityStatus.SAMPLE_TIMEOUT.getValue());
        updateBusPostOrder(busPostOrder);
    }

    /**
     * 检查订单是否超时
     * @param orderId
     * @return
     */
    @Override
    public BusPostOrder orderTimeout(Long orderId) {
        BusPostOrder oldOrder = selectBusPostOrderByOrderId(orderId);
        Integer status = oldOrder.getStatus();
        if (!StatusUtils.hasStatus(status, PostOrderStatus.SHIPPED.getValue())) {
            updateOrderValidity(orderId);
        }
        return oldOrder;
    }

    /**
     * 获取未接单的状态
     *
     * @return
     */
    public List<Integer> getPendingStatus() {
        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(PostOrderStatus.init.getValue());//创建
        statusList.add(StatusUtils.updateStatus(PostOrderStatus.init.getValue(), PostOrderStatus.pay.getValue()));//已支付
        return statusList;
    }

    /**
     * 获取已接单的数据
     *
     * @return
     */
    public List<Integer> getReceivedStatus() {
        return StatusUtils.getStatusListWithIncluded(PostOrderStatus.values().length, PostOrderStatus.ORDER_RECEIVED.getValue());
    }


    /**
     * 检查当前操作人是否是订单发布人
     *
     * @param busPostOrder
     * @return
     */
    private void checkUser(BusPostOrder busPostOrder) {
        Long merchantId = busPostOrder.getMerchantId();
        Long userId = SecurityUtils.getUserId();
        if (!merchantId.equals(userId)) {
            throw new RuntimeException("无法操作他人发布的订单");
        }
    }

    private void checkUser(Long orderId) {
        checkUser(selectBusPostOrderByOrderId(orderId));
    }
}
