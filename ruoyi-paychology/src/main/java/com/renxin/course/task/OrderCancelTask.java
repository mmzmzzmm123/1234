//package com.renxin.course.task;
//
//import com.renxin.course.constant.CourConstant;
//import com.renxin.course.domain.CourOrder;
//import com.renxin.course.service.ICourOrderService;
//import com.renxin.gauge.constant.GaugeConstant;
//import com.renxin.gauge.domain.PsyOrder;
//import com.renxin.gauge.service.IPsyOrderService;
//import com.renxin.psychology.constant.ConsultConstant;
//import com.renxin.psychology.service.IPsyConsultOrderService;
//import com.renxin.psychology.vo.PsyConsultOrderVO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.TimerTask;
//
///**
// * 订单30分钟未支付，自动修改订单状态为已取消
// */
//@Component
//public class OrderCancelTask extends TimerTask {
//
//    private static final Logger log = LoggerFactory.getLogger(OrderCancelTask.class);
//
//    private Integer orderId;
//
//    private String module;
//
//    private Long id;
//
//    public void setOrderId(Integer orderId) {
//        this.orderId = orderId;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setModule(String module) {
//        this.module = module;
//    }
//
//
//    private static ICourOrderService courOrderService;
//    @Autowired
//    public void setICourOrderService(ICourOrderService courOrderService) {
//        OrderCancelTask.courOrderService = courOrderService;
//    }
//
//    private static IPsyOrderService psyOrderService;
//    @Autowired
//    public void setIPsyOrderService(IPsyOrderService psyOrderService) {
//        OrderCancelTask.psyOrderService = psyOrderService;
//    }
//
//    @Resource
//    private IPsyConsultOrderService psyConsultOrderService;
//
//    @Override
//    public void run() {
//        log.info("30分钟未支付，自动修改订单状态为已取消");
//        if (CourConstant.MODULE_COURSE.equals(module)) {
//           CourOrder courOrder =  courOrderService.selectCourOrderById(orderId);
//           if (courOrder.getStatus() == CourConstant.COUR_ORDER_STATUE_CREATED) {
//               courOrder.setStatus(CourConstant.COUR_ORDER_STATUE_CANCELED);
//               courOrderService.updateCourOrder(courOrder);
//           }
//        } else if (GaugeConstant.MODULE_GAUGE.equals(module)) {
//            PsyOrder psyOrder = psyOrderService.selectPsyOrderById(orderId);
//            if (psyOrder.getOrderStatus() == GaugeConstant.GAUGE_ORDER_STATUE_CREATED) {
//                psyOrder.setOrderStatus(GaugeConstant.GAUGE_ORDER_STATUE_CANCELED);
//                psyOrderService.updatePsyOrder(psyOrder);
//            }
//        } else if (ConsultConstant.MODULE_CONSULT.equals(module)) {
//            PsyConsultOrderVO psyOrder = psyConsultOrderService.getOne(id);
//            if (psyOrder.getStatus() == ConsultConstant.CONSULT_ORDER_STATUE_CREATED) {
//                psyOrder.setStatus(ConsultConstant.CONSULT_ORDER_STATUE_CANCELED);
//                psyConsultOrderService.updatePsyOrder(psyOrder);
//            }
//        }
//        log.info("{}订单id={} 自动修改订单状态为已取消，操作已完成", module, ConsultConstant.MODULE_CONSULT.equals(module) ? id : orderId);
//    }
//}
