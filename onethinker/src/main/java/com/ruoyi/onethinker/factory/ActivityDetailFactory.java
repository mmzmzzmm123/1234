package com.ruoyi.onethinker.factory;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.enums.ActivityTypeEnum;
import com.ruoyi.onethinker.factory.service.IActivityDetailService;
import com.ruoyi.onethinker.factory.service.impl.RedEnvelopeCtrlServiceImpl;
import lombok.extern.log4j.Log4j2;

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
        if (ActivityTypeEnum.RED_ENVELOPE.getCode().equals(activityType)) {
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
