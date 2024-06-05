package com.ruoyi.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.domain.PsyConsultOrderItem;
import com.ruoyi.psychology.dto.OrderItemDTO;
import com.ruoyi.psychology.vo.PsyConsultOrderVO;

import java.util.List;

/**
 * 咨询订单Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
public interface PsyConsultOrderItemMapper extends BaseMapper<PsyConsultOrderItem>
{
    public List<OrderItemDTO> getOrderItemList(Long consultantId,String day) ;
}
