package com.ruoyi.ufreight.mapper;

import java.util.List;
import com.ruoyi.ufreight.domain.InboundHistory;

/**
 * 入库流水Mapper接口
 * 
 * @author derek
 * @date 2023-11-20
 */
public interface InboundHistoryMapper 
{
    /**
     * 查询入库流水
     * 
     * @param inboundId 入库流水主键
     * @return 入库流水
     */
    public InboundHistory selectInboundHistoryByInboundId(Long inboundId);

    /**
     * 查询入库流水列表
     * 
     * @param inboundHistory 入库流水
     * @return 入库流水集合
     */
    public List<InboundHistory> selectInboundHistoryList(InboundHistory inboundHistory);

    /**
     * 新增入库流水
     * 
     * @param inboundHistory 入库流水
     * @return 结果
     */
    public int insertInboundHistory(InboundHistory inboundHistory);

    /**
     * 修改入库流水
     * 
     * @param inboundHistory 入库流水
     * @return 结果
     */
    public int updateInboundHistory(InboundHistory inboundHistory);

    /**
     * 删除入库流水
     * 
     * @param inboundId 入库流水主键
     * @return 结果
     */
    public int deleteInboundHistoryByInboundId(Long inboundId);

    /**
     * 批量删除入库流水
     * 
     * @param inboundIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInboundHistoryByInboundIds(Long[] inboundIds);
}
