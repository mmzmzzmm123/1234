package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByAssessmentrecordchild;

/**
 * 幼儿评估记录Service接口
 *
 * @author tsbz
 * @date 2020-10-27
 */
public interface IByAssessmentrecordchildService {
    /**
     * 查询幼儿评估记录
     *
     * @param id 幼儿评估记录ID
     * @return 幼儿评估记录
     */
    public ByAssessmentrecordchild selectByAssessmentrecordchildById(Long id);

    /**
     * 查询幼儿评估记录列表
     *
     * @param byAssessmentrecordchild 幼儿评估记录
     * @return 幼儿评估记录集合
     */
    public List<ByAssessmentrecordchild> selectByAssessmentrecordchildList(ByAssessmentrecordchild byAssessmentrecordchild);

    /**
     * 新增幼儿评估记录
     *
     * @param byAssessmentrecordchild 幼儿评估记录
     * @return 结果
     */
    public int insertByAssessmentrecordchild(ByAssessmentrecordchild byAssessmentrecordchild);

    /**
     * 修改幼儿评估记录
     *
     * @param byAssessmentrecordchild 幼儿评估记录
     * @return 结果
     */
    public int updateByAssessmentrecordchild(ByAssessmentrecordchild byAssessmentrecordchild);

    /**
     * 批量删除幼儿评估记录
     *
     * @param ids 需要删除的幼儿评估记录ID
     * @return 结果
     */
    public int deleteByAssessmentrecordchildByIds(Long[] ids);

    /**
     * 删除幼儿评估记录信息
     *
     * @param id 幼儿评估记录ID
     * @return 结果
     */
    public int deleteByAssessmentrecordchildById(Long id);
}
