package com.ruoyi.distribution.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.distribution.mapper.DistributionCommissionRecordMapper;
import com.ruoyi.distribution.domain.DistributionCommissionRecord;
import com.ruoyi.distribution.service.IDistributionCommissionRecordService;

/**
 * 分销佣金记录Service业务层处理
 *
 * @author Lam
 * @date 2023-11-02
 */
@Service
@RequiredArgsConstructor
public class DistributionCommissionRecordServiceImpl implements IDistributionCommissionRecordService {

    private final DistributionCommissionRecordMapper distributionCommissionRecordMapper;

    /**
     * 查询分销佣金记录
     *
     * @param id 分销佣金记录主键
     * @return 分销佣金记录
     */
    @Override
    public DistributionCommissionRecord selectDistributionCommissionRecordById(Long id) {
        return distributionCommissionRecordMapper.selectDistributionCommissionRecordById(id);
    }

    /**
     * 查询分销佣金记录列表
     *
     * @param distributionCommissionRecord 分销佣金记录
     * @return 分销佣金记录
     */
    @Override
    public List<DistributionCommissionRecord> selectDistributionCommissionRecordList(DistributionCommissionRecord distributionCommissionRecord) {
        return distributionCommissionRecordMapper.selectDistributionCommissionRecordList(distributionCommissionRecord);
    }

    /**
     * 新增分销佣金记录
     *
     * @param distributionCommissionRecord 分销佣金记录
     * @return 结果
     */
    @Override
    public int insertDistributionCommissionRecord(DistributionCommissionRecord distributionCommissionRecord) {
        distributionCommissionRecord.setCreateTime(DateUtils.getNowDate());
        return distributionCommissionRecordMapper.insertDistributionCommissionRecord(distributionCommissionRecord);
    }

    /**
     * 修改分销佣金记录
     *
     * @param distributionCommissionRecord 分销佣金记录
     * @return 结果
     */
    @Override
    public int updateDistributionCommissionRecord(DistributionCommissionRecord distributionCommissionRecord) {
        return distributionCommissionRecordMapper.updateDistributionCommissionRecord(distributionCommissionRecord);
    }

    /**
     * 批量删除分销佣金记录
     *
     * @param ids 需要删除的分销佣金记录主键
     * @return 结果
     */
    @Override
    public int deleteDistributionCommissionRecordByIds(Long[] ids) {
        return distributionCommissionRecordMapper.deleteDistributionCommissionRecordByIds(ids);
    }

    /**
     * 删除分销佣金记录信息
     *
     * @param id 分销佣金记录主键
     * @return 结果
     */
    @Override
    public int deleteDistributionCommissionRecordById(Long id) {
        return distributionCommissionRecordMapper.deleteDistributionCommissionRecordById(id);
    }
}
