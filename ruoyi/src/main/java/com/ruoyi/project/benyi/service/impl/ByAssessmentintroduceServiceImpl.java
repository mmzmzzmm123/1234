package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByAssessmentintroduceMapper;
import com.ruoyi.project.benyi.domain.ByAssessmentintroduce;
import com.ruoyi.project.benyi.service.IByAssessmentintroduceService;

/**
 * 评估体系介绍Service业务层处理
 *
 * @author tsbz
 * @date 2020-10-09
 */
@Service
public class ByAssessmentintroduceServiceImpl implements IByAssessmentintroduceService {
    @Autowired
    private ByAssessmentintroduceMapper byAssessmentintroduceMapper;

    /**
     * 查询评估体系介绍
     *
     * @param id 评估体系介绍ID
     * @return 评估体系介绍
     */
    @Override
    public ByAssessmentintroduce selectByAssessmentintroduceById(Long id) {
        return byAssessmentintroduceMapper.selectByAssessmentintroduceById(id);
    }

    /**
     * 查询评估体系介绍列表
     *
     * @param byAssessmentintroduce 评估体系介绍
     * @return 评估体系介绍
     */
    @Override
    public List<ByAssessmentintroduce> selectByAssessmentintroduceList(ByAssessmentintroduce byAssessmentintroduce) {
        return byAssessmentintroduceMapper.selectByAssessmentintroduceList(byAssessmentintroduce);
    }

    /**
     * 新增评估体系介绍
     *
     * @param byAssessmentintroduce 评估体系介绍
     * @return 结果
     */
    @Override
    public int insertByAssessmentintroduce(ByAssessmentintroduce byAssessmentintroduce) {
        byAssessmentintroduce.setCreateTime(DateUtils.getNowDate());
        return byAssessmentintroduceMapper.insertByAssessmentintroduce(byAssessmentintroduce);
    }

    /**
     * 修改评估体系介绍
     *
     * @param byAssessmentintroduce 评估体系介绍
     * @return 结果
     */
    @Override
    public int updateByAssessmentintroduce(ByAssessmentintroduce byAssessmentintroduce) {
        return byAssessmentintroduceMapper.updateByAssessmentintroduce(byAssessmentintroduce);
    }

    /**
     * 批量删除评估体系介绍
     *
     * @param ids 需要删除的评估体系介绍ID
     * @return 结果
     */
    @Override
    public int deleteByAssessmentintroduceByIds(Long[] ids) {
        return byAssessmentintroduceMapper.deleteByAssessmentintroduceByIds(ids);
    }

    /**
     * 删除评估体系介绍信息
     *
     * @param id 评估体系介绍ID
     * @return 结果
     */
    @Override
    public int deleteByAssessmentintroduceById(Long id) {
        return byAssessmentintroduceMapper.deleteByAssessmentintroduceById(id);
    }
}
