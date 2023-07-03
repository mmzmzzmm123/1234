package com.ruoyi.psychology.service;

import java.util.List;
import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.vo.PsyConsultOrderVO;

/**
 * 咨询订单Service接口
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
public interface IPsyConsultOrderService 
{
    PsyConsultOrderVO getOne(Long id);

    PsyConsultOrderVO getOneByOrderId(String orderId);

    List<PsyConsultOrder> getList(PsyConsultOrderVO req);

    List<PsyConsultOrderVO> getOrderList(PsyConsultOrderVO req);

    void updatePsyOrder(PsyConsultOrderVO req);

    int doConsult(Long id, Long workId);

    int add(PsyConsultOrderVO req);

    int update(PsyConsultOrderVO req);

    int deleteAll(Long[] ids);

    int delete(Long id);
}
