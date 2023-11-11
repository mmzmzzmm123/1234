package com.ruoyi.api.order.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/10 1:53
 */
@ApiModel("api-礼物订单表单")
@Data
@Accessors(chain = true)
public class ApiGiftOrderFormDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分销用户标识")
    private Long shareUserId;

    @ApiModelProperty("店员标识")
    @NotNull(message = "店员标识不能为空")
    private Long staffId;

    @ApiModelProperty("礼物标识")
    @NotNull(message = "礼物标识不能为空")
    private Long giftId;

    @ApiModelProperty("支付方式")
    @NotBlank(message = "支付方式不能为空")
    private String payWay;

    @ApiModelProperty("数量")
    @NotNull(message = "数量不能为空")
    private Long num;

    @ApiModelProperty("社交账号服务商")
    private String accountServiceProvider;

    @ApiModelProperty("客户账号")
    private String customNum;

    @ApiModelProperty("备注")
    private String remark;
}
