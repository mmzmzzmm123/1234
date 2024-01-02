package com.ruoyi.common.core.domain.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OrderRequest {
	@ApiModelProperty("订单ID")
	private String orderId;
	@ApiModelProperty("下单开始时间")
	private String startTime;
	@ApiModelProperty("下单结束时间")
	private String endTime;

	@ApiModelProperty("订单类型 0-拉人入群")
	private Integer orderType;

	@ApiModelProperty("订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款")
	private Integer orderStatus;

	@ApiModelProperty("page")
	private int page = 1;

	@ApiModelProperty("limit")
	private int limit = 10;

	private Integer merchantType;
	private String merchantId;

	@ApiModelProperty("任务名称")
	private String taskName;

}
