package com.renxin.common.event.listen;

import com.alibaba.fastjson2.JSONObject;
import com.renxin.common.event.ConsultServeIntegralEvent;
import com.renxin.psychology.request.PsyRefConsultServeReq;
import com.renxin.psychology.service.IPsyConsultServeConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ConsultServeListener {

    @Resource
    private IPsyConsultServeConfigService service;

    @Async
    @EventListener(ConsultServeIntegralEvent.class)
//    @TransactionalEventListener(classes = ConsultServeIntegralEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void send(ConsultServeIntegralEvent event) {
        PsyRefConsultServeReq record = event.getData();
        log.info("参数{}", JSONObject.toJSONString(record));
        boolean b = service.setRefNum(record);
        if (!b) {
            log.error("关联咨询师数据设置失败,参数{}", JSONObject.toJSONString(record));
        }
    }
}
