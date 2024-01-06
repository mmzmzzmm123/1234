package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.app.OrderRequest;
import com.ruoyi.common.core.domain.app.SelectOrderDTO;
import com.ruoyi.common.core.domain.entity.order.Order;

import java.util.Date;
import java.util.List;

/**
 * @Entity com.ruoyi.system.domain.MerchantAmountDetail
 */
public interface OrderMapper extends BaseMapper<Order> {

	public List<Order> list(OrderRequest orderRequest);

	public Integer listCount(OrderRequest orderRequest);

	public default void updateStatus(String orderId, int orderStatus) {
		Order entity = new Order();
		entity.setOrderId(orderId);
		entity.setUpdateTime(new Date());
		entity.setOrderStatus(orderStatus);
		updateById(entity);
	}

	public default void updateCancel(String orderId, String cause) {
		Order entity = new Order();
		entity.setOrderId(orderId);
		entity.setUpdateTime(new Date());
		entity.setOrderStatus(3);
		entity.setRemark(cause);
		updateById(entity);
	}

	public default void updateFinish(String orderId) {
		Order entity = new Order();
		entity.setOrderId(orderId);
		entity.setFinishTime(new Date());
		entity.setOrderStatus(2);
		entity.setUpdateTime(new Date());
		updateById(entity);
	}

	Integer listCountByUpdateTime(SelectOrderDTO orderRequest);

	List<Order> listByUpdateTime(SelectOrderDTO orderRequest);
}
