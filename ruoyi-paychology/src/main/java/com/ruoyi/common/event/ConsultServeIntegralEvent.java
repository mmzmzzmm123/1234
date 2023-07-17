package com.ruoyi.common.event;

import com.ruoyi.psychology.request.PsyRefConsultServeReq;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ConsultServeIntegralEvent extends ApplicationEvent {

    private static final long serialVersionUID = -3931544651724002089L;

    public ConsultServeIntegralEvent() {
        super("Empty");
    }

    // 订单类积分
    public ConsultServeIntegralEvent(PsyRefConsultServeReq data) {
        super("Empty");
        this.data = data;
    }

    private PsyRefConsultServeReq data;

}
