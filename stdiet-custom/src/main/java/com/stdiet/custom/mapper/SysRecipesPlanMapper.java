package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysRecipesPlan;

/**
 * 食谱计划Mapper接口
 *
 * @author xzj
 * @date 2021-01-15
 */
public interface SysRecipesPlanMapper
{
    /**
     * 查询食谱计划
     *
     * @param id 食谱计划ID
     * @return 食谱计划
     */
    public SysRecipesPlan selectSysRecipesPlanById(Long id);

    /**
     * 查询食谱计划列表
     *
     * @param sysRecipesPlan 食谱计划
     * @return 食谱计划集合
     */
    public List<SysRecipesPlan> selectSysRecipesPlanList(SysRecipesPlan sysRecipesPlan);

    /**
     * 新增食谱计划
     *
     * @param sysRecipesPlan 食谱计划
     * @return 结果
     */
    public int insertSysRecipesPlan(SysRecipesPlan sysRecipesPlan);

    /**
     * 修改食谱计划
     *
     * @param sysRecipesPlan 食谱计划
     * @return 结果
     */
    public int updateSysRecipesPlan(SysRecipesPlan sysRecipesPlan);

    /**
     * 删除食谱计划
     *
     * @param id 食谱计划ID
     * @return 结果
     */
    public int deleteSysRecipesPlanById(Long id);

    /**
     * 批量删除食谱计划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysRecipesPlanByIds(Long[] ids);

    /**
     * 批量添加食谱计划
     *
     * @return 结果
     */
    int insertBatch(List<SysRecipesPlan> sysRecipesPlanList);

    /**
     * 根据订单ID删除对应食谱计划
     * @param orderId
     * @return
     */
    int delRecipesPlanByOrderId(Long orderId);
}