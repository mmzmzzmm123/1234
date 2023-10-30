package com.ruoyi.onethinker.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.ruoyi.common.enums.IntegralTypeEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.onethinker.domain.PlatformUserIntegralHistory;
import com.ruoyi.onethinker.mapper.PlatformUserIntegralHistoryMapper;
import com.ruoyi.onethinker.service.IPlatformUserIntegralHistoryService;
import lombok.extern.log4j.Log4j2;

/**
 * 平台用户积分流水记录Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
@Service
@Log4j2
public class PlatformUserIntegralHistoryServiceImpl implements IPlatformUserIntegralHistoryService {
    @Resource
    private PlatformUserIntegralHistoryMapper platformUserIntegralHistoryMapper;

    /**
     * 查询平台用户积分流水记录列表
     *
     * @param platformUserIntegralHistory 平台用户积分流水记录
     * @return 平台用户积分流水记录
     */
    @Override
    public List<PlatformUserIntegralHistory> selectPlatformUserIntegralHistoryList(PlatformUserIntegralHistory platformUserIntegralHistory) {
        return platformUserIntegralHistoryMapper.selectPlatformUserIntegralHistoryList(platformUserIntegralHistory);
    }

    /**
     * 新增平台用户积分流水记录
     *
     * @param platformUserIntegralHistory 平台用户积分流水记录
     * @return 结果
     */
    @Override
    public int insertPlatformUserIntegralHistory(PlatformUserIntegralHistory platformUserIntegralHistory) {
        platformUserIntegralHistory.setCreateTime(DateUtils.getNowDate());
        if (ObjectUtils.isEmpty(platformUserIntegralHistory.getEnabled())) {
            platformUserIntegralHistory.setEnabled(PlatformUserIntegralHistory.STATE_TYPE_ENABLED);
        }
        platformUserIntegralHistory.setSysUserId(SecurityUtils.getUserId());
        return platformUserIntegralHistoryMapper.insertPlatformUserIntegralHistory(platformUserIntegralHistory);
    }

    @Override
    public List<PlatformUserIntegralHistory> withdrawalIntegralList(PlatformUserIntegralHistory platformUserIntegralHistory) {
        platformUserIntegralHistory.setEnabled(IntegralTypeEnum.CASH_WITHDRAWAL.getCode());
        return platformUserIntegralHistoryMapper.selectPlatformUserIntegralHistoryList(platformUserIntegralHistory);
    }

    @Override
    public int updateWithdrawalIntegral(PlatformUserIntegralHistory platformUserIntegralHistory) {
        Assert.isTrue(!ObjectUtils.isEmpty(platformUserIntegralHistory.getId()),"参数缺失");
        Assert.isTrue(!ObjectUtils.isEmpty(platformUserIntegralHistory.getEnabled()),"参数缺失");
        PlatformUserIntegralHistory saveParams = new PlatformUserIntegralHistory();
        saveParams.setSysUserId(SecurityUtils.getUserId());
        saveParams.setId(platformUserIntegralHistory.getId());
        saveParams.setEnabled(platformUserIntegralHistory.getEnabled());
        return platformUserIntegralHistoryMapper.updatePlatformUserIntegralHistory(saveParams);
    }
}
