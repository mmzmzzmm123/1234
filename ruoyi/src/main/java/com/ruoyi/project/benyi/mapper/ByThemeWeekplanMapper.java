package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByThemeWeekplan;

/**
 * 主题整合周计划Mapper接口
 *
 * @author tsbz
 * @date 2020-08-25
 */
public interface ByThemeWeekplanMapper {
    /**
     * 查询主题整合周计划
     *
     * @param id 主题整合周计划ID
     * @return 主题整合周计划
     */
    public ByThemeWeekplan selectByThemeWeekplanById(String id);

    /**
     * 查询主题整合周计划列表
     *
     * @param byThemeWeekplan 主题整合周计划
     * @return 主题整合周计划集合
     */
    public List<ByThemeWeekplan> selectByThemeWeekplanList(ByThemeWeekplan byThemeWeekplan);

    /**
     * 新增主题整合周计划
     *
     * @param byThemeWeekplan 主题整合周计划
     * @return 结果
     */
    public int insertByThemeWeekplan(ByThemeWeekplan byThemeWeekplan);

    /**
     * 修改主题整合周计划
     *
     * @param byThemeWeekplan 主题整合周计划
     * @return 结果
     */
    public int updateByThemeWeekplan(ByThemeWeekplan byThemeWeekplan);

    /**
     * 删除主题整合周计划
     *
     * @param id 主题整合周计划ID
     * @return 结果
     */
    public int deleteByThemeWeekplanById(String id);

    /**
     * 批量删除主题整合周计划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByThemeWeekplanByIds(String[] ids);
}
