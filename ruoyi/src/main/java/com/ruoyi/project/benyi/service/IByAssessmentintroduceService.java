package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByAssessmentintroduce;

/**
 * 评估体系介绍Service接口
 *
 * @author tsbz
 * @date 2020-10-09
 */
public interface IByAssessmentintroduceService {
    /**
     * 查询评估体系介绍
     *
     * @param id 评估体系介绍ID
     * @return 评估体系介绍
     */
    public ByAssessmentintroduce selectByAssessmentintroduceById(Long id);

    /**
     * 查询评估体系介绍列表
     *
     * @param byAssessmentintroduce 评估体系介绍
     * @return 评估体系介绍集合
     */
    public List<ByAssessmentintroduce> selectByAssessmentintroduceList(ByAssessmentintroduce byAssessmentintroduce);

    /**
     * 新增评估体系介绍
     *
     * @param byAssessmentintroduce 评估体系介绍
     * @return 结果
     */
    public int insertByAssessmentintroduce(ByAssessmentintroduce byAssessmentintroduce);

    /**
     * 修改评估体系介绍
     *
     * @param byAssessmentintroduce 评估体系介绍
     * @return 结果
     */
    public int updateByAssessmentintroduce(ByAssessmentintroduce byAssessmentintroduce);

    /**
     * 批量删除评估体系介绍
     *
     * @param ids 需要删除的评估体系介绍ID
     * @return 结果
     */
    public int deleteByAssessmentintroduceByIds(Long[] ids);

    /**
     * 删除评估体系介绍信息
     *
     * @param id 评估体系介绍ID
     * @return 结果
     */
    public int deleteByAssessmentintroduceById(Long id);
}
