package com.ruoyi.distribution.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.distribution.mapper.DistributionRelationMapper;
import com.ruoyi.distribution.domain.DistributionRelation;
import com.ruoyi.distribution.service.IDistributionRelationService;

/**
 * 分销关系Service业务层处理
 *
 * @author Lam
 * @date 2023-11-02
 */
@Service
@RequiredArgsConstructor
public class DistributionRelationServiceImpl implements IDistributionRelationService {

    private final DistributionRelationMapper distributionRelationMapper;

    /**
     * 查询分销关系
     *
     * @param id 分销关系主键
     * @return 分销关系
     */
    @Override
    public DistributionRelation selectDistributionRelationById(Long id) {
        return distributionRelationMapper.selectDistributionRelationById(id);
    }

    /**
     * 查询分销关系列表
     *
     * @param distributionRelation 分销关系
     * @return 分销关系
     */
    @Override
    public List<DistributionRelation> selectDistributionRelationList(DistributionRelation distributionRelation) {
        return distributionRelationMapper.selectDistributionRelationList(distributionRelation);
    }

    /**
     * 新增分销关系
     *
     * @param distributionRelation 分销关系
     * @return 结果
     */
    @Override
    public int insertDistributionRelation(DistributionRelation distributionRelation) {
        distributionRelation.setCreateTime(DateUtils.getNowDate());
        return distributionRelationMapper.insertDistributionRelation(distributionRelation);
    }

    /**
     * 修改分销关系
     *
     * @param distributionRelation 分销关系
     * @return 结果
     */
    @Override
    public int updateDistributionRelation(DistributionRelation distributionRelation) {
        return distributionRelationMapper.updateDistributionRelation(distributionRelation);
    }

    /**
     * 批量删除分销关系
     *
     * @param ids 需要删除的分销关系主键
     * @return 结果
     */
    @Override
    public int deleteDistributionRelationByIds(Long[] ids) {
        return distributionRelationMapper.deleteDistributionRelationByIds(ids);
    }

    /**
     * 删除分销关系信息
     *
     * @param id 分销关系主键
     * @return 结果
     */
    @Override
    public int deleteDistributionRelationById(Long id) {
        return distributionRelationMapper.deleteDistributionRelationById(id);
    }
}
