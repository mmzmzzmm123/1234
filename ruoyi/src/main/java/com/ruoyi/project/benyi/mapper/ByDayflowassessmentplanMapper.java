package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByDayflowassessmentplan;

/**
 * 幼儿园一日流程评估计划Mapper接口
 *
 * @author tsbz
 * @date 2021-02-01
 */
public interface ByDayflowassessmentplanMapper {
    /**
     * 查询幼儿园一日流程评估计划
     *
     * @param id 幼儿园一日流程评估计划ID
     * @return 幼儿园一日流程评估计划
     */
    public ByDayflowassessmentplan selectByDayflowassessmentplanById(Long id);

    /**
     * 查询幼儿园一日流程评估计划列表
     *
     * @param byDayflowassessmentplan 幼儿园一日流程评估计划
     * @return 幼儿园一日流程评估计划集合
     */
    public List<ByDayflowassessmentplan> selectByDayflowassessmentplanList(ByDayflowassessmentplan byDayflowassessmentplan);

    /**
     * 新增幼儿园一日流程评估计划
     *
     * @param byDayflowassessmentplan 幼儿园一日流程评估计划
     * @return 结果
     */
    public int insertByDayflowassessmentplan(ByDayflowassessmentplan byDayflowassessmentplan);

    /**
     * 修改幼儿园一日流程评估计划
     *
     * @param byDayflowassessmentplan 幼儿园一日流程评估计划
     * @return 结果
     */
    public int updateByDayflowassessmentplan(ByDayflowassessmentplan byDayflowassessmentplan);

    /**
     * 删除幼儿园一日流程评估计划
     *
     * @param id 幼儿园一日流程评估计划ID
     * @return 结果
     */
    public int deleteByDayflowassessmentplanById(Long id);

    /**
     * 批量删除幼儿园一日流程评估计划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByDayflowassessmentplanByIds(Long[] ids);
}
