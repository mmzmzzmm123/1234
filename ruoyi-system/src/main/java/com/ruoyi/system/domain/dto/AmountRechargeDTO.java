package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/26/026 20:13
 * @Description :
 */
@Data
public class AmountRechargeDTO {
    @ApiModelProperty(value = "商家ID", hidden = true)
    private String merchantId;

    @Min(1)
    @ApiModelProperty("充值金额")
    private Long amount;

    @ApiModelProperty(value = "创建人Id", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "创建人", hidden = true)
    private String createBy;

    public Long getAmount() {
        return BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(100L)).longValue();
    }


}
