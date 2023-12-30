package com.ruoyi.system.domain.dto;

import lombok.Data;

@Data
public class SkuAttrDTO {
    private Long id;

    private Long productId;

    private Long price;

    private String priceUnit;

    private Integer stock;

    private String countyCode;

    private String countyName;
}
