package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.Garden;

/**
 * 菜地Service接口
 *
 * @author 郏磊涛
 * @date 2022-03-29
 */
public interface IGardenService
{
    /**
     * 查询菜地
     *
     * @param id 菜地主键
     * @return 菜地
     */
    public Garden selectGardenById(Long id);

    /**
     * 查询菜地列表
     *
     * @param garden 菜地
     * @return 菜地集合
     */
    public List<Garden> selectGardenList(Garden garden);

    /**
     * 新增菜地
     *
     * @param garden 菜地
     * @return 结果
     */
    public int insertGarden(Garden garden);

    /**
     * 批量新增菜地
     *
     * @param garden 起始菜地信息，以此地编号开始依次递增
     * @param amount 菜地数量
     * @return 结果
     */
    public int insertGardens(Garden garden, int amount);

    /**
     * 修改菜地
     *
     * @param garden 菜地
     * @return 结果
     */
    public int updateGarden(Garden garden);

    /**
     * 批量修改菜地状态
     *
     * @param garden 菜地
     * @param ids 需要更改的菜地id集合
     * @return 结果
     */
    public int updateGardenStatus(Garden garden, Long[] ids);

    /**
     * 批量删除菜地
     *
     * @param ids 需要删除的菜地主键集合
     * @return 结果
     */
    public int deleteGardenByIds(Long[] ids);

    /**
     * 删除菜地信息
     *
     * @param id 菜地主键
     * @return 结果
     */
    public int deleteGardenById(Long id);
}
