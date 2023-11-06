package com.ruoyi.api.order.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LAM
 * @date 2023/10/11 1:02
 */
@ApiModel("api-指定订单表单")
@Data
@Accessors(chain = true)
public class ApiAppointOrderFormDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分销用户标识")
    private Long shareUserId;

    @ApiModelProperty("店员标识")
    @NotNull(message = "店员标识不能为空")
    private Long staffId;

    @ApiModelProperty("订单金额")
    @NotNull(message = "订单金额不能为空")
    private BigDecimal amount;

    @ApiModelProperty("支付方式")
    @NotBlank(message = "支付方式不能为空")
    private String payWay;

    @ApiModelProperty("数量")
    @NotNull(message = "数量不能为空")
    private Long num;

    @ApiModelProperty("服务标识")
    @NotNull(message = "服务标识不能为空")
    private Long serviceId;

    @ApiModelProperty("服务子项标识")
    @NotNull(message = "服务子项标识不能为空")
    private Long serviceItemId;

    @ApiModelProperty("社交账号服务商")
    private String accountServiceProvider;

    @ApiModelProperty("客户账号")
    private String customNum;

    @ApiModelProperty("备注")
    private String remark;
}
