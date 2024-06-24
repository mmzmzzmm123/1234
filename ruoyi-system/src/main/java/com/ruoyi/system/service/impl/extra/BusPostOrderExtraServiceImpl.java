package com.ruoyi.system.service.impl.extra;

import com.ruoyi.common.enums.OrderSampleType;
import com.ruoyi.common.enums.OrderValidityStatus;
import com.ruoyi.common.enums.PostOrderStatus;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StatusUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.portal.form.BusPostCursorForm;
import com.ruoyi.portal.form.BusPostOrderForm;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.domain.BusTransactions;
import com.ruoyi.system.manager.PayManager;
import com.ruoyi.system.mapper.extra.BusPostOrderExtraMapper;
import com.ruoyi.system.service.extra.BusOrderAssignmentsExtraService;
import com.ruoyi.system.service.extra.BusPostOrderExtraService;
import com.ruoyi.system.service.extra.IPostOrderImagesExtraService;
import com.ruoyi.system.service.impl.BusPostOrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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


    @Autowired
    private IPostOrderImagesExtraService postOrderImagesExtraService;

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
     * @param busPostOrderForm
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusPostOrder postOrder(BusPostOrderForm busPostOrderForm) {
        BusPostOrder busPostOrder = new BusPostOrder();
        BeanUtils.copyProperties(busPostOrderForm, busPostOrder);

        busPostOrder.setOrderId(null);
        busPostOrder.setMerchantId(SecurityUtils.getLoginUser().getUser().getUserId());

        busPostOrder.setStatus(PostOrderStatus.CREATED.getValue());
        busPostOrder.setOrderValidityStatus(OrderValidityStatus.NORMAL.getValue());
        int orderCount = insertBusPostOrder(busPostOrder);
        int imageCount = postOrderImagesExtraService.insertPostOrderImageBatch(busPostOrderForm.getImageList(), busPostOrder.getOrderId());
        int orderAssignmentCount = busOrderAssignmentsExtraService.createByOrder(busPostOrder);
        //只要有不成功的返回的都是0
        return orderCount * imageCount * orderAssignmentCount > 0 ? busPostOrder : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(BusPostOrderForm busPostOrderForm) {
        Integer payType = busPostOrderForm.getPayType();
        Long orderId = busPostOrderForm.getOrderId();
        BusPostOrder busPostOrder = selectBusPostOrderByOrderId(orderId);
        checkUser(busPostOrder);

        if (StatusUtils.hasStatus(busPostOrder.getStatus(), PostOrderStatus.PAID.getValue())) {
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
        busPostOrder.setStatus(StatusUtils.updateStatus(oldStatus, PostOrderStatus.PAID.getValue()));
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
        return super.updateBusPostOrder(BeanUtils.convert(busPostOrderForm,BusPostOrder.class));
    }


    @Override
    public List<BusPostOrder> findOrderListByUserId(BusPostOrderForm busPostOrderForm) {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            throw new RuntimeException("用户信息异常");
        }
        Boolean orderPending = busPostOrderForm.getOrderPending();
        //在状态中 从已创建这个状态开始到后面都是连续的 已接单对应的10进制的数是128 所以当状态>=128的情况下 当前商品一定处于已接单的状态
        Integer fullBitStatus = StatusUtils.getIntByBit(PostOrderStatus.ORDER_RECEIVED.getValue());//获取在未接单的状态下的最大状态值

        //如果已接单状态区间就是 fullBitStatus(128)-> （1<<PostOrderStatus.COMPLETED.getValue()）-1 这个区间
        //如果是未接单状态就是 0-128（(1<<PostOrderStatus.ORDER_RECEIVED.getValue())-1）这样的状态区间
        Integer startStatus = null;
        Integer endStatus = null;
        if (null != orderPending) {
            startStatus = orderPending ? fullBitStatus : 0;
            endStatus = orderPending ? StatusUtils.getFullBitStatus(PostOrderStatus.COMPLETED.getValue()) : fullBitStatus;
        }
        return busPostOrderExtraMapper.findOrderListByUserId(startStatus, endStatus, userId);
    }

    public static void main(String[] args) {
        int value = PostOrderStatus.ORDER_RECEIVED.getValue();
        Integer fullBitStatus = StatusUtils.getIntByBit(value);
        System.out.println(fullBitStatus);
    }

    @Override
    public int confirm(Long orderId) {
        checkUser(orderId);
        BusPostOrder busPostOrder = selectBusPostOrderByOrderId(orderId);
        if (StatusUtils.hasStatus(busPostOrder.getStatus(), PostOrderStatus.CONFIRM_SHIPMENT.getValue())) {
            throw new RuntimeException("已同意发货");
        }
        Integer newStatus = StatusUtils.updateStatus(busPostOrder.getStatus(), PostOrderStatus.CONFIRM_SHIPMENT.getValue());
        busPostOrder = new BusPostOrder();
        busPostOrder.setStatus(newStatus);
        busPostOrder.setOrderId(orderId);
        //todo:这里需要把订单加入到延迟队列中
        return updateBusPostOrder(busPostOrder);
    }

    /**
     * 查询需要打样切还没打样的订单数据
     *
     * @return
     */
    @Override
    public List<BusPostOrder> findSampleOrder(List<Long>ids) {
        BusPostOrder busPostOrder = new BusPostOrder();
        busPostOrder.setSample(OrderSampleType.SAMPLING_REQUIRED.getValue());//需要打样
        busPostOrder.setOrderValidityStatus(OrderValidityStatus.NORMAL.getValue());//订单正常
        List<Integer> statusListExcluded = StatusUtils.getStatusListExcluded(PostOrderStatus.SHOP_SAMPLE.getValue());//没有打样
        return busPostOrderExtraMapper.findSampleOrder(busPostOrder, statusListExcluded, new Date(),ids);
    }

    /**
     * 订单检查 如果超时订单已经打样 什么都不做 如果没有打样更新订单异常状态
     *
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
            updateOrderValidity(orderId, OrderValidityStatus.SAMPLE_TIMEOUT.getValue());
        }
        return oldOrder;
    }

    private void updateOrderValidity(Long orderId, Integer status) {
        BusPostOrder busPostOrder = new BusPostOrder();
        busPostOrder.setOrderId(orderId);
        busPostOrder.setOrderValidityStatus(status);
        updateBusPostOrder(busPostOrder);
    }

    /**
     * 检查订单是否超时
     *
     * @param orderId
     * @return
     */
    @Override
    public BusPostOrder orderTimeout(Long orderId) {
        //todo:这里订单超时有问题 需要修改
        BusPostOrder oldOrder = selectBusPostOrderByOrderId(orderId);
        Integer status = oldOrder.getStatus();
        if (!StatusUtils.hasStatus(status, PostOrderStatus.CONFIRM_SHIPMENT.getValue())) {
            updateOrderValidity(orderId, OrderValidityStatus.ORDER_TIMEOUT.getValue());
        }
        return oldOrder;
    }

    /**
     * 游标分页
     *
     * @param busPostOrderForm
     * @return
     */
    @Override
    public List<BusPostOrder> list(BusPostCursorForm busPostOrderForm) {
        Integer limit = busPostOrderForm.getLimit();
        limit = limit == null ? 20 : limit;
        if (limit > 100) {
            throw new RuntimeException("参数异常分页参数过大");
        }
        return busPostOrderExtraMapper.cursorList(busPostOrderForm.getLastId(),limit, StatusUtils.getIntByBit(PostOrderStatus.ORDER_RECEIVED.getValue()));
    }


    /**
     * 获取未接单的状态
     *
     * @return
     */
    public List<Integer> getPendingStatus() {
        return StatusUtils.getStatusListExcluded(PostOrderStatus.values().length,
                PostOrderStatus.COMPLETED.getValue(),
                PostOrderStatus.SHIPPED_RETURNED.getValue(),
                PostOrderStatus.RETURN_REQUESTED.getValue(),
                PostOrderStatus.SHIPPED_RECEIVED.getValue(),
                PostOrderStatus.CONFIRM_SHIPMENT.getValue(),
                PostOrderStatus.SHOP_SAMPLE_CONFIRMED.getValue(),
                PostOrderStatus.SHOP_SAMPLE.getValue(),
                PostOrderStatus.ORDER_RECEIVED.getValue(),
                PostOrderStatus.TIMEOUT.getValue(),
                PostOrderStatus.CANCELLED.getValue(),
                PostOrderStatus.CANCEL_REQUESTED.getValue()
        );
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
