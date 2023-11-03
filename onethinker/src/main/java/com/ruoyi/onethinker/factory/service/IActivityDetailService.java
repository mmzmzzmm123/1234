package com.ruoyi.onethinker.factory.service;

import com.ruoyi.onethinker.dto.ActivityReqDTO;

/**
 * @author : yangyouqi
 * @date : 2023/11/3 0003 14:20
 */
public interface IActivityDetailService {

    /**
     * 保存活动明细数据
     * @param activityReqDTO
     * @return
     */
    int saveEntry(ActivityReqDTO activityReqDTO);
}
