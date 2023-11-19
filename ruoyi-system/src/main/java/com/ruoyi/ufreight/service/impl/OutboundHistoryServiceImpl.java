package com.ruoyi.ufreight.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ufreight.mapper.OutboundHistoryMapper;
import com.ruoyi.ufreight.domain.OutboundHistory;
import com.ruoyi.ufreight.service.IOutboundHistoryService;

/**
 * 出库流水Service业务层处理
 * 
 * @author derek
 * @date 2023-11-20
 */
@Service
public class OutboundHistoryServiceImpl implements IOutboundHistoryService 
{
    @Autowired
    private OutboundHistoryMapper outboundHistoryMapper;

    /**
     * 查询出库流水
     * 
     * @param outboundId 出库流水主键
     * @return 出库流水
     */
    @Override
    public OutboundHistory selectOutboundHistoryByOutboundId(Long outboundId)
    {
        return outboundHistoryMapper.selectOutboundHistoryByOutboundId(outboundId);
    }

    /**
     * 查询出库流水列表
     * 
     * @param outboundHistory 出库流水
     * @return 出库流水
     */
    @Override
    public List<OutboundHistory> selectOutboundHistoryList(OutboundHistory outboundHistory)
    {
        return outboundHistoryMapper.selectOutboundHistoryList(outboundHistory);
    }

    /**
     * 新增出库流水
     * 
     * @param outboundHistory 出库流水
     * @return 结果
     */
    @Override
    public int insertOutboundHistory(OutboundHistory outboundHistory)
    {
        outboundHistory.setCreateTime(DateUtils.getNowDate());
        return outboundHistoryMapper.insertOutboundHistory(outboundHistory);
    }

    /**
     * 修改出库流水
     * 
     * @param outboundHistory 出库流水
     * @return 结果
     */
    @Override
    public int updateOutboundHistory(OutboundHistory outboundHistory)
    {
        outboundHistory.setUpdateTime(DateUtils.getNowDate());
        return outboundHistoryMapper.updateOutboundHistory(outboundHistory);
    }

    /**
     * 批量删除出库流水
     * 
     * @param outboundIds 需要删除的出库流水主键
     * @return 结果
     */
    @Override
    public int deleteOutboundHistoryByOutboundIds(Long[] outboundIds)
    {
        return outboundHistoryMapper.deleteOutboundHistoryByOutboundIds(outboundIds);
    }

    /**
     * 删除出库流水信息
     * 
     * @param outboundId 出库流水主键
     * @return 结果
     */
    @Override
    public int deleteOutboundHistoryByOutboundId(Long outboundId)
    {
        return outboundHistoryMapper.deleteOutboundHistoryByOutboundId(outboundId);
    }
}
