package com.ruoyi.common.core.domain.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class OrderListResponseVO {
	@ApiModelProperty("任务名称")
	private String taskName;

	@ApiModelProperty("下单时间")
	private Date createTime;

	@ApiModelProperty("订单号")
	private String orderId;

	@ApiModelProperty("订单类型 0-拉人入群")
	private Integer orderType;

	@ApiModelProperty("商品名称")
	private String productName;

	@ApiModelProperty("规格名称(目前商品只有一个规格)")
	private List<String> skuNameList;

	@ApiModelProperty("订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款")
	private Integer orderStatus;

	@ApiModelProperty("预付金额")
	private Double price;

	@ApiModelProperty("实际消费")
	private Double actualPrice;

	@ApiModelProperty("退款金额")
	private Double refundPrice;

	@ApiModelProperty("关联任务")
	private String taskId;

	@ApiModelProperty("商家ID")
	private String merchantId;

	@ApiModelProperty("商家名称")
	private String merchantName;

	@ApiModelProperty("备注")
	private String remark;

	private Date updateTime;

	public void setPrice(Double price) {
		if (price == null) {
			this.price = 0d;
			return;
		}
		this.price = BigDecimal.valueOf(price).divide(BigDecimal.valueOf(10000L), 4, RoundingMode.HALF_UP).doubleValue();
	}

	public void setActualPrice(Double actualPrice) {
		if (actualPrice == null) {
			this.actualPrice = 0d;
			return;
		}
		this.actualPrice = BigDecimal.valueOf(actualPrice).divide(BigDecimal.valueOf(10000L), 4, RoundingMode.HALF_UP).doubleValue();
	}

	public void setRefundPrice(Double refundPrice) {
		if (refundPrice == null) {
			this.refundPrice = 0d;
			return;
		}
		this.refundPrice = BigDecimal.valueOf(refundPrice).divide(BigDecimal.valueOf(10000L), 4, RoundingMode.HALF_UP).doubleValue();
	}
}
