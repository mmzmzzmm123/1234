package com.ruoyi.psychology.service;

import java.util.List;
import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.dto.OrderDTO;
import com.ruoyi.psychology.dto.OrderListDTO;
import com.ruoyi.psychology.request.PsyAdminOrderReq;
import com.ruoyi.psychology.request.PsyHxOrderReq;
import com.ruoyi.psychology.vo.PsyConsultOrderVO;

/**
 * 咨询订单Service接口
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
public interface IPsyConsultOrderService 
{

    OrderDTO getOrderDetail(Long id);

    PsyConsultOrderVO getOne(Long id);

    List<PsyConsultOrder> getList(PsyAdminOrderReq req);

    List<PsyConsultOrder> getCancelList(int num);

    List<OrderListDTO> getOrderList(PsyConsultOrderVO req);

    String hx(PsyHxOrderReq req);

    void updatePayOrder(PsyConsultOrderVO req);

    void wechatPayNotify(PsyConsultOrderVO req);

    int doConsult(Long id, Long workId, Integer time);

    int add(PsyConsultOrderVO req);

    void cancel(PsyConsultOrder order);

    int update(PsyConsultOrderVO req);

    int deleteAll(Long[] ids);

    int delete(Long id);
}
