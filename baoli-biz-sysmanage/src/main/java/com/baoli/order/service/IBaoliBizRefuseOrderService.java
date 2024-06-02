package com.baoli.order.service;

import java.util.List;
import com.baoli.order.domain.BaoliBizRefuseOrder;

/**
 * 拒单订单管理Service接口
 * 
 * @author niujs
 * @date 2024-04-11
 */
public interface IBaoliBizRefuseOrderService 
{
    /**
     * 查询拒单订单管理
     * 
     * @param id 拒单订单管理主键
     * @return 拒单订单管理
     */
    public BaoliBizRefuseOrder selectBaoliBizRefuseOrderById(Long id);

    /**
     * 查询拒单订单管理列表
     * 
     * @param baoliBizRefuseOrder 拒单订单管理
     * @return 拒单订单管理集合
     */
    public List<BaoliBizRefuseOrder> selectBaoliBizRefuseOrderList(BaoliBizRefuseOrder baoliBizRefuseOrder);

    /**
     * 新增拒单订单管理
     * 
     * @param baoliBizRefuseOrder 拒单订单管理
     * @return 结果
     */
    public int insertBaoliBizRefuseOrder(BaoliBizRefuseOrder baoliBizRefuseOrder);

    /**
     * 修改拒单订单管理
     * 
     * @param baoliBizRefuseOrder 拒单订单管理
     * @return 结果
     */
    public int updateBaoliBizRefuseOrder(BaoliBizRefuseOrder baoliBizRefuseOrder);

    /**
     * 批量删除拒单订单管理
     * 
     * @param ids 需要删除的拒单订单管理主键集合
     * @return 结果
     */
    public int deleteBaoliBizRefuseOrderByIds(Long[] ids);

    /**
     * 删除拒单订单管理信息
     * 
     * @param id 拒单订单管理主键
     * @return 结果
     */
    public int deleteBaoliBizRefuseOrderById(Long id);
}
