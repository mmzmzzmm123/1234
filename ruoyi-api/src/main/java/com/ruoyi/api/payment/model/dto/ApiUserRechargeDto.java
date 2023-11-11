package com.ruoyi.api.payment.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LAM
 * @date 2023/10/5 21:07
 */
@ApiModel("api-用户充值")
@Data
@Accessors(chain = true)
public class ApiUserRechargeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户自定义充值金额")
    private BigDecimal amount;

    @ApiModelProperty("系统充值配置标识")
    private Long rechargeConfigId;
}
