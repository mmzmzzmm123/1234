package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByDayflowassessment;

/**
 * 幼儿园一日流程评估Mapper接口
 *
 * @author tsbz
 * @date 2021-02-05
 */
public interface ByDayflowassessmentMapper {
    /**
     * 查询幼儿园一日流程评估
     *
     * @param id 幼儿园一日流程评估ID
     * @return 幼儿园一日流程评估
     */
    public ByDayflowassessment selectByDayflowassessmentById(Long id);

    /**
     * 查询幼儿园一日流程评估列表
     *
     * @param byDayflowassessment 幼儿园一日流程评估
     * @return 幼儿园一日流程评估集合
     */
    public List<ByDayflowassessment> selectByDayflowassessmentList(ByDayflowassessment byDayflowassessment);

    /**
     * 查询幼儿园一日流程评估列表
     *
     * @param byDayflowassessment 幼儿园一日流程评估
     * @return 幼儿园一日流程评估集合
     */
    public List<ByDayflowassessment> selectByDayflowassessmentPjf(ByDayflowassessment byDayflowassessment);

    /**
     * 查询幼儿园一日流程评估列表
     *
     * @param byDayflowassessment 幼儿园一日流程评估
     * @return 幼儿园一日流程评估集合
     */
    public List<ByDayflowassessment> selectByDayflowassessmentTeacherPjf(ByDayflowassessment byDayflowassessment);


    /**
     * 新增幼儿园一日流程评估
     *
     * @param byDayflowassessment 幼儿园一日流程评估
     * @return 结果
     */
    public int insertByDayflowassessment(ByDayflowassessment byDayflowassessment);

    /**
     * 修改幼儿园一日流程评估
     *
     * @param byDayflowassessment 幼儿园一日流程评估
     * @return 结果
     */
    public int updateByDayflowassessment(ByDayflowassessment byDayflowassessment);

    /**
     * 删除幼儿园一日流程评估
     *
     * @param id 幼儿园一日流程评估ID
     * @return 结果
     */
    public int deleteByDayflowassessmentById(Long id);

    /**
     * 批量删除幼儿园一日流程评估
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByDayflowassessmentByIds(Long[] ids);
}
