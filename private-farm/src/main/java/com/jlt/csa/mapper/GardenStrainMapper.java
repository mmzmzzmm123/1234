package com.jlt.csa.mapper;

import java.util.List;
import com.jlt.csa.domain.GardenStrain;

/**
 * 菜地品种Mapper接口
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public interface GardenStrainMapper 
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
     * 删除菜地品种
     * 
     * @param id 菜地品种主键
     * @return 结果
     */
    public int deleteGardenStrainById(Long id);

    /**
     * 批量删除菜地品种
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGardenStrainByIds(Long[] ids);
}
