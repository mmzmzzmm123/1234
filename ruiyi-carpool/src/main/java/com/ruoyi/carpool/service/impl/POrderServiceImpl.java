package com.ruoyi.carpool.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.carpool.mapper.POrderMapper;
import com.ruoyi.carpool.domain.POrder;
import com.ruoyi.carpool.service.IPOrderService;

/**
 * 订单信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
@Service
public class POrderServiceImpl implements IPOrderService
{
    @Autowired
    private POrderMapper pOrderMapper;

    /**
     * 查询订单信息
     * 
     * @param id 订单信息主键
     * @return 订单信息
     */
    @Override
    public POrder selectPOrderById(Long id)
    {
        return pOrderMapper.selectPOrderById(id);
    }

    /**
     * 查询订单信息列表
     * 
     * @param pOrder 订单信息
     * @return 订单信息
     */
    @Override
    public List<POrder> selectPOrderList(POrder pOrder)
    {
        return pOrderMapper.selectPOrderList(pOrder);
    }

    /**
     * 新增订单信息
     * 
     * @param pOrder 订单信息
     * @return 结果
     */
    @Override
    public int insertPOrder(POrder pOrder)
    {
        String orderNum = DateUtils.dateTimeNow();
        pOrder.setOrderNum("carpool_"+orderNum);
        pOrder.setIsTake("0");
        pOrder.setState("0");
        pOrder.setMember(0);
        pOrder.setCreateTime(DateUtils.getNowDate());
        return pOrderMapper.insertPOrder(pOrder);
    }

    /**
     * 修改订单信息
     * 
     * @param pOrder 订单信息
     * @return 结果
     */
    @Override
    public int updatePOrder(POrder pOrder)
    {
        pOrder.setUpdateTime(DateUtils.getNowDate());
        return pOrderMapper.updatePOrder(pOrder);
    }

    /**
     * 批量删除订单信息
     * 
     * @param ids 需要删除的订单信息主键
     * @return 结果
     */
    @Override
    public int deletePOrderByIds(Long[] ids)
    {
        return pOrderMapper.deletePOrderByIds(ids);
    }

    /**
     * 删除订单信息信息
     * 
     * @param id 订单信息主键
     * @return 结果
     */
    @Override
    public int deletePOrderById(Long id)
    {
        return pOrderMapper.deletePOrderById(id);
    }

    /**
     * 删除订单信息信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    @Override
    public int takeOrder(POrder pOrder)
    {
        pOrder.setIsTake("1");
        return pOrderMapper.takeOrderById(pOrder);
    }


}
