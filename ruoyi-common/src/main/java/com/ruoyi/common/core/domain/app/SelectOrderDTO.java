package com.ruoyi.common.core.domain.app;

import lombok.Data;

@Data
public class SelectOrderDTO {
    public String updateTime;
    private Integer merchantType;
    private String merchantId;

    private Integer page = 1;
    private Integer limit = 100;
}
