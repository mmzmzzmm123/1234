package com.ruoyi.api.distribution.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.distribution.model.dto.ApiPageDistributionRelationDto;
import com.ruoyi.common.utils.TokenUtils;
import com.ruoyi.distribution.domain.DistributionCommissionRecord;
import com.ruoyi.distribution.domain.DistributionRelation;
import com.ruoyi.distribution.mapper.DistributionCommissionRecordMapper;
import com.ruoyi.distribution.mapper.DistributionRelationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LAM
 * @date 2023/11/4 20:01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiDistributionService {

    private final DistributionRelationMapper distributionRelationMapper;
    private final DistributionCommissionRecordMapper commissionRecordMapper;

    /**
     * 根据条件查询分销关系数据
     *
     * @param dto 查询条件
     * @return 结果
     * */
    public List<DistributionRelation> selectRelation(ApiPageDistributionRelationDto dto) {
        DistributionRelation select = new DistributionRelation();
        select.setPId(TokenUtils.getUserId());
        if (ObjectUtil.isNotNull(dto.getLevel())){
            select.setLevel(dto.getLevel());
        }
        return distributionRelationMapper.selectJoinChildUserInfo(select);
    }

    /**
     * 查询分销佣金记录数据
     *
     * @return 结果
     * */
    public List<DistributionCommissionRecord> selectCommissionRecord(){
        DistributionCommissionRecord select = new DistributionCommissionRecord();
        select.setUserId(TokenUtils.getUserId());
        return commissionRecordMapper.selectJoinDistributionUser(select);
    }
}
