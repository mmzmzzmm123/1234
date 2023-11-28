package com.ruoyi.ufreight.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ufreight.mapper.PendingSkuMapper;
import com.ruoyi.ufreight.domain.PendingSku;
import com.ruoyi.ufreight.service.IPendingSkuService;

/**
 * 待处理skuService业务层处理
 * 
 * @author derek
 * @date 2023-11-20
 */
@Service
public class PendingSkuServiceImpl implements IPendingSkuService 
{
    @Autowired
    private PendingSkuMapper pendingSkuMapper;

    /**
     * 查询待处理sku
     * 
     * @param pendingId 待处理sku主键
     * @return 待处理sku
     */
    @Override
    public PendingSku selectPendingSkuByPendingId(Long pendingId)
    {
        return pendingSkuMapper.selectPendingSkuByPendingId(pendingId);
    }

    /**
     * 查询待处理sku列表
     * 
     * @param pendingSku 待处理sku
     * @return 待处理sku
     */
    @Override
    public List<PendingSku> selectPendingSkuList(PendingSku pendingSku)
    {
        return pendingSkuMapper.selectPendingSkuList(pendingSku);
    }

    /**
     * 新增待处理sku
     * 
     * @param pendingSku 待处理sku
     * @return 结果
     */
    @Override
    public int insertPendingSku(PendingSku pendingSku)
    {
        pendingSku.setCreateTime(DateUtils.getNowDate());
        return pendingSkuMapper.insertPendingSku(pendingSku);
    }

    /**
     * 修改待处理sku
     * 
     * @param pendingSku 待处理sku
     * @return 结果
     */
    @Override
    public int updatePendingSku(PendingSku pendingSku)
    {
        pendingSku.setUpdateTime(DateUtils.getNowDate());
        return pendingSkuMapper.updatePendingSku(pendingSku);
    }

    /**
     * 批量删除待处理sku
     * 
     * @param pendingIds 需要删除的待处理sku主键
     * @return 结果
     */
    @Override
    public int deletePendingSkuByPendingIds(Long[] pendingIds)
    {
        return pendingSkuMapper.deletePendingSkuByPendingIds(pendingIds);
    }

    /**
     * 删除待处理sku信息
     * 
     * @param pendingId 待处理sku主键
     * @return 结果
     */
    @Override
    public int deletePendingSkuByPendingId(Long pendingId)
    {
        return pendingSkuMapper.deletePendingSkuByPendingId(pendingId);
    }
}
