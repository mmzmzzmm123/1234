package com.ruoyi.ufreight.service;

import java.util.List;
import com.ruoyi.ufreight.domain.PendingSku;

/**
 * 待处理skuService接口
 * 
 * @author derek
 * @date 2023-11-20
 */
public interface IPendingSkuService 
{
    /**
     * 查询待处理sku
     * 
     * @param pendingId 待处理sku主键
     * @return 待处理sku
     */
    public PendingSku selectPendingSkuByPendingId(Long pendingId);

    /**
     * 查询待处理sku列表
     * 
     * @param pendingSku 待处理sku
     * @return 待处理sku集合
     */
    public List<PendingSku> selectPendingSkuList(PendingSku pendingSku);

    /**
     * 新增待处理sku
     * 
     * @param pendingSku 待处理sku
     * @return 结果
     */
    public int insertPendingSku(PendingSku pendingSku);

    /**
     * 修改待处理sku
     * 
     * @param pendingSku 待处理sku
     * @return 结果
     */
    public int updatePendingSku(PendingSku pendingSku);

    /**
     * 批量删除待处理sku
     * 
     * @param pendingIds 需要删除的待处理sku主键集合
     * @return 结果
     */
    public int deletePendingSkuByPendingIds(Long[] pendingIds);

    /**
     * 删除待处理sku信息
     * 
     * @param pendingId 待处理sku主键
     * @return 结果
     */
    public int deletePendingSkuByPendingId(Long pendingId);
}
