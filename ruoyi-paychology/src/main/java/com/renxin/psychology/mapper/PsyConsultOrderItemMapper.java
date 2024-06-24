package com.renxin.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renxin.psychology.domain.PsyConsultOrderItem;
import com.renxin.psychology.dto.OrderItemDTO;

import java.util.List;

/**
 * 咨询订单Mapper接口
 * 
 * @author renxin
 * @date 2023-06-26
 */
public interface PsyConsultOrderItemMapper extends BaseMapper<PsyConsultOrderItem>
{
    public List<OrderItemDTO> getOrderItemList(Long consultantId,String day) ;
}