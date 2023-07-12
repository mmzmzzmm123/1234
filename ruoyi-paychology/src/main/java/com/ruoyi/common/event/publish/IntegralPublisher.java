package com.ruoyi.common.event.publish;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.event.IntegralEvent;
import com.ruoyi.user.domain.PsyUserIntegralRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
public class IntegralPublisher {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(PsyUserIntegralRecord record) {
        IntegralEvent event = new IntegralEvent(record);
        publish(event);
    }

    public void publish(IntegralEvent event) {
        applicationEventPublisher.publishEvent(event);
    }


}
