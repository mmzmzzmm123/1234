package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByAssessmentchild;

/**
 * 幼儿评估Mapper接口
 *
 * @author tsbz
 * @date 2020-10-27
 */
public interface ByAssessmentchildMapper {
    /**
     * 查询幼儿评估
     *
     * @param id 幼儿评估ID
     * @return 幼儿评估
     */
    public ByAssessmentchild selectByAssessmentchildById(Long id);

    /**
     * 查询幼儿评估列表
     *
     * @param byAssessmentchild 幼儿评估
     * @return 幼儿评估集合
     */
    public List<ByAssessmentchild> selectByAssessmentchildList(ByAssessmentchild byAssessmentchild);

    /**
     * 新增幼儿评估
     *
     * @param byAssessmentchild 幼儿评估
     * @return 结果
     */
    public int insertByAssessmentchild(ByAssessmentchild byAssessmentchild);

    /**
     * 修改幼儿评估
     *
     * @param byAssessmentchild 幼儿评估
     * @return 结果
     */
    public int updateByAssessmentchild(ByAssessmentchild byAssessmentchild);

    /**
     * 删除幼儿评估
     *
     * @param id 幼儿评估ID
     * @return 结果
     */
    public int deleteByAssessmentchildById(Long id);

    /**
     * 批量删除幼儿评估
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByAssessmentchildByIds(Long[] ids);
}
