package com.stdiet.custom.domain;

public class SysIngredientRec {
    private Long ingredientId;
    private Long recommandId;

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Long getRecommandId() {
        return recommandId;
    }

    public void setRecommandId(Long recommandId) {
        this.recommandId = recommandId;
    }

    @Override
    public String toString() {
        return "SysIngredientRec{" +
                "ingredientId=" + ingredientId +
                ", recommandId=" + recommandId +
                '}';
    }
}
