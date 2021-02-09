package com.stdiet.custom.service;

import com.stdiet.custom.domain.SysRecipes;

import java.util.List;

public interface ISysRecipesService {
    public List<SysRecipes> selectSysRecipesByRecipesId(Long id);
}
