package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByTeacherassessment;

/**
 * 教师月绩效考核Service接口
 *
 * @author tsbz
 * @date 2021-07-01
 */
public interface IByTeacherassessmentService {
    /**
     * 查询教师月绩效考核
     *
     * @param id 教师月绩效考核ID
     * @return 教师月绩效考核
     */
    public ByTeacherassessment selectByTeacherassessmentById(Long id);

    /**
     * 查询教师月绩效考核列表
     *
     * @param byTeacherassessment 教师月绩效考核
     * @return 教师月绩效考核集合
     */
    public List<ByTeacherassessment> selectByTeacherassessmentList(ByTeacherassessment byTeacherassessment);

    /**
     * 新增教师月绩效考核
     *
     * @param byTeacherassessment 教师月绩效考核
     * @return 结果
     */
    public int insertByTeacherassessment(ByTeacherassessment byTeacherassessment);

    /**
     * 修改教师月绩效考核
     *
     * @param byTeacherassessment 教师月绩效考核
     * @return 结果
     */
    public int updateByTeacherassessment(ByTeacherassessment byTeacherassessment);

    /**
     * 批量删除教师月绩效考核
     *
     * @param ids 需要删除的教师月绩效考核ID
     * @return 结果
     */
    public int deleteByTeacherassessmentByIds(Long[] ids);

    /**
     * 删除教师月绩效考核信息
     *
     * @param id 教师月绩效考核ID
     * @return 结果
     */
    public int deleteByTeacherassessmentById(Long id);
}
