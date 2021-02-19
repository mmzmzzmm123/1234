package com.stdiet.custom.dto.request;

import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class FoodHeatCalculatorRequest extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //客户ID加密串
    private String customerEncId;

    //食材数据对应JSON数组字符串
    private String ingredientArray;
}
