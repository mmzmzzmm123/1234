package com.ruoyi.api.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LAM
 * @date 2023/9/19 22:23
 */
@Data
@ApiModel("api-用户钱包")
@Accessors(chain = true)
public class ApiUserWalletVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("余额")
    private BigDecimal balance;

    @ApiModelProperty("赠送余额")
    private BigDecimal giftBalance;

    @ApiModelProperty("消费总额")
    private BigDecimal totalBalance;

    @ApiModelProperty("赠送总额")
    private BigDecimal totalGiftBalance;
}
