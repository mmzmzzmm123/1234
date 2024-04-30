package com.baoli.order.service.impl;

import java.util.List;
import java.util.Map;

import com.baoli.order.domain.BaoliBizRefuseOrder;
import com.baoli.order.mapper.BaoliBizRefuseOrderMapper;
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

    @Autowired
    private BaoliBizRefuseOrderMapper baoliBizRefuseOrderMapper;
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

    @Override
    public List<BaoliBizOrder> selectMyOrderList(BaoliBizOrder baoliBizOrder) {
        return baoliBizOrderMapper.selectMyOrderList(baoliBizOrder);
    }

    @Override
    public List<Map<String, Object>> selectMyOrder(BaoliBizOrder baoliBizOrder) {
        return baoliBizOrderMapper.selectMyOrder(baoliBizOrder);
    }

    @Override
    public List<Map<String, Object>> selectMyOrderCount(BaoliBizOrder baoliBizOrder) {
        return baoliBizOrderMapper.selectMyOrderCount(baoliBizOrder);
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

    @Override
    public int transRefuseOrder(BaoliBizOrder  order) {
        BaoliBizOrder baoliBizOrder = baoliBizOrderMapper.selectBaoliBizOrderById(order.getId());
        //创建拒单
        BaoliBizRefuseOrder refuseOrder = new BaoliBizRefuseOrder();
        refuseOrder.setCity(baoliBizOrder.getCity());
        refuseOrder.setStoreId(baoliBizOrder.getStoreId());
        refuseOrder.setCarType(baoliBizOrder.getCarType());
        refuseOrder.setLevel1BrandId(baoliBizOrder.getLevel1BrandId());
        refuseOrder.setLevel2BrandId(baoliBizOrder.getLevel2BrandId());
        refuseOrder.setCarSeriesId(baoliBizOrder.getCarSeriesId());
        refuseOrder.setCarModelId(baoliBizOrder.getCarModelId());
        refuseOrder.setLoanType(baoliBizOrder.getLoanType());
        refuseOrder.setOrderNumber(baoliBizOrder.getOrderNumber());
        refuseOrder.setBank(baoliBizOrder.getBank());
        refuseOrder.setCustomerName(baoliBizOrder.getCustomerName());
        refuseOrder.setApplyNumber(baoliBizOrder.getApplyNumber());
        refuseOrder.setLoanAmount(baoliBizOrder.getLoanAmount());
        refuseOrder.setInStore(baoliBizOrder.getApplicantId());
        refuseOrder.setRefuseReason(order.getRefuseReason());
        refuseOrder.setRemark(order.getRemark());
        refuseOrder.setStatus(baoliBizOrder.getStatus());
        refuseOrder.setCreateTime(DateUtils.getNowDate());
        refuseOrder.setApplicantId(baoliBizOrder.getApplicantId());
        refuseOrder.setProvinceId(baoliBizOrder.getProvinceId());
        baoliBizRefuseOrderMapper.insertBaoliBizRefuseOrder(refuseOrder);
        //修改订单(id,refuseOrderId)
        baoliBizOrder.setRefuseOrderId(refuseOrder.getId());
        return baoliBizOrderMapper.updateBaoliBizOrder(baoliBizOrder);
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

    @Override
    public int batchUpdateOrder(Map<String, Object> baoliBizOrder) {
        return baoliBizOrderMapper.batchUpdateOrder(baoliBizOrder);
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
