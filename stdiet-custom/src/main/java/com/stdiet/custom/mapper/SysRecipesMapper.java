package com.stdiet.custom.mapper;

import com.stdiet.custom.domain.SysRecipes;

import java.util.List;

public interface SysRecipesMapper {

    public List<SysRecipes> selectSysRecipesByRecipesId(Long id);
}
