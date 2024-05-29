package com.onethinker.onethinker.factory.service;

import com.onethinker.onethinker.dto.ActivityReqDTO;

import java.util.List;

/**
 * @author : yangyouqi
 * @date : 2023/11/3 0003 14:20
 */
public interface IActivityDetailService<T> {

    /**
     * 保存活动明细数据
     *
     * @param activityReqDTO 活动明细
     * @return 更新条数
     */
    int saveEntry(ActivityReqDTO activityReqDTO);

    /**
     * 通过条件状态查询数据
     *
     * @param reqDTO 查询条件
     * @return 查询数据
     */
    List<T> queryRedEnvelopeCtrlByParams(T reqDTO);

    /**
     * 更新活动信息
     * @param doTemp 更新数据
     * @param status 更新状态
     * @return
     */
    int updateEntry(T doTemp, Integer status);
}
