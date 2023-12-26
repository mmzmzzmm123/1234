package com.ruoyi.system.domain.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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
     * 接收资金商家
     */
    @NotBlank
    private String toMerchantId;

    /**
     * 划拨金额
     */
    @NotBlank
    @Min(1)
    private Long amount;

    /**
     * 描述
     */
    private String describe;



}
