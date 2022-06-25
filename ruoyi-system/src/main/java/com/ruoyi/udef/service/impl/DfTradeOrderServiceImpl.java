package com.ruoyi.udef.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.udef.mapper.DfTradeOrderMapper;
import com.ruoyi.udef.domain.DfTradeOrder;
import com.ruoyi.udef.service.IDfTradeOrderService;

/**
 * 成交订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
@Service
public class DfTradeOrderServiceImpl implements IDfTradeOrderService 
{
    @Autowired
    private DfTradeOrderMapper dfTradeOrderMapper;

    /**
     * 查询成交订单
     * 
     * @param id 成交订单主键
     * @return 成交订单
     */
    @Override
    public DfTradeOrder selectDfTradeOrderById(String id)
    {
        return dfTradeOrderMapper.selectDfTradeOrderById(id);
    }

    /**
     * 查询成交订单列表
     * 
     * @param dfTradeOrder 成交订单
     * @return 成交订单
     */
    @Override
    public List<DfTradeOrder> selectDfTradeOrderList(DfTradeOrder dfTradeOrder)
    {
        return dfTradeOrderMapper.selectDfTradeOrderList(dfTradeOrder);
    }

    /**
     * 新增成交订单
     * 
     * @param dfTradeOrder 成交订单
     * @return 结果
     */
    @Override
    public int insertDfTradeOrder(DfTradeOrder dfTradeOrder)
    {
        return dfTradeOrderMapper.insertDfTradeOrder(dfTradeOrder);
    }

    /**
     * 修改成交订单
     * 
     * @param dfTradeOrder 成交订单
     * @return 结果
     */
    @Override
    public int updateDfTradeOrder(DfTradeOrder dfTradeOrder)
    {
        return dfTradeOrderMapper.updateDfTradeOrder(dfTradeOrder);
    }

    /**
     * 批量删除成交订单
     * 
     * @param ids 需要删除的成交订单主键
     * @return 结果
     */
    @Override
    public int deleteDfTradeOrderByIds(String[] ids)
    {
        return dfTradeOrderMapper.deleteDfTradeOrderByIds(ids);
    }

    /**
     * 删除成交订单信息
     * 
     * @param id 成交订单主键
     * @return 结果
     */
    @Override
    public int deleteDfTradeOrderById(String id)
    {
        return dfTradeOrderMapper.deleteDfTradeOrderById(id);
    }
}
