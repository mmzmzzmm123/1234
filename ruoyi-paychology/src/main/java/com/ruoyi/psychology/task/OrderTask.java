package com.ruoyi.psychology.task;

import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.service.IPsyConsultOrderService;
import com.ruoyi.system.service.ISysConfigService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("consultOrderTask")
public class OrderTask {

    @Resource
    private ISysConfigService configService;

    @Resource
    private IPsyConsultOrderService psyConsultOrderService;

    public void cancel()
    {
        System.out.println("咨询订单取消");
        String val = configService.selectConfigByKey("order.cancel.time");
        int num = StringUtils.isNotEmpty(val) ? Integer.parseInt(val) : 15;
        List<PsyConsultOrder> cancelList = psyConsultOrderService.getCancelList(num);
        if (CollectionUtils.isNotEmpty(cancelList)) {
            cancelList.forEach(order -> {
                psyConsultOrderService.cancel(order);
            });
        }
    }

}
