package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByThemeMonthplan;

/**
 * 主题整合月计划Mapper接口
 *
 * @author tsbz
 * @date 2020-08-17
 */
public interface ByThemeMonthplanMapper {
    /**
     * 查询主题整合月计划
     *
     * @param id 主题整合月计划ID
     * @return 主题整合月计划
     */
    public ByThemeMonthplan selectByThemeMonthplanById(Long id);

    /**
     * 查询主题整合月计划列表
     *
     * @param byThemeMonthplan 主题整合月计划
     * @return 主题整合月计划集合
     */
    public List<ByThemeMonthplan> selectByThemeMonthplanList(ByThemeMonthplan byThemeMonthplan);

    /**
     * 新增主题整合月计划
     *
     * @param byThemeMonthplan 主题整合月计划
     * @return 结果
     */
    public int insertByThemeMonthplan(ByThemeMonthplan byThemeMonthplan);

    /**
     * 修改主题整合月计划
     *
     * @param byThemeMonthplan 主题整合月计划
     * @return 结果
     */
    public int updateByThemeMonthplan(ByThemeMonthplan byThemeMonthplan);

    /**
     * 删除主题整合月计划
     *
     * @param id 主题整合月计划ID
     * @return 结果
     */
    public int deleteByThemeMonthplanById(Long id);

    /**
     * 批量删除主题整合月计划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByThemeMonthplanByIds(Long[] ids);
}
