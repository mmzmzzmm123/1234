package com.stdiet.custom.mapper;

import com.stdiet.custom.domain.SysRecipesPlan;
import com.stdiet.custom.domain.SysRecipesPlanListInfo;
import com.stdiet.custom.domain.SysRecipesPlanModel;

import java.util.List;

/**
 * 食谱计划Mapper接口
 *
 * @author wonder
 * @date 2021-02-27
 */
public interface SysRecipesPlanModelMapper
{
    List<SysRecipesPlanModel> selectRecipesModelListByCondition(SysRecipesPlanModel sysRecipesPlanModel);

    int insertRecipsesModel(SysRecipesPlanModel sysRecipesPlanModel);

    int updateRecipesModel(SysRecipesPlanModel sysRecipesPlanModel);

    int removeRecipesModel(Long id);
}