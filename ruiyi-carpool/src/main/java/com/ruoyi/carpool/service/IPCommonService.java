package com.ruoyi.carpool.service;

import com.ruoyi.carpool.domain.CommonVO;
import com.ruoyi.common.core.domain.AjaxResult;

public interface IPCommonService {

    /**
     * 取消订单
     * @param orderNum
     * @return
     */
    public AjaxResult cancelOrder(CommonVO commonVO);


    /**
     * 审查拼单
     * @param commonVO
     * @return
     */
    public AjaxResult joinOrderList(CommonVO commonVO);

}
