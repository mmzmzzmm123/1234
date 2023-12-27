package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/25/025 17:26
 * @Description : 资金划拨DTO
 */
@Data
public class AmountTransferDTO {

    /**
     * 划拨商家
     */
    private String merchantId;

    /**
     * 接收资金商家ID
     */
    @NotEmpty
    @ApiModelProperty("接收资金商家ID")
    private String targetMerchantId;

    /**
     * 划拨金额
     */
    @Min(1)
    @ApiModelProperty("划拨金额")
    private Long amount;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String describe;


}
