package com.onethinker.user.service;

import com.onethinker.user.domain.PlatformUserIntegralHistory;

import java.util.List;

/**
 * 平台用户积分流水记录Service接口
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
public interface IPlatformUserIntegralHistoryService {


    /**
     * 查询平台用户积分流水记录列表
     *
     * @param platformUserIntegralHistory 平台用户积分流水记录
     * @return 平台用户积分流水记录集合
     */
    public List<PlatformUserIntegralHistory> selectPlatformUserIntegralHistoryList(PlatformUserIntegralHistory platformUserIntegralHistory);

    /**
     * 新增平台用户积分流水记录
     *
     * @param platformUserIntegralHistory 平台用户积分流水记录
     * @return 结果
     */
    public int insertPlatformUserIntegralHistory(PlatformUserIntegralHistory platformUserIntegralHistory);

    /**
     * 查询积分提现列表
     * @param platformUserIntegralHistory
     * @return
     */
    List<PlatformUserIntegralHistory> withdrawalIntegralList(PlatformUserIntegralHistory platformUserIntegralHistory);

    /**
     * 提现审核
     * @param platformUserIntegralHistory
     * @return
     */
    int updateWithdrawalIntegral(PlatformUserIntegralHistory platformUserIntegralHistory);
}
