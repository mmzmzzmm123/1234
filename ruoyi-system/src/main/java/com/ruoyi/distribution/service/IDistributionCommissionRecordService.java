package com.ruoyi.distribution.service;

import java.util.List;
import com.ruoyi.distribution.domain.DistributionCommissionRecord;

/**
 * 分销佣金记录Service接口
 *
 * @author Lam
 * @date 2023-11-02
 */
public interface IDistributionCommissionRecordService {

    /**
     * 查询分销佣金记录
     *
     * @param id 分销佣金记录主键
     * @return 分销佣金记录
     */
    public DistributionCommissionRecord selectDistributionCommissionRecordById(Long id);

    /**
     * 查询分销佣金记录列表
     *
     * @param distributionCommissionRecord 分销佣金记录
     * @return 分销佣金记录集合
     */
    public List<DistributionCommissionRecord> selectDistributionCommissionRecordList(DistributionCommissionRecord distributionCommissionRecord);

    /**
     * 新增分销佣金记录
     *
     * @param distributionCommissionRecord 分销佣金记录
     * @return 结果
     */
    public int insertDistributionCommissionRecord(DistributionCommissionRecord distributionCommissionRecord);

    /**
     * 修改分销佣金记录
     *
     * @param distributionCommissionRecord 分销佣金记录
     * @return 结果
     */
    public int updateDistributionCommissionRecord(DistributionCommissionRecord distributionCommissionRecord);

    /**
     * 批量删除分销佣金记录
     *
     * @param ids 需要删除的分销佣金记录主键集合
     * @return 结果
     */
    public int deleteDistributionCommissionRecordByIds(Long[] ids);

    /**
     * 删除分销佣金记录信息
     *
     * @param id 分销佣金记录主键
     * @return 结果
     */
    public int deleteDistributionCommissionRecordById(Long id);
}
