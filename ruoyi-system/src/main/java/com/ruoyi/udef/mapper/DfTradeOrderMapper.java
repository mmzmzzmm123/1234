package com.ruoyi.udef.mapper;

import java.util.List;
import com.ruoyi.udef.domain.DfTradeOrder;

/**
 * 成交订单Mapper接口
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
public interface DfTradeOrderMapper 
{
    /**
     * 查询成交订单
     * 
     * @param id 成交订单主键
     * @return 成交订单
     */
    public DfTradeOrder selectDfTradeOrderById(String id);

    /**
     * 查询成交订单列表
     * 
     * @param dfTradeOrder 成交订单
     * @return 成交订单集合
     */
    public List<DfTradeOrder> selectDfTradeOrderList(DfTradeOrder dfTradeOrder);

    /**
     * 新增成交订单
     * 
     * @param dfTradeOrder 成交订单
     * @return 结果
     */
    public int insertDfTradeOrder(DfTradeOrder dfTradeOrder);

    /**
     * 修改成交订单
     * 
     * @param dfTradeOrder 成交订单
     * @return 结果
     */
    public int updateDfTradeOrder(DfTradeOrder dfTradeOrder);

    /**
     * 删除成交订单
     * 
     * @param id 成交订单主键
     * @return 结果
     */
    public int deleteDfTradeOrderById(String id);

    /**
     * 批量删除成交订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDfTradeOrderByIds(String[] ids);

    public List<DfTradeOrder> selectDfTradeOrderLatest(DfTradeOrder dfTradeOrder);
}
