package com.renxin.common.event.publish;

import com.renxin.common.event.IntegralEvent;
import com.renxin.user.domain.PsyUserIntegralRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
