package com.ruoyi.api.platform.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LAM
 * @date 2023/10/5 0:17
 */
@ApiModel("api-充值配置信息")
@Data
@Accessors(chain = true)
public class ApiRechargeConfigVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标识")
    private Long id;

    @ApiModelProperty("充值金额")
    private BigDecimal amount;

    @ApiModelProperty("赠送金额")
    private BigDecimal giveAmount;
}
