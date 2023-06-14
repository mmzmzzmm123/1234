package com.ruoyi.course.domain.dto;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseQueryDTO extends BaseEntity {
    private String nameValue;
    private Integer typeValue;
    private Integer payTypeValue;
    private String authorValue;
    private BigDecimal lowPrice;
    private BigDecimal highPrice;

    private Integer onSaleValue;
}
