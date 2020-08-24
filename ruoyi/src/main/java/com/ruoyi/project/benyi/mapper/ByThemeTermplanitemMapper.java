package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByThemeTermplanitem;

/**
 * 主题整合学期计划明细Mapper接口
 *
 * @author tsbz
 * @date 2020-08-24
 */
public interface ByThemeTermplanitemMapper {
    /**
     * 查询主题整合学期计划明细
     *
     * @param id 主题整合学期计划明细ID
     * @return 主题整合学期计划明细
     */
    public ByThemeTermplanitem selectByThemeTermplanitemById(Long id);

    /**
     * 查询主题整合学期计划明细列表
     *
     * @param byThemeTermplanitem 主题整合学期计划明细
     * @return 主题整合学期计划明细集合
     */
    public List<ByThemeTermplanitem> selectByThemeTermplanitemList(ByThemeTermplanitem byThemeTermplanitem);

    /**
     * 新增主题整合学期计划明细
     *
     * @param byThemeTermplanitem 主题整合学期计划明细
     * @return 结果
     */
    public int insertByThemeTermplanitem(ByThemeTermplanitem byThemeTermplanitem);

    /**
     * 修改主题整合学期计划明细
     *
     * @param byThemeTermplanitem 主题整合学期计划明细
     * @return 结果
     */
    public int updateByThemeTermplanitem(ByThemeTermplanitem byThemeTermplanitem);

    /**
     * 删除主题整合学期计划明细
     *
     * @param id 主题整合学期计划明细ID
     * @return 结果
     */
    public int deleteByThemeTermplanitemById(Long id);

    /**
     * 批量删除主题整合学期计划明细
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByThemeTermplanitemByIds(Long[] ids);
}
