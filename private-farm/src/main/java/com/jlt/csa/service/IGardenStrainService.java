package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.GardenStrain;

/**
 * 菜地品种Service接口
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public interface IGardenStrainService 
{
    /**
     * 查询菜地品种
     * 
     * @param id 菜地品种主键
     * @return 菜地品种
     */
    public GardenStrain selectGardenStrainById(Long id);

    /**
     * 查询菜地品种列表
     * 
     * @param gardenStrain 菜地品种
     * @return 菜地品种集合
     */
    public List<GardenStrain> selectGardenStrainList(GardenStrain gardenStrain);

    /**
     * 新增菜地品种
     * 
     * @param gardenStrain 菜地品种
     * @return 结果
     */
    public int insertGardenStrain(GardenStrain gardenStrain);

    /**
     * 修改菜地品种
     * 
     * @param gardenStrain 菜地品种
     * @return 结果
     */
    public int updateGardenStrain(GardenStrain gardenStrain);

    /**
     * 批量删除菜地品种
     * 
     * @param ids 需要删除的菜地品种主键集合
     * @return 结果
     */
    public int deleteGardenStrainByIds(Long[] ids);

    /**
     * 删除菜地品种信息
     * 
     * @param id 菜地品种主键
     * @return 结果
     */
    public int deleteGardenStrainById(Long id);
}
