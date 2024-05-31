package com.onethinker.user.event;

import com.onethinker.common.enums.IntegralTypeEnum;
import com.onethinker.user.domain.PlatformUserIntegral;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author yangyouqi
 */
@Getter
public class ActionUserIntegralSuccessEvent extends ApplicationEvent {

    private final IntegralTypeEnum integralType;
    private final Long integral;

    public ActionUserIntegralSuccessEvent(PlatformUserIntegral platformUserIntegral, IntegralTypeEnum integralType,Long integral) {
        super(platformUserIntegral);
        this.integralType = integralType;
        this.integral = integral;
    }

}
