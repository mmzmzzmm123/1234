package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByAssessmentrecordchildMapper;
import com.ruoyi.project.benyi.domain.ByAssessmentrecordchild;
import com.ruoyi.project.benyi.service.IByAssessmentrecordchildService;

/**
 * 幼儿评估记录Service业务层处理
 *
 * @author tsbz
 * @date 2020-10-27
 */
@Service
public class ByAssessmentrecordchildServiceImpl implements IByAssessmentrecordchildService {
    @Autowired
    private ByAssessmentrecordchildMapper byAssessmentrecordchildMapper;

    /**
     * 查询幼儿评估记录
     *
     * @param id 幼儿评估记录ID
     * @return 幼儿评估记录
     */
    @Override
    public ByAssessmentrecordchild selectByAssessmentrecordchildById(Long id) {
        return byAssessmentrecordchildMapper.selectByAssessmentrecordchildById(id);
    }

    /**
     * 查询幼儿评估记录列表
     *
     * @param byAssessmentrecordchild 幼儿评估记录
     * @return 幼儿评估记录
     */
    @Override
    public List<ByAssessmentrecordchild> selectByAssessmentrecordchildList(ByAssessmentrecordchild byAssessmentrecordchild) {
        return byAssessmentrecordchildMapper.selectByAssessmentrecordchildList(byAssessmentrecordchild);
    }

    /**
     * 新增幼儿评估记录
     *
     * @param byAssessmentrecordchild 幼儿评估记录
     * @return 结果
     */
    @Override
    public int insertByAssessmentrecordchild(ByAssessmentrecordchild byAssessmentrecordchild) {
        byAssessmentrecordchild.setCreateTime(DateUtils.getNowDate());
        return byAssessmentrecordchildMapper.insertByAssessmentrecordchild(byAssessmentrecordchild);
    }

    /**
     * 修改幼儿评估记录
     *
     * @param byAssessmentrecordchild 幼儿评估记录
     * @return 结果
     */
    @Override
    public int updateByAssessmentrecordchild(ByAssessmentrecordchild byAssessmentrecordchild) {
        return byAssessmentrecordchildMapper.updateByAssessmentrecordchild(byAssessmentrecordchild);
    }

    /**
     * 批量删除幼儿评估记录
     *
     * @param ids 需要删除的幼儿评估记录ID
     * @return 结果
     */
    @Override
    public int deleteByAssessmentrecordchildByIds(Long[] ids) {
        return byAssessmentrecordchildMapper.deleteByAssessmentrecordchildByIds(ids);
    }

    /**
     * 删除幼儿评估记录信息
     *
     * @param id 幼儿评估记录ID
     * @return 结果
     */
    @Override
    public int deleteByAssessmentrecordchildById(Long id) {
        return byAssessmentrecordchildMapper.deleteByAssessmentrecordchildById(id);
    }
}
