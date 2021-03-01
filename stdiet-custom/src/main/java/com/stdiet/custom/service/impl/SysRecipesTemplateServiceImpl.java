package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.SecurityUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysRecipesPlan;
import com.stdiet.custom.domain.SysRecipesTemplate;
import com.stdiet.custom.mapper.SysRecipesPlanMapper;
import com.stdiet.custom.mapper.SysRecipesTemplateMapper;
import com.stdiet.custom.service.ISysRecipesTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 食谱计划Service业务层处理
 *
 * @author wonder
 * @date 2021-02-27
 */
@Service("sysRecipesPlanModelService")
@Transactional
public class SysRecipesTemplateServiceImpl implements ISysRecipesTemplateService {

    @Autowired
    SysRecipesTemplateMapper sysRecipesTemplateMapper;

    @Autowired
    SysRecipesPlanMapper sysRecipesPlanMapper;

    @Override
    public List<SysRecipesTemplate> selectRecipesTemplateListByCondition(SysRecipesTemplate sysRecipesTemplate) {
        return sysRecipesTemplateMapper.selectRecipesTemplateListByCondition(sysRecipesTemplate);
    }

    @Override
    public int insertRecipsesTemplate(SysRecipesTemplate sysRecipesTemplate) {
        SysRecipesPlan sysRecipesPlan = new SysRecipesPlan();
        sysRecipesPlan.setStartNumDay(1);
        sysRecipesPlan.setEndNumDay(7);
        sysRecipesPlan.setType(1);
        sysRecipesPlanMapper.insertSysRecipesPlan(sysRecipesPlan);
        if (!StringUtils.isNull(sysRecipesPlan.getId())) {
            sysRecipesTemplate.setCreateBy(SecurityUtils.getUsername());
            sysRecipesTemplate.setCreateTime(DateUtils.getNowDate());
            sysRecipesTemplate.setPlanId(sysRecipesPlan.getId());
            return sysRecipesTemplateMapper.insertRecipsesTemplate(sysRecipesTemplate);
        }
        return 0;
    }

    @Override
    public int updateRecipesTemplate(SysRecipesTemplate sysRecipesTemplate) {
        sysRecipesTemplate.setUpdateBy(SecurityUtils.getUsername());
        sysRecipesTemplate.setUpdateTime(DateUtils.getNowDate());
        return sysRecipesTemplateMapper.updateRecipesTemplate(sysRecipesTemplate);
    }

    @Override
    public int removeRecipesTemplate(Long id) {
        return sysRecipesTemplateMapper.removeRecipesTemplate(id);
    }

    @Override
    public SysRecipesTemplate getRecipesTemplateById(Long id) {
        return sysRecipesTemplateMapper.getRecipesTemplateById(id);
    }
}
