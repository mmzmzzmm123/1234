package com.ruoyi.system.orderTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-20 11:25:31
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-20     张李鑫                     1.0         1.0 Version
 */
@Component
public class OrderTask {

    private static final Logger log = LoggerFactory.getLogger(OrderTask.class);

    @Value("${supply.quantity}")
    private Integer quantity;//设置集群数量 多集群的情况下定时任务只处理自己的任务

    @Value("${task.enable}")
    public boolean taskEnable;

    @Autowired
    private OrderSampleTask orderSampleTask;
    /**
     *  定期轮询需要打样的订单
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void findSampleOrder() {
        if (!taskEnable) {
            return;
        }
        orderSampleTask.init();
    }
}
