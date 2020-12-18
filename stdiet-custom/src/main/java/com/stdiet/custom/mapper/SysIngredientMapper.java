package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysIngredient;
import com.stdiet.custom.domain.SysIngredientNotRec;
import com.stdiet.custom.domain.SysIngredientRec;

/**
 * 食材Mapper接口
 * 
 * @author wonder
 * @date 2020-12-15
 */
public interface SysIngredientMapper 
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
     * 删除食材
     * 
     * @param id 食材ID
     * @return 结果
     */
    public int deleteSysIngredientById(Long id);

    /**
     * 批量删除食材
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysIngredientByIds(Long[] ids);

    public int batchIngredientRec(List<SysIngredientRec> ingredientRecList);

    public int batchIngredientNotRec(List<SysIngredientNotRec> ingredientNotRecList);

    public int deleteIngredentRecByIngredientId(Long recId);

    public int deleteIngredentNotRecByIngredientId(Long notRecId);
}