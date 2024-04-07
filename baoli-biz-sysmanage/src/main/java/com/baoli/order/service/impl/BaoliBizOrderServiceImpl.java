package com.baoli.order.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.order.mapper.BaoliBizOrderMapper;
import com.baoli.order.domain.BaoliBizOrder;
import com.baoli.order.service.IBaoliBizOrderService;

/**
 * 订单Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-07
 */
@Service
public class BaoliBizOrderServiceImpl implements IBaoliBizOrderService 
{
    @Autowired
    private BaoliBizOrderMapper baoliBizOrderMapper;

    /**
     * 查询订单
     * 
     * @param id 订单主键
     * @return 订单
     */
    @Override
    public BaoliBizOrder selectBaoliBizOrderById(Long id)
    {
        return baoliBizOrderMapper.selectBaoliBizOrderById(id);
    }

    /**
     * 查询订单列表
     * 
     * @param baoliBizOrder 订单
     * @return 订单
     */
    @Override
    public List<BaoliBizOrder> selectBaoliBizOrderList(BaoliBizOrder baoliBizOrder)
    {
        return baoliBizOrderMapper.selectBaoliBizOrderList(baoliBizOrder);
    }

    /**
     * 新增订单
     * 
     * @param baoliBizOrder 订单
     * @return 结果
     */
    @Override
    public int insertBaoliBizOrder(BaoliBizOrder baoliBizOrder)
    {
        baoliBizOrder.setCreateTime(DateUtils.getNowDate());
        return baoliBizOrderMapper.insertBaoliBizOrder(baoliBizOrder);
    }

    /**
     * 修改订单
     * 
     * @param baoliBizOrder 订单
     * @return 结果
     */
    @Override
    public int updateBaoliBizOrder(BaoliBizOrder baoliBizOrder)
    {
        baoliBizOrder.setUpdateTime(DateUtils.getNowDate());
        return baoliBizOrderMapper.updateBaoliBizOrder(baoliBizOrder);
    }

    /**
     * 批量删除订单
     * 
     * @param ids 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizOrderByIds(Long[] ids)
    {
        return baoliBizOrderMapper.deleteBaoliBizOrderByIds(ids);
    }

    /**
     * 删除订单信息
     * 
     * @param id 订单主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizOrderById(Long id)
    {
        return baoliBizOrderMapper.deleteBaoliBizOrderById(id);
    }
}
