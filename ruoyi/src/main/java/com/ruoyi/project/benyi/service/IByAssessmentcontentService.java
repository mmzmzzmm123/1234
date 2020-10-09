package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByAssessmentcontent;

/**
 * 评估内容Service接口
 *
 * @author tsbz
 * @date 2020-10-09
 */
public interface IByAssessmentcontentService {
    /**
     * 查询评估内容
     *
     * @param id 评估内容ID
     * @return 评估内容
     */
    public ByAssessmentcontent selectByAssessmentcontentById(Long id);

    /**
     * 查询评估内容列表
     *
     * @param byAssessmentcontent 评估内容
     * @return 评估内容集合
     */
    public List<ByAssessmentcontent> selectByAssessmentcontentList(ByAssessmentcontent byAssessmentcontent);

    /**
     * 新增评估内容
     *
     * @param byAssessmentcontent 评估内容
     * @return 结果
     */
    public int insertByAssessmentcontent(ByAssessmentcontent byAssessmentcontent);

    /**
     * 修改评估内容
     *
     * @param byAssessmentcontent 评估内容
     * @return 结果
     */
    public int updateByAssessmentcontent(ByAssessmentcontent byAssessmentcontent);

    /**
     * 批量删除评估内容
     *
     * @param ids 需要删除的评估内容ID
     * @return 结果
     */
    public int deleteByAssessmentcontentByIds(Long[] ids);

    /**
     * 删除评估内容信息
     *
     * @param id 评估内容ID
     * @return 结果
     */
    public int deleteByAssessmentcontentById(Long id);
}
