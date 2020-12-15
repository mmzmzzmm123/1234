package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysIngredientMapper;
import com.stdiet.custom.domain.SysIngredient;
import com.stdiet.custom.service.ISysIngredientService;

/**
 * 食材Service业务层处理
 * 
 * @author wonder
 * @date 2020-12-15
 */
@Service
public class SysIngredientServiceImpl implements ISysIngredientService 
{
    @Autowired
    private SysIngredientMapper sysIngredientMapper;

    /**
     * 查询食材
     * 
     * @param id 食材ID
     * @return 食材
     */
    @Override
    public SysIngredient selectSysIngredientById(Long id)
    {
        return sysIngredientMapper.selectSysIngredientById(id);
    }

    /**
     * 查询食材列表
     * 
     * @param sysIngredient 食材
     * @return 食材
     */
    @Override
    public List<SysIngredient> selectSysIngredientList(SysIngredient sysIngredient)
    {
        return sysIngredientMapper.selectSysIngredientList(sysIngredient);
    }

    /**
     * 新增食材
     * 
     * @param sysIngredient 食材
     * @return 结果
     */
    @Override
    public int insertSysIngredient(SysIngredient sysIngredient)
    {
        sysIngredient.setCreateTime(DateUtils.getNowDate());
        return sysIngredientMapper.insertSysIngredient(sysIngredient);
    }

    /**
     * 修改食材
     * 
     * @param sysIngredient 食材
     * @return 结果
     */
    @Override
    public int updateSysIngredient(SysIngredient sysIngredient)
    {
        sysIngredient.setUpdateTime(DateUtils.getNowDate());
        return sysIngredientMapper.updateSysIngredient(sysIngredient);
    }

    /**
     * 批量删除食材
     * 
     * @param ids 需要删除的食材ID
     * @return 结果
     */
    @Override
    public int deleteSysIngredientByIds(Long[] ids)
    {
        return sysIngredientMapper.deleteSysIngredientByIds(ids);
    }

    /**
     * 删除食材信息
     * 
     * @param id 食材ID
     * @return 结果
     */
    @Override
    public int deleteSysIngredientById(Long id)
    {
        return sysIngredientMapper.deleteSysIngredientById(id);
    }
}