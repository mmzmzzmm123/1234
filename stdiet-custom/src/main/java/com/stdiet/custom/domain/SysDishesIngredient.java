package com.stdiet.custom.domain;

import lombok.Data;

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

    private Integer cusWeight;

    private BigDecimal weight;

    private String remark;

    private Long id;

    private String cus_unit;

    private String cus_weight;

    private String recIdsStr;

    private String notRecIdsStr;

}