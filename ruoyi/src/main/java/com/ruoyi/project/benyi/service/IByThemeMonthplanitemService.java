package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByThemeMonthplanitem;

/**
 * 主题整合周计划Service接口
 *
 * @author tsbz
 * @date 2020-08-25
 */
public interface IByThemeMonthplanitemService {
    /**
     * 查询主题整合周计划
     *
     * @param id 主题整合周计划ID
     * @return 主题整合周计划
     */
    public ByThemeMonthplanitem selectByThemeMonthplanitemById(String id);

    /**
     * 查询主题整合周计划列表
     *
     * @param byThemeMonthplanitem 主题整合周计划
     * @return 主题整合周计划集合
     */
    public List<ByThemeMonthplanitem> selectByThemeMonthplanitemList(ByThemeMonthplanitem byThemeMonthplanitem);

    /**
     * 新增主题整合周计划
     *
     * @param byThemeMonthplanitem 主题整合周计划
     * @return 结果
     */
    public int insertByThemeMonthplanitem(ByThemeMonthplanitem byThemeMonthplanitem);

    /**
     * 修改主题整合周计划
     *
     * @param byThemeMonthplanitem 主题整合周计划
     * @return 结果
     */
    public int updateByThemeMonthplanitem(ByThemeMonthplanitem byThemeMonthplanitem);

    /**
     * 批量删除主题整合周计划
     *
     * @param ids 需要删除的主题整合周计划ID
     * @return 结果
     */
    public int deleteByThemeMonthplanitemByIds(String[] ids);

    /**
     * 删除主题整合周计划信息
     *
     * @param id 主题整合周计划ID
     * @return 结果
     */
    public int deleteByThemeMonthplanitemById(String id);
}
