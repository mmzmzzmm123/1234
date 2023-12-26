package com.ruoyi.common.core.domain.app;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OrderListResponse {

	@ApiModelProperty("下单时间")
	private Date createTime;

	@ApiModelProperty("订单号")
	private String orderId;

	@ApiModelProperty("订单类型 0-拉人入群")
	private String orderType;

	@ApiModelProperty("商品名称")
	private String productName;

	@ApiModelProperty("规格名称(目前商品只有一个规格)")
	private List<String> skuNameList;

	@ApiModelProperty("订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款")
	private Integer orderStatus;

	@ApiModelProperty("预付金额")
	private long price;

	@ApiModelProperty("实际消费")
	private long actualPrice;

	@ApiModelProperty("退款金额")
	private long refundPrice;

	@ApiModelProperty("关联任务")
	private String taskId;
}
