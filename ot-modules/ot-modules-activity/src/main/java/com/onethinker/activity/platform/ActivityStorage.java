package com.onethinker.activity.platform;

import com.onethinker.activity.domain.RedEnvelopeDtl;
import com.onethinker.activity.dto.ActivityReqDTO;
import com.onethinker.activity.dto.ActivityResDTO;
import com.onethinker.common.enums.ActivityTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @author yangyouqi
 * @date 2024/5/31
 * 活动信息
 */
public interface ActivityStorage {

    /**
     * 返回活动类型
     * @return 返回值
     */
    ActivityTypeEnum getActivityType();

    /**
     * 保存活动信息
     * @param activity 活动实体类
     */
    void saveActivitCtr(ActivityReqDTO activity);

    /**
     * 更新活动信息
     * @param activity 活动实体类
     */
    void updateEntry(ActivityReqDTO activity);

    /**
     * 更新活动明细数据
     * @param reqDTO
     * @param status
     */
    void updateEntry(Object reqDTO, Integer status);


    /**
     * 查询活动信息
     * @param activityResDTO 活动
     * @return 返回值
     */
    void getActivityInfo(ActivityResDTO activityResDTO);

    /**
     * 通过查询条件获取红包明细数据
     * @param redEnvelopeCtrlDTO
     * @return
     */
    List<Map> queryEntryByParams(Object redEnvelopeCtrlDTO);
}
