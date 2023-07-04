package com.ruoyi.psychology.task;

import com.ruoyi.psychology.constant.ConsultConstant;
import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.service.IPsyConsultOrderService;
import com.ruoyi.psychology.vo.PsyConsultOrderVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("consultOrderTask")
public class OrderTask {

    @Resource
    private IPsyConsultOrderService psyConsultOrderService;

    public void cancel()
    {
        System.out.println("取消订单");
        List<PsyConsultOrder> cancelList = psyConsultOrderService.getCancelList();
        if (CollectionUtils.isNotEmpty(cancelList)) {
            cancelList.forEach(order -> {
                psyConsultOrderService.cancel(order);
            });
        }
    }

}
