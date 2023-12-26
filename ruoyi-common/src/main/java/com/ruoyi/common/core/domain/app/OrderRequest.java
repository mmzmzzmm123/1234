package com.ruoyi.common.core.domain.app;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OrderRequest {
	@ApiModelProperty("订单ID")
	private String orderId;
	@ApiModelProperty("下单开始时间")
	private Date startTime;
	@ApiModelProperty("下单结束时间")
	private Date endTime;

	@ApiModelProperty("订单类型 0-拉人入群")
	private int orderType;

	@ApiModelProperty("订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款")
	private int orderStatus;

	@ApiModelProperty("page")
	private int page;

	@ApiModelProperty("limit")
	private int limit;

}
