package com.ruoyi.common.event.listen;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.event.ConsultServeIntegralEvent;
import com.ruoyi.psychology.request.PsyRefConsultServeReq;
import com.ruoyi.psychology.service.IPsyConsultServeConfigService;
import com.ruoyi.user.service.IPsyUserIntegralRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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
