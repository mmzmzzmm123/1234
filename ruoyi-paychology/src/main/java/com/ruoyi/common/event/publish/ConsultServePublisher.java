package com.ruoyi.common.event.publish;

import com.ruoyi.common.event.ConsultServeIntegralEvent;
import com.ruoyi.psychology.request.PsyRefConsultServeReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ConsultServePublisher {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(PsyRefConsultServeReq record) {
        ConsultServeIntegralEvent event = new ConsultServeIntegralEvent(record);
        publish(event);
    }

    public void publish(ConsultServeIntegralEvent event) {
        applicationEventPublisher.publishEvent(event);
    }


}
