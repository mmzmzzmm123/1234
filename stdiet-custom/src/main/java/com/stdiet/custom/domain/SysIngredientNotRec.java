package com.stdiet.custom.domain;

public class SysIngredientNotRec {
    private Long ingredientId;
    private Long notRecommandId;

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Long getRecommandId() {
        return notRecommandId;
    }

    public void setRecommandId(Long recommandId) {
        this.notRecommandId = recommandId;
    }

    @Override
    public String toString() {
        return "SysIngredientRec{" +
                "ingredientId=" + ingredientId +
                ", notRecommandId=" + notRecommandId +
                '}';
    }
}
