package com.renxin.psychology.service;

import com.renxin.psychology.domain.PsyConsultOrderServe;
import com.renxin.psychology.vo.PsyConsultServeConfigVO;

/**
 * 咨询订单Service接口
 * 
 * @author renxin
 * @date 2023-06-26
 */
public interface IPsyConsultOrderServeService
{

    void add(PsyConsultServeConfigVO serve, Long orderId);

    PsyConsultOrderServe getOneByOrder(Long orderId, Long serveId);

}
