package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.Garden;

/**
 * 菜地划分Service接口
 * 
 * @author 郏磊涛
 * @date 2022-03-24
 */
public interface IGardenService 
{
    /**
     * 查询菜地划分
     * 
     * @param id 菜地划分主键
     * @return 菜地划分
     */
    public Garden selectGardenById(Long id);

    /**
     * 查询菜地划分列表
     * 
     * @param garden 菜地划分
     * @return 菜地划分集合
     */
    public List<Garden> selectGardenList(Garden garden);

    /**
     * 新增菜地划分
     * 
     * @param garden 菜地划分
     * @return 结果
     */
    public int insertGarden(Garden garden);

    /**
     * 修改菜地划分
     * 
     * @param garden 菜地划分
     * @return 结果
     */
    public int updateGarden(Garden garden);

    /**
     * 批量删除菜地划分
     * 
     * @param ids 需要删除的菜地划分主键集合
     * @return 结果
     */
    public int deleteGardenByIds(Long[] ids);

    /**
     * 删除菜地划分信息
     * 
     * @param id 菜地划分主键
     * @return 结果
     */
    public int deleteGardenById(Long id);
}
