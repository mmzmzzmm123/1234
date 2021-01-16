package com.stdiet.custom.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 菜品对象 sys_dishes
 *
 * @author wonder
 * @date 2020-12-28
 */
public class SysDishesIngredient extends SysIngredient {

    private Long ingredientId;

    private Long dishesId;

    private Long cusUnit;

    private BigDecimal cusWeight;

    private BigDecimal weight;

    private String remark;

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Long getDishesId() {
        return dishesId;
    }

    public void setDishesId(Long dishesId) {
        this.dishesId = dishesId;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getCusWeight() {
        return cusWeight;
    }

    public void setCusWeight(BigDecimal cusWeight) {
        this.cusWeight = cusWeight;
    }

    public Long getCusUnit() {
        return cusUnit;
    }

    public void setCusUnit(Long cusUnit) {
        this.cusUnit = cusUnit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("ingredientId", getIngredientId())
                .append("dishesId", getDishesId())
                .append("weight", getWeight())
                .append("cusWeight", getCusWeight())
                .append("cusUnit", getCusUnit())
                .toString();
    }
}