package com.baoli.order.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.order.mapper.BaoliBizRefuseOrderMapper;
import com.baoli.order.domain.BaoliBizRefuseOrder;
import com.baoli.order.service.IBaoliBizRefuseOrderService;

/**
 * 拒单订单管理Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-11
 */
@Service
public class BaoliBizRefuseOrderServiceImpl implements IBaoliBizRefuseOrderService 
{
    @Autowired
    private BaoliBizRefuseOrderMapper baoliBizRefuseOrderMapper;

    /**
     * 查询拒单订单管理
     * 
     * @param id 拒单订单管理主键
     * @return 拒单订单管理
     */
    @Override
    public BaoliBizRefuseOrder selectBaoliBizRefuseOrderById(Long id)
    {
        return baoliBizRefuseOrderMapper.selectBaoliBizRefuseOrderById(id);
    }

    /**
     * 查询拒单订单管理列表
     * 
     * @param baoliBizRefuseOrder 拒单订单管理
     * @return 拒单订单管理
     */
    @Override
    public List<BaoliBizRefuseOrder> selectBaoliBizRefuseOrderList(BaoliBizRefuseOrder baoliBizRefuseOrder)
    {
        return baoliBizRefuseOrderMapper.selectBaoliBizRefuseOrderList(baoliBizRefuseOrder);
    }

    /**
     * 新增拒单订单管理
     * 
     * @param baoliBizRefuseOrder 拒单订单管理
     * @return 结果
     */
    @Override
    public int insertBaoliBizRefuseOrder(BaoliBizRefuseOrder baoliBizRefuseOrder)
    {
        baoliBizRefuseOrder.setCreateTime(DateUtils.getNowDate());
        return baoliBizRefuseOrderMapper.insertBaoliBizRefuseOrder(baoliBizRefuseOrder);
    }

    /**
     * 修改拒单订单管理
     * 
     * @param baoliBizRefuseOrder 拒单订单管理
     * @return 结果
     */
    @Override
    public int updateBaoliBizRefuseOrder(BaoliBizRefuseOrder baoliBizRefuseOrder)
    {
        baoliBizRefuseOrder.setUpdateTime(DateUtils.getNowDate());
        return baoliBizRefuseOrderMapper.updateBaoliBizRefuseOrder(baoliBizRefuseOrder);
    }

    /**
     * 批量删除拒单订单管理
     * 
     * @param ids 需要删除的拒单订单管理主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizRefuseOrderByIds(Long[] ids)
    {
        return baoliBizRefuseOrderMapper.deleteBaoliBizRefuseOrderByIds(ids);
    }

    /**
     * 删除拒单订单管理信息
     * 
     * @param id 拒单订单管理主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizRefuseOrderById(Long id)
    {
        return baoliBizRefuseOrderMapper.deleteBaoliBizRefuseOrderById(id);
    }
}
