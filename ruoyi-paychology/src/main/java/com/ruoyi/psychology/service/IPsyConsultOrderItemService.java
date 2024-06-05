package com.ruoyi.psychology.service;

import com.ruoyi.psychology.domain.PsyConsultOrderItem;
import com.ruoyi.psychology.dto.OrderItemDTO;

import java.util.List;

/**
 * 咨询订单Service接口
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
public interface IPsyConsultOrderItemService
{

    void del(Long id);

    void add(PsyConsultOrderItem item);

    void update(PsyConsultOrderItem item);

    PsyConsultOrderItem getOne(Long id);

    boolean updateBatch(List<PsyConsultOrderItem> entities);

    List<PsyConsultOrderItem> getList(Long orderId);

    PsyConsultOrderItem getOneByOrderId(Long orderId);

    List<OrderItemDTO> getOrderItemList(Long consultantId,String day);    

}
