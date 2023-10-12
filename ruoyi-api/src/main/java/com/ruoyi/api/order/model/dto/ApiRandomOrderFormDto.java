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
 * @date 2023/10/12 3:56
 */
@ApiModel("api-随机订单表单")
@Data
@Accessors(chain = true)
public class ApiRandomOrderFormDto implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @NotBlank(message = "社交账号服务商不能为空")
    private String accountServiceProvider;

    @ApiModelProperty("客户账号")
    @NotBlank(message = "客户账号不能为空")
    private String customNum;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("店员等级")
    @NotNull(message = "店员等级不能为空")
    private Long staffLevel;

    @ApiModelProperty("选择店员性别")
    @NotBlank(message = "选择店员性别不能为空")
    private String chooseStaffSex;

    @ApiModelProperty("是否过滤服务过的店员")
    @NotBlank(message = "是否过滤服务过的店员不能为空")
    private String filterServedStaff;
}
