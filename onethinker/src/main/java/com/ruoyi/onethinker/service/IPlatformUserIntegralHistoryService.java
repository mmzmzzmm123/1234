package com.ruoyi.onethinker.service;

import java.util.List;
import com.ruoyi.onethinker.domain.PlatformUserIntegralHistory;

/**
 * 平台用户积分流水记录Service接口
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
public interface IPlatformUserIntegralHistoryService {
    /**
     * 查询平台用户积分流水记录
     *
     * @param id 平台用户积分流水记录主键
     * @return 平台用户积分流水记录
     */
    public PlatformUserIntegralHistory selectPlatformUserIntegralHistoryById(Long id);

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
     * 修改平台用户积分流水记录
     *
     * @param platformUserIntegralHistory 平台用户积分流水记录
     * @return 结果
     */
    public int updatePlatformUserIntegralHistory(PlatformUserIntegralHistory platformUserIntegralHistory);

    /**
     * 批量删除平台用户积分流水记录
     *
     * @param ids 需要删除的平台用户积分流水记录主键集合
     * @return 结果
     */
    public int deletePlatformUserIntegralHistoryByIds(Long[] ids);

    /**
     * 删除平台用户积分流水记录信息
     *
     * @param id 平台用户积分流水记录主键
     * @return 结果
     */
    public int deletePlatformUserIntegralHistoryById(Long id);
}
