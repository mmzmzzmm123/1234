package com.baoli.order.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.order.mapper.BaoliBizOrderLabelMapper;
import com.baoli.order.domain.BaoliBizOrderLabel;
import com.baoli.order.service.IBaoliBizOrderLabelService;

/**
 * 订单标签管理Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-24
 */
@Service
public class BaoliBizOrderLabelServiceImpl implements IBaoliBizOrderLabelService 
{
    @Autowired
    private BaoliBizOrderLabelMapper baoliBizOrderLabelMapper;

    /**
     * 查询订单标签管理
     * 
     * @param id 订单标签管理主键
     * @return 订单标签管理
     */
    @Override
    public BaoliBizOrderLabel selectBaoliBizOrderLabelById(Long id)
    {
        return baoliBizOrderLabelMapper.selectBaoliBizOrderLabelById(id);
    }

    /**
     * 查询订单标签管理列表
     * 
     * @param baoliBizOrderLabel 订单标签管理
     * @return 订单标签管理
     */
    @Override
    public List<BaoliBizOrderLabel> selectBaoliBizOrderLabelList(BaoliBizOrderLabel baoliBizOrderLabel)
    {
        return baoliBizOrderLabelMapper.selectBaoliBizOrderLabelList(baoliBizOrderLabel);
    }

    /**
     * 新增订单标签管理
     * 
     * @param baoliBizOrderLabel 订单标签管理
     * @return 结果
     */
    @Override
    public int insertBaoliBizOrderLabel(BaoliBizOrderLabel baoliBizOrderLabel)
    {
        return baoliBizOrderLabelMapper.insertBaoliBizOrderLabel(baoliBizOrderLabel);
    }

    /**
     * 修改订单标签管理
     * 
     * @param baoliBizOrderLabel 订单标签管理
     * @return 结果
     */
    @Override
    public int updateBaoliBizOrderLabel(BaoliBizOrderLabel baoliBizOrderLabel)
    {
        return baoliBizOrderLabelMapper.updateBaoliBizOrderLabel(baoliBizOrderLabel);
    }

    /**
     * 批量删除订单标签管理
     * 
     * @param ids 需要删除的订单标签管理主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizOrderLabelByIds(Long[] ids)
    {
        return baoliBizOrderLabelMapper.deleteBaoliBizOrderLabelByIds(ids);
    }

    /**
     * 删除订单标签管理信息
     * 
     * @param id 订单标签管理主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizOrderLabelById(Long id)
    {
        return baoliBizOrderLabelMapper.deleteBaoliBizOrderLabelById(id);
    }
}
