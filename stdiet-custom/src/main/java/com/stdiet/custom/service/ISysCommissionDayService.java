package com.stdiet.custom.service;

import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.custom.domain.SysCommision;
import com.stdiet.custom.domain.SysCommissionDayDetail;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.domain.SysOrderCommisionDayDetail;
import com.stdiet.custom.dto.request.SysOrderCommision;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 按天计算提成
 *
 * @author wonder
 * @date 2020-09-24
 */
public interface ISysCommissionDayService {

    /**
     * 根据订单列表按天计算营养师、售后提成
     * @param sysCommision
     * @return
     */
    List<SysCommissionDayDetail> calculateCommissionByDay(SysCommision sysCommision);

    /**
     * 根据订单计算该笔订单的服务到期时间
     * @param sysOrder 订单对象
     * @return
     */
    LocalDate getServerEndDate(SysOrder sysOrder);

    /**
     * 计算订单提成详情
     */
    AjaxResult calculateOrderCommissionDetail(SysCommision sysCommision);

    /**
     * 根据开始生效时间分类提成比例
     * @param tmpComList
     * @return
     */
    Map<String, List<SysCommision>> getRateMapByStartTime(List<SysCommision> tmpComList);

}
