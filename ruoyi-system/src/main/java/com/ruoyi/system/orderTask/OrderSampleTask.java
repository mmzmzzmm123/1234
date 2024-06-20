package com.ruoyi.system.orderTask;

import com.ruoyi.common.delayed.DelayedElement;
import com.ruoyi.common.delayed.Task;
import com.ruoyi.common.enums.PostOrderStatus;
import com.ruoyi.common.utils.StatusUtils;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.service.extra.BusPostOrderExtraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Description: 打样的延迟队列
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-19 18:20:54
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-19     张李鑫                     1.0         1.0 Version
 */

@Component
public class OrderSampleTask extends Task<Long> {

    private final long dayTime = 1000 * 60 * 60 * 24;

    private static final Logger log = LoggerFactory.getLogger(OrderSampleTask.class);

    @Value("${supply.quantity}")
    private Integer quantity;//设置集群数量 多集群的情况下定时任务只处理自己的任务


    @Autowired
    private OrderTimeoutTask orderTimeoutTask;

    private final ConcurrentSkipListSet<Long> orderIdCache;

    @Autowired
    private BusPostOrderExtraService postOrderExtraService;

    public OrderSampleTask() {
        super(1, true);
        this.orderIdCache = new ConcurrentSkipListSet<>();
    }

    @Override
    public void run() {
        log.info("SampleTask is run .....");
        init();
        while (true) {
            try {
                process();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }


    public void process() throws InterruptedException {
        DelayedElement<Long> element = getDelayQueue().take();// 阻塞等待直到元素可获取
        Long orderId = element.getElement();
        orderIdCache.remove(orderId);
        BusPostOrder busPostOrder = ((CheckOrderTimeoutAndSampling) element.getTaskAction()).process(orderId);
        Integer status = busPostOrder.getStatus();
        if (StatusUtils.hasStatus(status, PostOrderStatus.SHOP_SAMPLE.getValue())) {
            log.info("订单进入时效性检查队列 id:{} ", orderId);
            orderTimeoutTask.add(orderId, dayTime * busPostOrder.getValidityPeriod(), postOrderExtraService::orderTimeout);
        }
    }


    public synchronized void init() {
        init(postOrderExtraService::checkOrderTimeoutAndSampling);
    }

    /**
     * 初始化任务
     */
    public synchronized void init(CheckOrderTimeoutAndSampling checkOrder) {
        List<BusPostOrder> list = postOrderExtraService.findSampleOrder();
        for (BusPostOrder busPostOrder : list) {
            Long orderId = busPostOrder.getOrderId();
            if (orderId % quantity == 0 && orderIdCache.add(orderId)) {
                Date sampleTime = busPostOrder.getSampleTime();
                //加一天 Action is not a functional interface
                getDelayQueue().offer(new DelayedElement<>(busPostOrder.getOrderId(), new Date().getTime() - sampleTime.getTime() + dayTime, checkOrder));
            }
        }
    }
}
