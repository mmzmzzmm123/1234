package com.ruoyi.common.event.listen;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.event.IntegralEvent;
import com.ruoyi.user.domain.PsyUserIntegralRecord;
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
public class IntegralListener {

    @Resource
    private IPsyUserIntegralRecordService service;

    @Async
    @EventListener(IntegralEvent.class)
//    @TransactionalEventListener(classes = IntegralEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void send(IntegralEvent event) {
        PsyUserIntegralRecord record = event.getData();
        log.info("参数{}", JSONObject.toJSONString(record));
        service.insert(record);
    }
}
