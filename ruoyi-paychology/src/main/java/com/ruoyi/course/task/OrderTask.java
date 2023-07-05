package com.ruoyi.course.task;

import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.service.ICourOrderService;
import com.ruoyi.gauge.constant.GaugeConstant;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.service.IPsyOrderService;
import com.ruoyi.system.service.ISysConfigService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("courseOrderTask")
public class OrderTask {

    @Resource
    private ISysConfigService configService;

    @Resource
    private ICourOrderService courOrderService;

    public void cancel()
    {
        System.out.println("课程订单取消");
        String val = configService.selectConfigByKey("order.cancel.time");
        int num = StringUtils.isNotEmpty(val) ? Integer.parseInt(val) : 15;
        List<CourOrder> cancelList = courOrderService.getCancelList(num);
        if (CollectionUtils.isNotEmpty(cancelList)) {
            cancelList.forEach(order -> {
                order.setStatus(GaugeConstant.GAUGE_ORDER_STATUE_CANCELED);
                courOrderService.updateCourOrder(order);
            });
        }
    }

}
