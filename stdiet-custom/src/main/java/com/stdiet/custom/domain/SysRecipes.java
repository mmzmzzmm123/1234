package com.stdiet.custom.domain;

import lombok.Data;

import java.util.List;

@Data
public class SysRecipes {

    private Long id;

    private Long cusId;

    private Long planId;

    private int reviewStatus;

    private List<SysRecipesDaily> menus;
}
