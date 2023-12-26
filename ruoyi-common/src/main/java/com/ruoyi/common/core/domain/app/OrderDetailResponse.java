package com.ruoyi.common.core.domain.app;

import java.util.List;

import com.ruoyi.common.core.domain.entity.order.Order;
import com.ruoyi.common.core.domain.entity.order.OrderRefund;
import com.ruoyi.common.core.domain.entity.order.OrderSku;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OrderDetailResponse {

	@ApiModelProperty("订单")
	private Order order;

	@ApiModelProperty("订单关联sku")
	private List<OrderSku> orderSkuList;

	@ApiModelProperty("订单退款")
	private OrderRefund orderRefund;

}
