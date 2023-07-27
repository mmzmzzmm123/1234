package com.ruoyi.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.dto.OrderDTO;
import com.ruoyi.psychology.dto.OrderListDTO;
import com.ruoyi.psychology.request.PsyAdminOrderReq;
import com.ruoyi.psychology.vo.PsyConsultOrderVO;

import java.util.List;

/**
 * 咨询订单Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
public interface PsyConsultOrderMapper extends BaseMapper<PsyConsultOrder>
{
    List<PsyConsultOrder> getList(PsyAdminOrderReq req);

    List<OrderListDTO> getOrderList(PsyConsultOrderVO req);

    OrderDTO getOrderDetail(Long id);

    int tombstonedByIds(Long[] ids);
}
