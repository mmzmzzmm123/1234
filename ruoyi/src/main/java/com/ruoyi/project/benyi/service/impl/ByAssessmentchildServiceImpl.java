package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByAssessmentchildMapper;
import com.ruoyi.project.benyi.domain.ByAssessmentchild;
import com.ruoyi.project.benyi.service.IByAssessmentchildService;

/**
 * 幼儿评估Service业务层处理
 *
 * @author tsbz
 * @date 2020-10-27
 */
@Service
public class ByAssessmentchildServiceImpl implements IByAssessmentchildService {
    @Autowired
    private ByAssessmentchildMapper byAssessmentchildMapper;

    /**
     * 查询幼儿评估
     *
     * @param id 幼儿评估ID
     * @return 幼儿评估
     */
    @Override
    public ByAssessmentchild selectByAssessmentchildById(Long id) {
        return byAssessmentchildMapper.selectByAssessmentchildById(id);
    }

    /**
     * 查询幼儿评估列表
     *
     * @param byAssessmentchild 幼儿评估
     * @return 幼儿评估
     */
    @Override
    public List<ByAssessmentchild> selectByAssessmentchildList(ByAssessmentchild byAssessmentchild) {
        return byAssessmentchildMapper.selectByAssessmentchildList(byAssessmentchild);
    }

    /**
     * 新增幼儿评估
     *
     * @param byAssessmentchild 幼儿评估
     * @return 结果
     */
    @Override
    public int insertByAssessmentchild(ByAssessmentchild byAssessmentchild) {
        byAssessmentchild.setCreateTime(DateUtils.getNowDate());
        return byAssessmentchildMapper.insertByAssessmentchild(byAssessmentchild);
    }

    /**
     * 修改幼儿评估
     *
     * @param byAssessmentchild 幼儿评估
     * @return 结果
     */
    @Override
    public int updateByAssessmentchild(ByAssessmentchild byAssessmentchild) {
        return byAssessmentchildMapper.updateByAssessmentchild(byAssessmentchild);
    }

    /**
     * 批量删除幼儿评估
     *
     * @param ids 需要删除的幼儿评估ID
     * @return 结果
     */
    @Override
    public int deleteByAssessmentchildByIds(Long[] ids) {
        return byAssessmentchildMapper.deleteByAssessmentchildByIds(ids);
    }

    /**
     * 删除幼儿评估信息
     *
     * @param id 幼儿评估ID
     * @return 结果
     */
    @Override
    public int deleteByAssessmentchildById(Long id) {
        return byAssessmentchildMapper.deleteByAssessmentchildById(id);
    }
}
