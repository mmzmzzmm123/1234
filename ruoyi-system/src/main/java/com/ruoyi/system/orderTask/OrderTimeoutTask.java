package com.ruoyi.system.orderTask;

import com.ruoyi.common.delayed.DelayedElement;
import com.ruoyi.common.delayed.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Description: 订单时效的延迟队列
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-20 10:18:47
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-20     张李鑫                     1.0         1.0 Version
 */
@Component
public class OrderTimeoutTask extends Task<Long> {
    private static final Logger log = LoggerFactory.getLogger(OrderTimeoutTask.class);
    public OrderTimeoutTask() {
        super(1, true);
    }

    @Override
    public void run() {
        log.info("OrderTask is run .....");
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
        ((OrderTimeout) element.getTaskAction()).orderTimeout(orderId);
        log.info("订单打样时效检查 id:{}", orderId);
    }




    public void add(Long orderId, long time, OrderTimeout orderTimeout) {
        getDelayQueue().offer(new DelayedElement<>(orderId, time, orderTimeout));
    }

}
