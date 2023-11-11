package com.ruoyi.api.staff.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LAM
 * @date 2023/10/18 15:22
 */
@Data
@Accessors(chain = true)
@ApiModel("api-员工钱包对象")
public class ApiStaffWalletVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("待结算")
    private BigDecimal waitCommission;

    @ApiModelProperty("已结算")
    private BigDecimal commissionSettled;

    @ApiModelProperty("订单总值")
    private BigDecimal totalOrderPrice;
}
