package com.stdiet.custom.service;

import java.util.List;
import com.stdiet.custom.domain.SysIngredient;

/**
 * 食材Service接口
 * 
 * @author wonder
 * @date 2020-12-15
 */
public interface ISysIngredientService 
{
    /**
     * 查询食材
     * 
     * @param id 食材ID
     * @return 食材
     */
    public SysIngredient selectSysIngredientById(Long id);

    /**
     * 查询食材列表
     * 
     * @param sysIngredient 食材
     * @return 食材集合
     */
    public List<SysIngredient> selectSysIngredientList(SysIngredient sysIngredient);

    /**
     * 新增食材
     * 
     * @param sysIngredient 食材
     * @return 结果
     */
    public int insertSysIngredient(SysIngredient sysIngredient);

    /**
     * 修改食材
     * 
     * @param sysIngredient 食材
     * @return 结果
     */
    public int updateSysIngredient(SysIngredient sysIngredient);

    /**
     * 批量删除食材
     * 
     * @param ids 需要删除的食材ID
     * @return 结果
     */
    public int deleteSysIngredientByIds(Long[] ids);

    /**
     * 删除食材信息
     * 
     * @param id 食材ID
     * @return 结果
     */
    public int deleteSysIngredientById(Long id);

    /**
     * 根据食材名称查询食材信息
     * @param name
     * @return
     */
    public SysIngredient selectSysIngredientByName(String name);}