package com.stdiet.custom.service.impl;

import com.stdiet.custom.domain.SysRecipes;
import com.stdiet.custom.mapper.SysRecipesMapper;
import com.stdiet.custom.service.ISysRecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysRecipesServiceImpl implements ISysRecipesService {

    @Autowired
    private SysRecipesMapper sysRecipesMapper;

    @Override
    public List<SysRecipes> selectSysRecipesByRecipesId(Long id) {
        return sysRecipesMapper.selectSysRecipesByRecipesId(id);
    }
}
