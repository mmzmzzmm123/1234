package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByDayflowassessmentitem;

/**
 * 幼儿园一日流程评估得分项Mapper接口
 *
 * @author tsbz
 * @date 2021-04-05
 */
public interface ByDayflowassessmentitemMapper {
    /**
     * 查询幼儿园一日流程评估得分项
     *
     * @param id 幼儿园一日流程评估得分项ID
     * @return 幼儿园一日流程评估得分项
     */
    public ByDayflowassessmentitem selectByDayflowassessmentitemById(Long id);

    /**
     * 查询幼儿园一日流程评估得分项列表
     *
     * @param byDayflowassessmentitem 幼儿园一日流程评估得分项
     * @return 幼儿园一日流程评估得分项集合
     */
    public List<ByDayflowassessmentitem> selectByDayflowassessmentitemList(ByDayflowassessmentitem byDayflowassessmentitem);

    /**
     * 新增幼儿园一日流程评估得分项
     *
     * @param byDayflowassessmentitem 幼儿园一日流程评估得分项
     * @return 结果
     */
    public int insertByDayflowassessmentitem(ByDayflowassessmentitem byDayflowassessmentitem);

    /**
     * 修改幼儿园一日流程评估得分项
     *
     * @param byDayflowassessmentitem 幼儿园一日流程评估得分项
     * @return 结果
     */
    public int updateByDayflowassessmentitem(ByDayflowassessmentitem byDayflowassessmentitem);

    /**
     * 删除幼儿园一日流程评估得分项
     *
     * @param id 幼儿园一日流程评估得分项ID
     * @return 结果
     */
    public int deleteByDayflowassessmentitemById(Long id);

    /**
     * 批量删除幼儿园一日流程评估得分项
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByDayflowassessmentitemByIds(Long[] ids);
}
