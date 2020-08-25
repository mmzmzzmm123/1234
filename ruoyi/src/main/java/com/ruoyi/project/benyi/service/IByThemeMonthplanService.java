package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByThemeMonthplan;

/**
 * 主题整合月计划Service接口
 *
 * @author tsbz
 * @date 2020-08-25
 */
public interface IByThemeMonthplanService {
    /**
     * 查询主题整合月计划
     *
     * @param id 主题整合月计划ID
     * @return 主题整合月计划
     */
    public ByThemeMonthplan selectByThemeMonthplanById(String id);

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
     * 批量删除主题整合月计划
     *
     * @param ids 需要删除的主题整合月计划ID
     * @return 结果
     */
    public int deleteByThemeMonthplanByIds(String[] ids);

    /**
     * 删除主题整合月计划信息
     *
     * @param id 主题整合月计划ID
     * @return 结果
     */
    public int deleteByThemeMonthplanById(String id);
}