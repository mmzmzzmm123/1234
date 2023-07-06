package com.ruoyi.psychology.task;

import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.service.IPsyConsultOrderService;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component("consultOrderTask")
public class OrderTask {

    @Resource
    private ISysConfigService configService;

    @Resource
    private IPsyConsultOrderService psyConsultOrderService;

    public void cancel()
    {
        String val = configService.selectConfigByKey("order.cancel.time");
        int num = StringUtils.isNotEmpty(val) ? Integer.parseInt(val) : 15;
        List<PsyConsultOrder> cancelList = psyConsultOrderService.getCancelList(num);
        if (CollectionUtils.isNotEmpty(cancelList)) {
            cancelList.forEach(order -> {
                psyConsultOrderService.cancel(order);
            });
            List<Long> collect = cancelList.stream().map(PsyConsultOrder::getId).collect(Collectors.toList());
            log.info("咨询订单取消, 订单id={} 自动修改订单状态为已取消，操作已完成", collect);
        }
    }

}
