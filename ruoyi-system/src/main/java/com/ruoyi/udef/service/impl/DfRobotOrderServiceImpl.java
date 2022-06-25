package com.ruoyi.udef.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.udef.mapper.DfRobotOrderMapper;
import com.ruoyi.udef.domain.DfRobotOrder;
import com.ruoyi.udef.service.IDfRobotOrderService;

/**
 * 机器人订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
@Service
public class DfRobotOrderServiceImpl implements IDfRobotOrderService 
{
    @Autowired
    private DfRobotOrderMapper dfRobotOrderMapper;

    /**
     * 查询机器人订单
     * 
     * @param id 机器人订单主键
     * @return 机器人订单
     */
    @Override
    public DfRobotOrder selectDfRobotOrderById(Long id)
    {
        return dfRobotOrderMapper.selectDfRobotOrderById(id);
    }

    /**
     * 查询机器人订单列表
     * 
     * @param dfRobotOrder 机器人订单
     * @return 机器人订单
     */
    @Override
    public List<DfRobotOrder> selectDfRobotOrderList(DfRobotOrder dfRobotOrder)
    {
        return dfRobotOrderMapper.selectDfRobotOrderList(dfRobotOrder);
    }

    /**
     * 新增机器人订单
     * 
     * @param dfRobotOrder 机器人订单
     * @return 结果
     */
    @Override
    public int insertDfRobotOrder(DfRobotOrder dfRobotOrder)
    {
        return dfRobotOrderMapper.insertDfRobotOrder(dfRobotOrder);
    }

    /**
     * 修改机器人订单
     * 
     * @param dfRobotOrder 机器人订单
     * @return 结果
     */
    @Override
    public int updateDfRobotOrder(DfRobotOrder dfRobotOrder)
    {
        return dfRobotOrderMapper.updateDfRobotOrder(dfRobotOrder);
    }

    /**
     * 批量删除机器人订单
     * 
     * @param ids 需要删除的机器人订单主键
     * @return 结果
     */
    @Override
    public int deleteDfRobotOrderByIds(Long[] ids)
    {
        return dfRobotOrderMapper.deleteDfRobotOrderByIds(ids);
    }

    /**
     * 删除机器人订单信息
     * 
     * @param id 机器人订单主键
     * @return 结果
     */
    @Override
    public int deleteDfRobotOrderById(Long id)
    {
        return dfRobotOrderMapper.deleteDfRobotOrderById(id);
    }
}
