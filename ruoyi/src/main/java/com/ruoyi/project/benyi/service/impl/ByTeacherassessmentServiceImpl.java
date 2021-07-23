package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.aspectj.lang.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByTeacherassessmentMapper;
import com.ruoyi.project.benyi.domain.ByTeacherassessment;
import com.ruoyi.project.benyi.service.IByTeacherassessmentService;

/**
 * 教师月绩效考核Service业务层处理
 *
 * @author tsbz
 * @date 2021-07-01
 */
@Service
public class ByTeacherassessmentServiceImpl implements IByTeacherassessmentService {
    @Autowired
    private ByTeacherassessmentMapper byTeacherassessmentMapper;

    /**
     * 查询教师月绩效考核
     *
     * @param id 教师月绩效考核ID
     * @return 教师月绩效考核
     */
    @Override
    public ByTeacherassessment selectByTeacherassessmentById(Long id) {
        return byTeacherassessmentMapper.selectByTeacherassessmentById(id);
    }

    /**
     * 查询教师月绩效考核列表
     *
     * @param byTeacherassessment 教师月绩效考核
     * @return 教师月绩效考核
     */
    @Override
    @DataScope(deptAlias = "a")
    public List<ByTeacherassessment> selectByTeacherassessmentList(ByTeacherassessment byTeacherassessment) {
        return byTeacherassessmentMapper.selectByTeacherassessmentList(byTeacherassessment);
    }

    /**
     * 新增教师月绩效考核
     *
     * @param byTeacherassessment 教师月绩效考核
     * @return 结果
     */
    @Override
    public int insertByTeacherassessment(ByTeacherassessment byTeacherassessment) {
        byTeacherassessment.setCreateTime(DateUtils.getNowDate());
        return byTeacherassessmentMapper.insertByTeacherassessment(byTeacherassessment);
    }

    /**
     * 修改教师月绩效考核
     *
     * @param byTeacherassessment 教师月绩效考核
     * @return 结果
     */
    @Override
    public int updateByTeacherassessment(ByTeacherassessment byTeacherassessment) {
        return byTeacherassessmentMapper.updateByTeacherassessment(byTeacherassessment);
    }

    /**
     * 批量删除教师月绩效考核
     *
     * @param ids 需要删除的教师月绩效考核ID
     * @return 结果
     */
    @Override
    public int deleteByTeacherassessmentByIds(Long[] ids) {
        return byTeacherassessmentMapper.deleteByTeacherassessmentByIds(ids);
    }

    /**
     * 删除教师月绩效考核信息
     *
     * @param id 教师月绩效考核ID
     * @return 结果
     */
    @Override
    public int deleteByTeacherassessmentById(Long id) {
        return byTeacherassessmentMapper.deleteByTeacherassessmentById(id);
    }
}
