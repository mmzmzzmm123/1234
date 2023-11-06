package com.ruoyi.distribution.service;

import java.util.List;
import com.ruoyi.distribution.domain.DistributionCommissionRate;

/**
 * 分销佣金比例Service接口
 *
 * @author Lam
 * @date 2023-11-02
 */
public interface IDistributionCommissionRateService {

    /**
     * 查询分销佣金比例
     *
     * @param id 分销佣金比例主键
     * @return 分销佣金比例
     */
    public DistributionCommissionRate selectDistributionCommissionRateById(Long id);

    /**
     * 查询分销佣金比例列表
     *
     * @param distributionCommissionRate 分销佣金比例
     * @return 分销佣金比例集合
     */
    public List<DistributionCommissionRate> selectDistributionCommissionRateList(DistributionCommissionRate distributionCommissionRate);

    /**
     * 新增分销佣金比例
     *
     * @param distributionCommissionRate 分销佣金比例
     * @return 结果
     */
    public int insertDistributionCommissionRate(DistributionCommissionRate distributionCommissionRate);

    /**
     * 修改分销佣金比例
     *
     * @param distributionCommissionRate 分销佣金比例
     * @return 结果
     */
    public int updateDistributionCommissionRate(DistributionCommissionRate distributionCommissionRate);

    /**
     * 批量删除分销佣金比例
     *
     * @param ids 需要删除的分销佣金比例主键集合
     * @return 结果
     */
    public int deleteDistributionCommissionRateByIds(Long[] ids);

    /**
     * 删除分销佣金比例信息
     *
     * @param id 分销佣金比例主键
     * @return 结果
     */
    public int deleteDistributionCommissionRateById(Long id);
}
