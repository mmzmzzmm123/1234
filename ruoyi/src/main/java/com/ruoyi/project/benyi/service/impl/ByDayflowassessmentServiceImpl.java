package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.aspectj.lang.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByDayflowassessmentMapper;
import com.ruoyi.project.benyi.domain.ByDayflowassessment;
import com.ruoyi.project.benyi.service.IByDayflowassessmentService;

/**
 * 幼儿园一日流程评估Service业务层处理
 *
 * @author tsbz
 * @date 2021-02-05
 */
@Service
public class ByDayflowassessmentServiceImpl implements IByDayflowassessmentService {
    @Autowired
    private ByDayflowassessmentMapper byDayflowassessmentMapper;

    /**
     * 查询幼儿园一日流程评估
     *
     * @param id 幼儿园一日流程评估ID
     * @return 幼儿园一日流程评估
     */
    @Override
    public ByDayflowassessment selectByDayflowassessmentById(Long id) {
        return byDayflowassessmentMapper.selectByDayflowassessmentById(id);
    }

    /**
     * 查询幼儿园一日流程评估列表
     *
     * @param byDayflowassessment 幼儿园一日流程评估
     * @return 幼儿园一日流程评估
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<ByDayflowassessment> selectByDayflowassessmentList(ByDayflowassessment byDayflowassessment) {
        return byDayflowassessmentMapper.selectByDayflowassessmentList(byDayflowassessment);
    }

    /**
     * 新增幼儿园一日流程评估
     *
     * @param byDayflowassessment 幼儿园一日流程评估
     * @return 结果
     */
    @Override
    public int insertByDayflowassessment(ByDayflowassessment byDayflowassessment) {
        byDayflowassessment.setCreateTime(DateUtils.getNowDate());
        return byDayflowassessmentMapper.insertByDayflowassessment(byDayflowassessment);
    }

    /**
     * 修改幼儿园一日流程评估
     *
     * @param byDayflowassessment 幼儿园一日流程评估
     * @return 结果
     */
    @Override
    public int updateByDayflowassessment(ByDayflowassessment byDayflowassessment) {
        return byDayflowassessmentMapper.updateByDayflowassessment(byDayflowassessment);
    }

    /**
     * 批量删除幼儿园一日流程评估
     *
     * @param ids 需要删除的幼儿园一日流程评估ID
     * @return 结果
     */
    @Override
    public int deleteByDayflowassessmentByIds(Long[] ids) {
        return byDayflowassessmentMapper.deleteByDayflowassessmentByIds(ids);
    }

    /**
     * 删除幼儿园一日流程评估信息
     *
     * @param id 幼儿园一日流程评估ID
     * @return 结果
     */
    @Override
    public int deleteByDayflowassessmentById(Long id) {
        return byDayflowassessmentMapper.deleteByDayflowassessmentById(id);
    }
}
