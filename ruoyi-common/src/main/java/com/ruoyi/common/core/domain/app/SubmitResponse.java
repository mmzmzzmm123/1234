package com.ruoyi.common.core.domain.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubmitResponse {

	@ApiModelProperty("下单时的参数")
	private com.ruoyi.common.core.domain.app.OrderProduceRequest.Params param;

	@ApiModelProperty("任务名称")
	private String taskName;

	@ApiModelProperty("订单ID")
	private String orderId;
}
