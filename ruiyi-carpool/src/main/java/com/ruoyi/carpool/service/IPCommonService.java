package com.ruoyi.carpool.service;

import com.ruoyi.carpool.domain.CommonVO;
import com.ruoyi.carpool.domain.PMember;
import com.ruoyi.carpool.domain.PPassenger;
import com.ruoyi.common.core.domain.AjaxResult;

public interface IPCommonService {

    /**
     * 取消订单
     * @param commonVO
     * @return
     */
    public AjaxResult cancelOrder(CommonVO commonVO);


    /**
     * 完成订单
     * @param commonVO
     * @return
     */
    public AjaxResult finshOrder(CommonVO commonVO);

    /**
     * 审查拼单
     * @param commonVO
     * @return
     */
    public AjaxResult joinOrderList(CommonVO commonVO);


    /**
     * 初始化微信用户信息
     * @param pMember
     * @return
     */
    public AjaxResult initMember(PPassenger pPassenger);

}
