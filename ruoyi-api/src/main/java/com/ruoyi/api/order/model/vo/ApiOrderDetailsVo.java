package com.ruoyi.api.order.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LAM
 * @date 2023/10/11 14:13
 */
@ApiModel("api-订单详情信息")
@Data
@Accessors(chain = true)
public class ApiOrderDetailsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("订单标识")
    private Long orderId;

    @ApiModelProperty("状态")
    private String state;

    @ApiModelProperty("服务标识")
    private Long serviceId;

    @ApiModelProperty("服务项目标识")
    private Long serviceItemId;

    @ApiModelProperty("店员等级标识")
    private Long staffLevelConfigId;

    @ApiModelProperty("礼物标识")
    private Long giftId;

    @ApiModelProperty("明细标题")
    private String detailsTitle;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("总价")
    private BigDecimal totalPrice;
}
