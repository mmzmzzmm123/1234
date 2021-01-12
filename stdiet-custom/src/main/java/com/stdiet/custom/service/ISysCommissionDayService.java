package com.stdiet.custom.service;

import com.stdiet.custom.domain.SysCommision;
import com.stdiet.custom.domain.SysCommissionDayDetail;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.domain.SysOrderCommisionDayDetail;

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

    List<SysCommissionDayDetail> calculateCommissionByDay(SysCommision sysCommision);

}
