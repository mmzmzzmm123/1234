package com.ruoyi.distribution.mapper;

import java.util.List;
import com.ruoyi.distribution.domain.DistributionRelation;

/**
 * 分销关系Mapper接口
 *
 * @author Lam
 * @date 2023-11-02
 */
public interface DistributionRelationMapper {

    /**
     * 查询分销关系
     *
     * @param id 分销关系主键
     * @return 分销关系
     */
    public DistributionRelation selectDistributionRelationById(Long id);

    /**
     * 查询分销关系列表
     *
     * @param distributionRelation 分销关系
     * @return 分销关系集合
     */
    public List<DistributionRelation> selectDistributionRelationList(DistributionRelation distributionRelation);

    /**
     * 记录查询子级用户信息列表
     *
     * @param distributionRelation 数据
     * @return 结果
     * */
    public List<DistributionRelation> selectJoinChildUserInfo(DistributionRelation distributionRelation);

    /**
     * 新增分销关系
     *
     * @param distributionRelation 分销关系
     * @return 结果
     */
    public int insertDistributionRelation(DistributionRelation distributionRelation);

    /**
     * 修改分销关系
     *
     * @param distributionRelation 分销关系
     * @return 结果
     */
    public int updateDistributionRelation(DistributionRelation distributionRelation);

    /**
     * 删除分销关系
     *
     * @param id 分销关系主键
     * @return 结果
     */
    public int deleteDistributionRelationById(Long id);

    /**
     * 批量删除分销关系
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDistributionRelationByIds(Long[] ids);
}
