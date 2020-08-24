package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByThemeTermplan;

/**
 * 主题整合学期计划Service接口
 *
 * @author tsbz
 * @date 2020-08-24
 */
public interface IByThemeTermplanService {
    /**
     * 查询主题整合学期计划
     *
     * @param id 主题整合学期计划ID
     * @return 主题整合学期计划
     */
    public ByThemeTermplan selectByThemeTermplanById(Long id);

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
     * 批量删除主题整合学期计划
     *
     * @param ids 需要删除的主题整合学期计划ID
     * @return 结果
     */
    public int deleteByThemeTermplanByIds(Long[] ids);

    /**
     * 删除主题整合学期计划信息
     *
     * @param id 主题整合学期计划ID
     * @return 结果
     */
    public int deleteByThemeTermplanById(Long id);
}
