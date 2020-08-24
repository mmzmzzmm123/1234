package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByThemeTermplan;

/**
 * 主题整合学期计划Mapper接口
 *
 * @author tsbz
 * @date 2020-08-24
 */
public interface ByThemeTermplanMapper {
    /**
     * 查询主题整合学期计划
     *
     * @param id 主题整合学期计划IDdeleteByThemeTermplanByIds
     * @return 主题整合学期计划
     */
    public ByThemeTermplan selectByThemeTermplanById(String id);

    /**
     * 查询主题整合学期计划列表
     *
     * @param byThemeTermplan 主题整合学期计划
     * @return 主题整合学期计划集合
     */
    public List<ByThemeTermplan> selectByThemeTermplanList(ByThemeTermplan byThemeTermplan);

    /**
     * 新增主题整合学期计划
     *
     * @param byThemeTermplan 主题整合学期计划
     * @return 结果
     */
    public int insertByThemeTermplan(ByThemeTermplan byThemeTermplan);

    /**
     * 修改主题整合学期计划
     *
     * @param byThemeTermplan 主题整合学期计划
     * @return 结果
     */
    public int updateByThemeTermplan(ByThemeTermplan byThemeTermplan);

    /**
     * 删除主题整合学期计划
     *
     * @param id 主题整合学期计划ID
     * @return 结果
     */
    public int deleteByThemeTermplanById(String id);

    /**
     * 批量删除主题整合学期计划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByThemeTermplanByIds(String[] ids);
}
