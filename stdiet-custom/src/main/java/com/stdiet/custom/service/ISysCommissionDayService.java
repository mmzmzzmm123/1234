package com.stdiet.custom.service;

import com.stdiet.custom.domain.SysCommision;
import com.stdiet.custom.domain.SysCommissionDayDetail;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.domain.SysOrderCommisionDayDetail;

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
     * 根据订单ID计算该笔订单的服务到期时间
     * @param orderId
     * @return
     */
    LocalDate getServerEndDate(Long orderId);

}
