package com.ruoyi.common.core.domain.app;

import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CancelOrderRequest {
	@ApiModelProperty("订单ID集合")
	private List<String> orderIds;
	@ApiModelProperty("取消原因")
	private String cause;

}
