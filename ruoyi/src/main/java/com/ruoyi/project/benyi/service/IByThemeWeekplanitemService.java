package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByThemeWeekplanitem;

/**
 * 主题整合周计划明细Service接口
 *
 * @author tsbz
 * @date 2020-08-26
 */
public interface IByThemeWeekplanitemService {
    /**
     * 查询主题整合周计划明细
     *
     * @param id 主题整合周计划明细ID
     * @return 主题整合周计划明细
     */
    public ByThemeWeekplanitem selectByThemeWeekplanitemById(Long id);

    /**
     * 查询主题整合周计划明细列表
     *
     * @param byThemeWeekplanitem 主题整合周计划明细
     * @return 主题整合周计划明细集合
     */
    public List<ByThemeWeekplanitem> selectByThemeWeekplanitemList(ByThemeWeekplanitem byThemeWeekplanitem);

    /**
     * 新增主题整合周计划明细
     *
     * @param byThemeWeekplanitem 主题整合周计划明细
     * @return 结果
     */
    public int insertByThemeWeekplanitem(ByThemeWeekplanitem byThemeWeekplanitem);

    /**
     * 修改主题整合周计划明细
     *
     * @param byThemeWeekplanitem 主题整合周计划明细
     * @return 结果
     */
    public int updateByThemeWeekplanitem(ByThemeWeekplanitem byThemeWeekplanitem);

    /**
     * 批量删除主题整合周计划明细
     *
     * @param ids 需要删除的主题整合周计划明细ID
     * @return 结果
     */
    public int deleteByThemeWeekplanitemByIds(Long[] ids);

    /**
     * 删除主题整合周计划明细信息
     *
     * @param id 主题整合周计划明细ID
     * @return 结果
     */
    public int deleteByThemeWeekplanitemById(Long id);


    /**
     * 删除主题整合周计划明细信息
     *
     * @param pid 主题周计划主键
     * @return 结果
     */
    public int deleteByThemeWeekplanitemByPId(String pid);
}
