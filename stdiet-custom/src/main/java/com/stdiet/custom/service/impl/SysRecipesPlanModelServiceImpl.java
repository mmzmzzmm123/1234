package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.SecurityUtils;
import com.stdiet.custom.domain.SysRecipesPlan;
import com.stdiet.custom.domain.SysRecipesPlanModel;
import com.stdiet.custom.mapper.SysRecipesPlanMapper;
import com.stdiet.custom.mapper.SysRecipesPlanModelMapper;
import com.stdiet.custom.service.ISysRecipesPlanModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 食谱计划Service业务层处理
 *
 * @author wonder
 * @date 2021-02-27
 */
@Service("sysRecipesPlanModelService")
@Transactional
public class SysRecipesPlanModelServiceImpl implements ISysRecipesPlanModelService {

    @Autowired
    SysRecipesPlanModelMapper sysRecipesPlanModelMapper;

    @Autowired
    SysRecipesPlanMapper sysRecipesPlanMapper;

    @Override
    public List<SysRecipesPlanModel> selectRecipesModelListByCondition(SysRecipesPlanModel sysRecipesPlanModel) {
        return sysRecipesPlanModelMapper.selectRecipesModelListByCondition(sysRecipesPlanModel);
    }

    @Override
    public int insertRecipsesModel(SysRecipesPlanModel sysRecipesPlanModel) {
        SysRecipesPlan sysRecipesPlan = new SysRecipesPlan();
        sysRecipesPlan.setStartNumDay(1);
        sysRecipesPlan.setEndNumDay(7);
        sysRecipesPlan.setType(1);
        int rows = sysRecipesPlanMapper.insertSysRecipesPlan(sysRecipesPlan);
        if (rows > 0) {
            sysRecipesPlanModel.setCreateBy(SecurityUtils.getUsername());
            sysRecipesPlanModel.setCreateTime(DateUtils.getNowDate());
            sysRecipesPlanModel.setPlanId(sysRecipesPlan.getId());
            return sysRecipesPlanModelMapper.insertRecipsesModel(sysRecipesPlanModel);
        }
        return 0;
    }

    @Override
    public int updateRecipesModel(SysRecipesPlanModel sysRecipesPlanModel) {
        sysRecipesPlanModel.setUpdateBy(SecurityUtils.getUsername());
        sysRecipesPlanModel.setUpdateTime(DateUtils.getNowDate());
        return sysRecipesPlanModelMapper.updateRecipesModel(sysRecipesPlanModel);
    }

    @Override
    public int removeRecipesModel(Long id) {
        return sysRecipesPlanModelMapper.removeRecipesModel(id);
    }


}