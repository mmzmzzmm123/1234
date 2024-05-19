package com.onethinker.onethinker.factory;

import com.onethinker.onethinker.factory.service.IActivityDetailService;
import com.onethinker.onethinker.factory.service.impl.RedEnvelopeCtrlServiceImpl;
import com.onethinker.common.enums.ActivityTypeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : yangyouqi
 * @date : 2023/11/3 0003 14:16
 * 实例化工厂: 根据活动来源不同实例化不同处理
 */
@Component
@Log4j2
public class ActivityDetailFactory {
    @Autowired
    private RedEnvelopeCtrlServiceImpl redEnvelopeCtrlService;


    public IActivityDetailService queryActivityDetailByActivityType(Integer activityType) {
        if (ActivityTypeEnum.YWKJ.getCode().equals(activityType)) {
            return null;
        } else if (ActivityTypeEnum.RED_ENVELOPE.getCode().equals(activityType)) {
            return redEnvelopeCtrlService;
        } else if (ActivityTypeEnum.SIGN_IN.getCode().equals(activityType)) {
            log.warn(activityType + "逻辑暂未实现");
            throw new RuntimeException("平台暂不支持活动");
        } else if (ActivityTypeEnum.POINT_LOTTERY.getCode().equals(activityType)) {
            log.warn(activityType + "逻辑暂未实现");
            throw new RuntimeException("平台暂不支持活动");
        } else if (ActivityTypeEnum.BET_SIZE.getCode().equals(activityType)) {
            log.warn(activityType + "逻辑暂未实现");
            throw new RuntimeException("平台暂不支持活动");
        } else {
            throw new RuntimeException("活动来源【" + activityType + "】有误");
        }
    }
}
