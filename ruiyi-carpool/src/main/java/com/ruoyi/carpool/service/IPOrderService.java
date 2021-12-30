package com.ruoyi.carpool.service;

import java.util.List;
import com.ruoyi.carpool.domain.POrder;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 订单信息Service接口
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
public interface IPOrderService 
{
    /**
     * 查询订单信息
     * 
     * @param id 订单信息主键
     * @return 订单信息
     */
    public POrder selectPOrderById(Long id);

    /**
     * 查询订单信息列表
     * 
     * @param pOrder 订单信息
     * @return 订单信息集合
     */
    public List<POrder> selectPOrderList(POrder pOrder);


    /**
     * 通过openID查询订单信息列表
     * @param openID
     * @return
     */
    public List<POrder> queryOrderInfoByOpenId(String openID);

    /**
     * 新增订单信息
     * 
     * @param pOrder 订单信息
     * @return 结果
     */
    public int insertPOrder(POrder pOrder);

    /**
     * 新增订单信息
     *
     * @param pOrder 订单信息
     * @return 结果
     */
    public AjaxResult insertPOrderWx(POrder pOrder);

    /**
     * 修改订单信息
     * 
     * @param pOrder 订单信息
     * @return 结果
     */
    public int updatePOrder(POrder pOrder);

    /**
     * 批量删除订单信息
     * 
     * @param ids 需要删除的订单信息主键集合
     * @return 结果
     */
    public int deletePOrderByIds(Long[] ids);

    /**
     * 删除订单信息信息
     * 
     * @param id 订单信息主键
     * @return 结果
     */
    public int deletePOrderById(Long id);

    /**
     * 司机接单
     *
     * @param pOrder 订单信息
     * @return 结果
     */
    public AjaxResult takeOrder(POrder pOrder);
}
