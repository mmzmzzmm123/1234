package com.ruoyi.ufreight.service;

import java.util.List;
import com.ruoyi.ufreight.domain.OutboundHistory;

/**
 * 出库流水Service接口
 * 
 * @author derek
 * @date 2023-11-20
 */
public interface IOutboundHistoryService 
{
    /**
     * 查询出库流水
     * 
     * @param outboundId 出库流水主键
     * @return 出库流水
     */
    public OutboundHistory selectOutboundHistoryByOutboundId(Long outboundId);

    /**
     * 查询出库流水列表
     * 
     * @param outboundHistory 出库流水
     * @return 出库流水集合
     */
    public List<OutboundHistory> selectOutboundHistoryList(OutboundHistory outboundHistory);

    /**
     * 新增出库流水
     * 
     * @param outboundHistory 出库流水
     * @return 结果
     */
    public int insertOutboundHistory(OutboundHistory outboundHistory);

    /**
     * 修改出库流水
     * 
     * @param outboundHistory 出库流水
     * @return 结果
     */
    public int updateOutboundHistory(OutboundHistory outboundHistory);

    /**
     * 批量删除出库流水
     * 
     * @param outboundIds 需要删除的出库流水主键集合
     * @return 结果
     */
    public int deleteOutboundHistoryByOutboundIds(Long[] outboundIds);

    /**
     * 删除出库流水信息
     * 
     * @param outboundId 出库流水主键
     * @return 结果
     */
    public int deleteOutboundHistoryByOutboundId(Long outboundId);
}
