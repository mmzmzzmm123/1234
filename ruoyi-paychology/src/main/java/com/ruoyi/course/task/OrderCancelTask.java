package com.ruoyi.course.task;

import com.ruoyi.course.constant.CourConstant;
import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.service.ICourOrderService;
import com.ruoyi.gauge.constant.GaugeConstant;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.service.IPsyOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * 订单30分钟未支付，自动修改订单状态为已取消
 */
@Component
public class OrderCancelTask extends TimerTask {

    private static final Logger log = LoggerFactory.getLogger(OrderCancelTask.class);

    private Integer orderId;

    private String module;

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setModule(String module) {
        this.module = module;
    }


    private static ICourOrderService courOrderService;
    @Autowired
    public void setICourOrderService(ICourOrderService courOrderService) {
        OrderCancelTask.courOrderService = courOrderService;
    }

    private static IPsyOrderService psyOrderService;
    @Autowired
    public void setIPsyOrderService(IPsyOrderService psyOrderService) {
        OrderCancelTask.psyOrderService = psyOrderService;
    }
    @Override
    public void run() {
        log.info("30分钟未支付，自动修改订单状态为已取消");
        if (CourConstant.MODULE_COURSE.equals(module)) {
           CourOrder courOrder =  courOrderService.selectCourOrderById(orderId);
           if (courOrder.getStatus() == CourConstant.COUR_ORDER_STATUE_CREATED) {
               courOrder.setStatus(CourConstant.COUR_ORDER_STATUE_CANCELED);
               courOrderService.updateCourOrder(courOrder);
           }
        } else if (GaugeConstant.MODULE_GAUGE.equals(module)) {
            PsyOrder psyOrder = psyOrderService.selectPsyOrderById(orderId);
            if (psyOrder.getOrderStatus() == GaugeConstant.GAUGE_ORDER_STATUE_CREATED) {
                psyOrder.setOrderStatus(GaugeConstant.GAUGE_ORDER_STATUE_CANCELED);
                psyOrderService.updatePsyOrder(psyOrder);
            }
        }
        log.info("{}订单id={} 自动修改订单状态为已取消，操作已完成", module, orderId);
    }
}
