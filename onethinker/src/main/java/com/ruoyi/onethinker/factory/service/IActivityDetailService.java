package com.ruoyi.onethinker.factory.service;

import java.util.List;

import com.ruoyi.onethinker.domain.RedEnvelopeCtrl;
import com.ruoyi.onethinker.dto.ActivityReqDTO;

/**
 * @author : yangyouqi
 * @date : 2023/11/3 0003 14:20
 */
public interface IActivityDetailService<T> {

    /**
     * 保存活动明细数据
     * @param activityReqDTO
     * @return
     */
    int saveEntry(ActivityReqDTO activityReqDTO);

    /**
     * 通过条件状态查询数据
     * @param reqDTO
     * @return
     */
    List<T> queryRedEnvelopeCtrlByParams(T reqDTO);

    int updateEntry(T doTemp, Integer createQrCodeStatusInit);
}
