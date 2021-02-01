package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByDayflowassessmentplanMapper;
import com.ruoyi.project.benyi.domain.ByDayflowassessmentplan;
import com.ruoyi.project.benyi.service.IByDayflowassessmentplanService;

/**
 * 幼儿园一日流程评估计划Service业务层处理
 *
 * @author tsbz
 * @date 2021-02-01
 */
@Service
public class ByDayflowassessmentplanServiceImpl implements IByDayflowassessmentplanService {
    @Autowired
    private ByDayflowassessmentplanMapper byDayflowassessmentplanMapper;

    /**
     * 查询幼儿园一日流程评估计划
     *
     * @param id 幼儿园一日流程评估计划ID
     * @return 幼儿园一日流程评估计划
     */
    @Override
    public ByDayflowassessmentplan selectByDayflowassessmentplanById(Long id) {
        return byDayflowassessmentplanMapper.selectByDayflowassessmentplanById(id);
    }

    /**
     * 查询幼儿园一日流程评估计划列表
     *
     * @param byDayflowassessmentplan 幼儿园一日流程评估计划
     * @return 幼儿园一日流程评估计划
     */
    @Override
    public List<ByDayflowassessmentplan> selectByDayflowassessmentplanList(ByDayflowassessmentplan byDayflowassessmentplan) {
        return byDayflowassessmentplanMapper.selectByDayflowassessmentplanList(byDayflowassessmentplan);
    }

    /**
     * 新增幼儿园一日流程评估计划
     *
     * @param byDayflowassessmentplan 幼儿园一日流程评估计划
     * @return 结果
     */
    @Override
    public int insertByDayflowassessmentplan(ByDayflowassessmentplan byDayflowassessmentplan) {
        byDayflowassessmentplan.setCreateTime(DateUtils.getNowDate());
        return byDayflowassessmentplanMapper.insertByDayflowassessmentplan(byDayflowassessmentplan);
    }

    /**
     * 修改幼儿园一日流程评估计划
     *
     * @param byDayflowassessmentplan 幼儿园一日流程评估计划
     * @return 结果
     */
    @Override
    public int updateByDayflowassessmentplan(ByDayflowassessmentplan byDayflowassessmentplan) {
        return byDayflowassessmentplanMapper.updateByDayflowassessmentplan(byDayflowassessmentplan);
    }

    /**
     * 批量删除幼儿园一日流程评估计划
     *
     * @param ids 需要删除的幼儿园一日流程评估计划ID
     * @return 结果
     */
    @Override
    public int deleteByDayflowassessmentplanByIds(Long[] ids) {
        return byDayflowassessmentplanMapper.deleteByDayflowassessmentplanByIds(ids);
    }

    /**
     * 删除幼儿园一日流程评估计划信息
     *
     * @param id 幼儿园一日流程评估计划ID
     * @return 结果
     */
    @Override
    public int deleteByDayflowassessmentplanById(Long id) {
        return byDayflowassessmentplanMapper.deleteByDayflowassessmentplanById(id);
    }
}
