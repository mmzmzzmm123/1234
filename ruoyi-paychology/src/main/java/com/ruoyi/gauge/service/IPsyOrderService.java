package com.ruoyi.gauge.service;

import java.util.List;

import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.gauge.domain.PsyOrder;

/**
 * 心理测评订单信息Service接口
 * 
 * @author ruoyi
 * @date 2022-10-12
 */
public interface IPsyOrderService 
{
    /**
     * 查询心理测评订单信息
     * 
     * @param id 心理测评订单信息主键
     * @return 心理测评订单信息
     */
    public PsyOrder selectPsyOrderById(Long id);

    /**
     * 查询心理测评订单信息列表
     * 
     * @param psyOrder 心理测评订单信息
     * @return 心理测评订单信息集合
     */
    public List<PsyOrder> selectPsyOrderList(PsyOrder psyOrder);

    /**
     * 新增心理测评订单信息
     * 
     * @param psyOrder 心理测评订单信息
     * @return 结果
     */
    public int insertPsyOrder(PsyOrder psyOrder);

    /**
     * 修改心理测评订单信息
     * 
     * @param psyOrder 心理测评订单信息
     * @return 结果
     */
    public int updatePsyOrder(PsyOrder psyOrder);

    /**
     * 修改心理测评订单信息  by  orderId
     *
     * @param psyOrder 心理测评订单信息
     * @return 结果
     */
    public int updatePsyOrderByOrderId(PsyOrder psyOrder);
    /**
     * 批量删除心理测评订单信息
     * 
     * @param ids 需要删除的心理测评订单信息主键集合
     * @return 结果
     */
    public int deletePsyOrderByIds(Long[] ids);

    /**
     * 删除心理测评订单信息信息
     * 
     * @param id 心理测评订单信息主键
     * @return 结果
     */
    public int deletePsyOrderById(Long id);

    List<PsyOrder> queryOrderInfo(PsyOrder psyOrder ,LoginDTO loginUser);

    int getMyReportNum(LoginDTO loginUser);
}
