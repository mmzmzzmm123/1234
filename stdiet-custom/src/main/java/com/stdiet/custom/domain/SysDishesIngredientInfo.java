package com.stdiet.custom.domain;

import lombok.Data;

/**
 * 食材对象 sys_ingredient
 *
 * @author wonder
 * @date 2020-12-15
 */
@Data
public class SysDishesIngredientInfo {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String cus_unit;

    private String cus_weight;

    private Integer weight;

    private String remark;

}