package com.stdiet.custom.service;

import com.stdiet.custom.domain.SysRecipesTemplate;

import java.util.List;
import java.util.Map;

/**
 * 食谱计划Service接口
 *
 * @author wonder
 * @date 2021-02-27
 */
public interface ISysRecipesTemplateService {

    List<SysRecipesTemplate> selectRecipesTemplateListByCondition(SysRecipesTemplate sysRecipesTemplate);

    Map<String, Long> insertRecipsesTemplate(SysRecipesTemplate sysRecipesTemplate);

    int updateRecipesTemplate(SysRecipesTemplate sysRecipesTemplate);

    int removeRecipesTemplate(Long id);

    SysRecipesTemplate getRecipesTemplateById(Long id);
}