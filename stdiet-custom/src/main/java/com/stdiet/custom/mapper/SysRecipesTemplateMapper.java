package com.stdiet.custom.mapper;

import com.stdiet.custom.domain.SysRecipesTemplate;

import java.util.List;

/**
 * 食谱计划Mapper接口
 *
 * @author wonder
 * @date 2021-02-27
 */
public interface SysRecipesTemplateMapper {
    List<SysRecipesTemplate> selectRecipesTemplateListByCondition(SysRecipesTemplate sysRecipesTemplate);

    int insertRecipsesTemplate(SysRecipesTemplate sysRecipesTemplate);

    int updateRecipesTemplate(SysRecipesTemplate sysRecipesTemplate);

    int removeRecipesTemplate(Long id);

    SysRecipesTemplate getRecipesTemplateById(Long id);
}