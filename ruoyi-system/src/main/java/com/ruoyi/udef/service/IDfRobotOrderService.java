package com.ruoyi.udef.service;

import java.util.List;
import com.ruoyi.udef.domain.DfRobotOrder;

/**
 * 机器人订单Service接口
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
public interface IDfRobotOrderService 
{
    /**
     * 查询机器人订单
     * 
     * @param id 机器人订单主键
     * @return 机器人订单
     */
    public DfRobotOrder selectDfRobotOrderById(Long id);

    /**
     * 查询机器人订单列表
     * 
     * @param dfRobotOrder 机器人订单
     * @return 机器人订单集合
     */
    public List<DfRobotOrder> selectDfRobotOrderList(DfRobotOrder dfRobotOrder);

    /**
     * 新增机器人订单
     * 
     * @param dfRobotOrder 机器人订单
     * @return 结果
     */
    public int insertDfRobotOrder(DfRobotOrder dfRobotOrder);

    /**
     * 修改机器人订单
     * 
     * @param dfRobotOrder 机器人订单
     * @return 结果
     */
    public int updateDfRobotOrder(DfRobotOrder dfRobotOrder);

    /**
     * 批量删除机器人订单
     * 
     * @param ids 需要删除的机器人订单主键集合
     * @return 结果
     */
    public int deleteDfRobotOrderByIds(Long[] ids);

    /**
     * 删除机器人订单信息
     * 
     * @param id 机器人订单主键
     * @return 结果
     */
    public int deleteDfRobotOrderById(Long id);
}
