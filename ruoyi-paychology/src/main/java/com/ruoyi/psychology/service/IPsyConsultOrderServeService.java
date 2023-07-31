package com.ruoyi.psychology.service;

import com.ruoyi.psychology.domain.PsyConsultOrderServe;
import com.ruoyi.psychology.vo.PsyConsultServeConfigVO;

import java.util.List;

/**
 * 咨询订单Service接口
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
public interface IPsyConsultOrderServeService
{

    void add(PsyConsultServeConfigVO serve, Long orderId);

    PsyConsultOrderServe getOneByOrder(Long orderId, Long serveId);

}
