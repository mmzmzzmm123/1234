package com.stdiet.custom.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 菜品对象 sys_dishes
 *
 * @author wonder
 * @date 2020-12-28
 */
@Data
public class SysDishesIngredient extends SysIngredient {

    private Long ingredientId;

    private Long dishesId;

    private Long cusUnit;

//    private BigDecimal cusWeight;

    private Integer cusWeight;

    private BigDecimal weight;

    private String remark;

}