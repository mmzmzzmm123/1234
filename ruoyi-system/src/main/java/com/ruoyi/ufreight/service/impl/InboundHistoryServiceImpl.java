package com.ruoyi.ufreight.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ufreight.mapper.InboundHistoryMapper;
import com.ruoyi.ufreight.domain.InboundHistory;
import com.ruoyi.ufreight.service.IInboundHistoryService;

/**
 * 入库流水Service业务层处理
 * 
 * @author derek
 * @date 2023-11-20
 */
@Service
public class InboundHistoryServiceImpl implements IInboundHistoryService 
{
    @Autowired
    private InboundHistoryMapper inboundHistoryMapper;

    /**
     * 查询入库流水
     * 
     * @param inboundId 入库流水主键
     * @return 入库流水
     */
    @Override
    public InboundHistory selectInboundHistoryByInboundId(Long inboundId)
    {
        return inboundHistoryMapper.selectInboundHistoryByInboundId(inboundId);
    }

    /**
     * 查询入库流水列表
     * 
     * @param inboundHistory 入库流水
     * @return 入库流水
     */
    @Override
    public List<InboundHistory> selectInboundHistoryList(InboundHistory inboundHistory)
    {
        return inboundHistoryMapper.selectInboundHistoryList(inboundHistory);
    }

    /**
     * 新增入库流水
     * 
     * @param inboundHistory 入库流水
     * @return 结果
     */
    @Override
    public int insertInboundHistory(InboundHistory inboundHistory)
    {
        inboundHistory.setCreateTime(DateUtils.getNowDate());
        return inboundHistoryMapper.insertInboundHistory(inboundHistory);
    }

    /**
     * 修改入库流水
     * 
     * @param inboundHistory 入库流水
     * @return 结果
     */
    @Override
    public int updateInboundHistory(InboundHistory inboundHistory)
    {
        inboundHistory.setUpdateTime(DateUtils.getNowDate());
        return inboundHistoryMapper.updateInboundHistory(inboundHistory);
    }

    /**
     * 批量删除入库流水
     * 
     * @param inboundIds 需要删除的入库流水主键
     * @return 结果
     */
    @Override
    public int deleteInboundHistoryByInboundIds(Long[] inboundIds)
    {
        return inboundHistoryMapper.deleteInboundHistoryByInboundIds(inboundIds);
    }

    /**
     * 删除入库流水信息
     * 
     * @param inboundId 入库流水主键
     * @return 结果
     */
    @Override
    public int deleteInboundHistoryByInboundId(Long inboundId)
    {
        return inboundHistoryMapper.deleteInboundHistoryByInboundId(inboundId);
    }
}
