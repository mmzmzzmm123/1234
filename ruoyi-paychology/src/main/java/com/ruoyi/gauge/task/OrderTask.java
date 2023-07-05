package com.ruoyi.gauge.task;

import com.ruoyi.gauge.constant.GaugeConstant;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.service.IPsyOrderService;
import com.ruoyi.system.service.ISysConfigService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("gaugeOrderTask")
public class OrderTask {

    @Resource
    private ISysConfigService configService;

    @Resource
    private IPsyOrderService psyOrderService;

    public void cancel()
    {
        System.out.println("测评订单取消");
        String val = configService.selectConfigByKey("order.cancel.time");
        int num = StringUtils.isNotEmpty(val) ? Integer.parseInt(val) : 15;
        List<PsyOrder> cancelList = psyOrderService.getOrderByCancel(num);
        if (CollectionUtils.isNotEmpty(cancelList)) {
            cancelList.forEach(order -> {
                order.setOrderStatus(GaugeConstant.GAUGE_ORDER_STATUE_CANCELED);
                psyOrderService.updatePsyOrder(order);
            });
        }
    }

}
