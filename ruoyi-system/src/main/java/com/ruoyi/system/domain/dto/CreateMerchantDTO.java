package com.ruoyi.system.domain.dto;

import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/25/025 15:54
 * @Description :
 */
@Data
public class CreateMerchantDTO {

    private String merchantId;

    private String merchantName;

    private Integer merchantType;

    private String plMerchantId;
}