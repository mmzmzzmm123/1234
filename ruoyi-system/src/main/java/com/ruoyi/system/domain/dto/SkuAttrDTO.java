package com.ruoyi.system.domain.dto;

import lombok.Data;

@Data
public class SkuAttrDTO {
    private Long id;

    private Long price;

    private String priceUnit;

    private Integer stock;

    private Integer countyId;

    private String countyName;
}
