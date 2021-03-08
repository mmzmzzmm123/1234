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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Long> insertRecipsesTemplate(SysRecipesTemplate sysRecipesTemplate) {
        SysRecipesPlan sysRecipesPlan = new SysRecipesPlan();
        sysRecipesPlan.setStartNumDay(1);
        sysRecipesPlan.setEndNumDay(7);
        sysRecipesPlan.setType(1);
        sysRecipesPlan.setCusId(0L);
        sysRecipesPlanMapper.insertSysRecipesPlan(sysRecipesPlan);
        if (!StringUtils.isNull(sysRecipesPlan.getId())) {
            sysRecipesTemplate.setCreateBy(SecurityUtils.getUsername());
            sysRecipesTemplate.setCreateTime(DateUtils.getNowDate());
            sysRecipesTemplate.setPlanId(sysRecipesPlan.getId());
            int rows = sysRecipesTemplateMapper.insertRecipsesTemplate(sysRecipesTemplate);
            if(rows > 0) {
                Map<String, Long> result =  new HashMap<>();
                result.put("id", sysRecipesTemplate.getId());
                result.put("planId", sysRecipesPlan.getId());
                return result;
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public int updateRecipesTemplate(SysRecipesTemplate sysRecipesTemplate) {
        sysRecipesTemplate.setUpdateBy(SecurityUtils.getUsername());
        sysRecipesTemplate.setUpdateTime(DateUtils.getNowDate());
        return sysRecipesTemplateMapper.updateRecipesTemplate(sysRecipesTemplate);
    }

    @Override
    public int removeRecipesTemplate(Long id) {
        SysRecipesTemplate sysRecipesTemplate= sysRecipesTemplateMapper.getRecipesTemplateById(id);
        if(StringUtils.isNull(sysRecipesTemplate)) {
            return 0;
        }
        int rows = sysRecipesTemplateMapper.removeRecipesTemplate(sysRecipesTemplate.getId());
        if(rows > 0) {
            return sysRecipesPlanMapper.deleteSysRecipesPlanById(sysRecipesTemplate.getPlanId());
        }
        return 0;
    }

    @Override
    public SysRecipesTemplate getRecipesTemplateById(Long id) {
        return sysRecipesTemplateMapper.getRecipesTemplateById(id);
    }
}
