package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/26/026 16:34
 * @Description :
 */
@Data
public class AmountRecoveryDTO {

    /**
     * 商家ID
     */
    @ApiModelProperty("商家ID")
    private String merchantId;

    /**
     * 回收商家ID
     */
    @NotBlank
    @ApiModelProperty("回收商家商家")
    private String targetMerchantId;


    @ApiModelProperty("回收资金金额")
    private Long amount;



}
