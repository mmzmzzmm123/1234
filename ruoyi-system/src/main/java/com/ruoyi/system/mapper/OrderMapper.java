package com.ruoyi.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.app.OrderRequest;
import com.ruoyi.common.core.domain.entity.order.Order;

/**
 * @Entity com.ruoyi.system.domain.MerchantAmountDetail
 */
public interface OrderMapper extends BaseMapper<Order> {

	public List<Order> list(OrderRequest orderRequest);

	public Integer listCount(OrderRequest orderRequest);
}
