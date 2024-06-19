package com.renxin.common.event;

import com.renxin.user.domain.PsyUserIntegralRecord;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class IntegralEvent extends ApplicationEvent {

    private static final long serialVersionUID = -3931544651724002089L;

    public IntegralEvent() {
        super("Empty");
    }

    // 订单类积分
    public IntegralEvent(PsyUserIntegralRecord data) {
        super("Empty");
        this.data = data;
    }

    private PsyUserIntegralRecord data;

}
