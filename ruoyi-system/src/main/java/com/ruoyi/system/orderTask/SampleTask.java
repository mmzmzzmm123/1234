package com.ruoyi.system.orderTask;

import com.ruoyi.common.delayed.DelayedElement;
import com.ruoyi.common.delayed.Task;
import com.ruoyi.common.enums.PostOrderStatus;
import com.ruoyi.common.utils.StatusUtils;
import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.service.BusPostOrderExtraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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
public class SampleTask extends Task<Long> {

    private final long dayTime = 1000 * 60 * 60 * 24;

    private static final Logger log = LoggerFactory.getLogger(SampleTask.class);


    @Autowired
    private OrderTask orderTask;

    @Autowired
    private BusPostOrderExtraService postOrderExtraService;

    public SampleTask() {
        super(1, true);
    }

    @Override
    public void run() {
        log.info("SampleTask is run .....");
        init(postOrderExtraService::checkOrderTimeoutAndSampling);
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
        BusPostOrder busPostOrder = ((CheckOrderTimeoutAndSampling) element.getTaskAction()).process(orderId);
        Integer status = busPostOrder.getStatus();
        if (StatusUtils.hasStatus(status, PostOrderStatus.SHOP_SAMPLE.getValue())) {
            log.info("订单进入时效性检查队列 id:{} ", orderId);
            orderTask.add(orderId, dayTime * busPostOrder.getValidityPeriod(), postOrderExtraService::orderTimeout);
        }
    }


    /**
     * 初始化任务
     */
    public void init(CheckOrderTimeoutAndSampling checkOrder) {
        List<BusPostOrder> list = postOrderExtraService.findSampleOrder();
        for (BusPostOrder busPostOrder : list) {
            Date sampleTime = busPostOrder.getSampleTime();
            //加一天 Action is not a functional interface
            getDelayQueue().offer(new DelayedElement<>(busPostOrder.getOrderId(), new Date().getTime() - sampleTime.getTime() + dayTime, checkOrder));
        }


    }

}
