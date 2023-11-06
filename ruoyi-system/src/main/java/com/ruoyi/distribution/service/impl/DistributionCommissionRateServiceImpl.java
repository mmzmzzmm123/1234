package com.ruoyi.distribution.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.distribution.mapper.DistributionCommissionRateMapper;
import com.ruoyi.distribution.domain.DistributionCommissionRate;
import com.ruoyi.distribution.service.IDistributionCommissionRateService;

/**
 * 分销佣金比例Service业务层处理
 *
 * @author Lam
 * @date 2023-11-02
 */
@Service
@RequiredArgsConstructor
public class DistributionCommissionRateServiceImpl implements IDistributionCommissionRateService {

    private final DistributionCommissionRateMapper distributionCommissionRateMapper;

    /**
     * 查询分销佣金比例
     *
     * @param id 分销佣金比例主键
     * @return 分销佣金比例
     */
    @Override
    public DistributionCommissionRate selectDistributionCommissionRateById(Long id) {
        return distributionCommissionRateMapper.selectDistributionCommissionRateById(id);
    }

    /**
     * 查询分销佣金比例列表
     *
     * @param distributionCommissionRate 分销佣金比例
     * @return 分销佣金比例
     */
    @Override
    public List<DistributionCommissionRate> selectDistributionCommissionRateList(DistributionCommissionRate distributionCommissionRate) {
        return distributionCommissionRateMapper.selectDistributionCommissionRateList(distributionCommissionRate);
    }

    /**
     * 新增分销佣金比例
     *
     * @param distributionCommissionRate 分销佣金比例
     * @return 结果
     */
    @Override
    public int insertDistributionCommissionRate(DistributionCommissionRate distributionCommissionRate) {
        // 判断是否已存在该目标层级
        DistributionCommissionRate select = new DistributionCommissionRate();
        select.setLevel(distributionCommissionRate.getLevel());
        if (ObjectUtil.isNotEmpty(distributionCommissionRateMapper.selectDistributionCommissionRateList(select))){
            throw new ServiceException("该层级已存在记录，请勿重复添加");
        }
        Date now = DateUtils.getNowDate();
        String sysName = SecurityUtils.getUsername();
        distributionCommissionRate.setCreateTime(now)
                .setUpdateTime(now)
                .setCreateBy(sysName)
                .setUpdateBy(sysName);
        return distributionCommissionRateMapper.insertDistributionCommissionRate(distributionCommissionRate);
    }

    /**
     * 修改分销佣金比例
     *
     * @param distributionCommissionRate 分销佣金比例
     * @return 结果
     */
    @Override
    public int updateDistributionCommissionRate(DistributionCommissionRate distributionCommissionRate) {
        // 判断是否已存在该目标层级
        DistributionCommissionRate select = new DistributionCommissionRate();
        select.setLevel(distributionCommissionRate.getLevel());
        List<DistributionCommissionRate> distributionCommissionRates = distributionCommissionRateMapper.selectDistributionCommissionRateList(select);
        distributionCommissionRates = distributionCommissionRates.stream().filter(item -> !item.getId().equals(distributionCommissionRate.getId())).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(distributionCommissionRates)){
            throw new ServiceException("当前修改的层级已存在，请检查");
        }
        distributionCommissionRate.setUpdateBy(SecurityUtils.getUsername());
        distributionCommissionRate.setUpdateTime(DateUtils.getNowDate());
        return distributionCommissionRateMapper.updateDistributionCommissionRate(distributionCommissionRate);
    }

    /**
     * 批量删除分销佣金比例
     *
     * @param ids 需要删除的分销佣金比例主键
     * @return 结果
     */
    @Override
    public int deleteDistributionCommissionRateByIds(Long[] ids) {
        return distributionCommissionRateMapper.deleteDistributionCommissionRateByIds(ids);
    }

    /**
     * 删除分销佣金比例信息
     *
     * @param id 分销佣金比例主键
     * @return 结果
     */
    @Override
    public int deleteDistributionCommissionRateById(Long id) {
        return distributionCommissionRateMapper.deleteDistributionCommissionRateById(id);
    }
}
