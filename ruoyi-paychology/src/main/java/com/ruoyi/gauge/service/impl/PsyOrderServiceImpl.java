package com.ruoyi.gauge.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.gauge.constant.GaugeConstant;
import org.springframework.stereotype.Service;
import com.ruoyi.gauge.mapper.PsyOrderMapper;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.service.IPsyOrderService;

import javax.annotation.Resource;

/**
 * 心理测评订单信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-12
 */
@Service
public class PsyOrderServiceImpl implements IPsyOrderService {
    @Resource
    private PsyOrderMapper psyOrderMapper;

    /**
     * 查询心理测评订单信息
     *
     * @param id 心理测评订单信息主键
     * @return 心理测评订单信息
     */
    @Override
    public PsyOrder selectPsyOrderById(Long id) {
        return psyOrderMapper.selectPsyOrderById(id);
    }

    /**
     * 查询心理测评订单信息列表
     *
     * @param psyOrder 心理测评订单信息
     * @return 心理测评订单信息
     */
    @Override
    public List<PsyOrder> selectPsyOrderList(PsyOrder psyOrder) {
        return psyOrderMapper.selectPsyOrderList(psyOrder);
    }

    /**
     * 新增心理测评订单信息
     *
     * @param psyOrder 心理测评订单信息
     * @return 结果
     */
    @Override
    public int insertPsyOrder(PsyOrder psyOrder) {
        psyOrder.setCreateTime(DateUtils.getNowDate());
        return psyOrderMapper.insertPsyOrder(psyOrder);
    }

    /**
     * 修改心理测评订单信息
     *
     * @param psyOrder 心理测评订单信息
     * @return 结果
     */
    @Override
    public int updatePsyOrder(PsyOrder psyOrder) {
        return psyOrderMapper.updatePsyOrder(psyOrder);
    }
    /**
     * 修改心理测评订单信息  by  orderId
     *
     * @param psyOrder 心理测评订单信息
     * @return 结果
     */
    @Override
    public int updatePsyOrderByOrderId(PsyOrder psyOrder){
        return psyOrderMapper.updatePsyOrderByOrder(psyOrder);
    }
    /**
     * 批量删除心理测评订单信息
     *
     * @param ids 需要删除的心理测评订单信息主键
     * @return 结果
     */
    @Override
    public int deletePsyOrderByIds(Long[] ids) {
        return psyOrderMapper.deletePsyOrderByIds(ids);
    }

    /**
     * 删除心理测评订单信息信息
     *
     * @param id 心理测评订单信息主键
     * @return 结果
     */
    @Override
    public int deletePsyOrderById(Long id) {
        return psyOrderMapper.deletePsyOrderById(id);
    }

    @Override
    public List<PsyOrder> queryOrderInfo(PsyOrder psyOrder ,Integer userId) {
        if (userId == null) {
            psyOrder.setCreateBy(null);
        } else {
            psyOrder.setCreateBy(userId + "");
        }
        return psyOrderMapper.queryOrderPage(psyOrder);
    }

    @Override
    public int getMyReportNum(LoginDTO loginUser) {
        return psyOrderMapper.getMyReportNum(loginUser);
    }

    /**
     * 查询测评订单
     *
     * @param orderId 测评订单主键
     * @return 课程订单
     */
    @Override
    public PsyOrder selectPsyOrderByOrderId(String orderId)
    {
        return psyOrderMapper.selectPsyOrderByOrderId(orderId);
    }

    /**
     * 生成测评订单
     *
     * @param psyOrder 测评订单
     * @return 生成的订单对象
     */
    @Override
    public PsyOrder generatePsyOrder(PsyOrder psyOrder) {
        psyOrder.setCreateTime(DateUtils.getNowDate());
        int code = psyOrderMapper.insertPsyOrder(psyOrder);
        if (code == 1) {
            return selectPsyOrderByOrderId(psyOrder.getOrderId());
        }
        return null;
    }

    /**
     * 根据测评ID查询测评是否已经购买
     *
     * @param userId 用户ID
     * @param gaugeId 测评ID
     * @return 测评是否已经购买
     */
    @Override
    public Integer getGaugeIsBuy(String userId, Long gaugeId) {
        PsyOrder psyOrder = new PsyOrder();
        psyOrder.setCreateBy(userId);
        psyOrder.setGaugeId(gaugeId);
        psyOrder.setOrderStatus(GaugeConstant.GAUGE_ORDER_STATUE_FINISHED);
        List<PsyOrder> psyOrderList = psyOrderMapper.selectPsyOrderList(psyOrder);
        if (psyOrderList.size() == 1) {
            return GaugeConstant.GAUGE_IS_BUY;
        }
        return GaugeConstant.GAUGE_NOT_BUY;
    }

    /**
     * 根据用户ID和测评ID查询测评订单信息
     *
     * @param userId 用户ID
     * @param gaugeId 测评ID
     * @return 订单信息
     */
    @Override
    public PsyOrder getPsyOrder(String userId, Long gaugeId) {
        PsyOrder psyOrder = new PsyOrder();
        psyOrder.setCreateBy(userId);
        psyOrder.setGaugeId(gaugeId);
        List<PsyOrder> psyOrderList = psyOrderMapper.selectPsyOrderList(psyOrder);
        if (psyOrderList.size() == 1) {
            return psyOrderList.get(0);
        }
        return null;
    }
}
