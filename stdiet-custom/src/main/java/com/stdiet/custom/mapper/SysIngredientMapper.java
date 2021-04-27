package com.stdiet.custom.mapper;

import com.stdiet.custom.domain.SysIngredentFile;
import com.stdiet.custom.domain.SysIngredient;
import com.stdiet.custom.domain.SysPhysicalSignsObj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 食材Mapper接口
 *
 * @author wonder
 * @date 2020-12-15
 */
public interface SysIngredientMapper {
    /**
     * 查询食材
     *
     * @param id 食材ID
     * @return 食材
     */
    SysIngredient selectSysIngredientById(Long id);

    /**
     * 查询食材列表
     *
     * @param sysIngredient 食材
     * @return 食材集合
     */
    List<SysIngredient> selectSysIngredientList(SysIngredient sysIngredient);

    /**
     * 新增食材
     *
     * @param sysIngredient 食材
     * @return 结果
     */
    int insertSysIngredient(SysIngredient sysIngredient);

    /**
     * 修改食材
     *
     * @param sysIngredient 食材
     * @return 结果
     */
    int updateSysIngredient(SysIngredient sysIngredient);

    /**
     * 删除食材
     *
     * @param id 食材ID
     * @return 结果
     */
    int deleteSysIngredientById(Long id);

    /**
     * 批量删除食材
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
//    int deleteSysIngredientByIds(Long[] ids);

    int batchIngredientRec(List<SysPhysicalSignsObj> ingredientRecList);

    int batchIngredientNotRec(List<SysPhysicalSignsObj> ingredientNotRecList);

    int deleteIngredientRecByIngredientId(Long recId);

    int deleteIngredientNotRecByIngredientId(Long notRecId);

//    int deleteIngredientRecByIngredientIds(Long[] id);

//    int deleteIngredientNotRecByIngredientIds(Long[] id);

    /**
     * 根据食材名称查询食材信息
     *
     * @param name
     * @return
     */
    SysIngredient selectSysIngredientByName(@Param("name") String name);

    int batchInsertIngredientImage(List<SysIngredentFile> list);

    int deleteIngredientImageById(Long id);

}