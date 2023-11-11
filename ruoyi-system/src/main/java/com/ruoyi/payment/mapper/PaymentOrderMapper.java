package com.ruoyi.payment.mapper;

import java.util.List;
import com.ruoyi.payment.domain.PaymentOrder;

/**
 * 支付订单Mapper接口
 *
 * @author Lam
 * @date 2023-09-15
 */
public interface PaymentOrderMapper {

    /**
     * 查询支付订单
     *
     * @param id 支付订单主键
     * @return 支付订单
     */
    public PaymentOrder selectPaymentOrderById(Long id);

    /**
     * 根据商户订单号查询记录
     *
     * @param outTradeNo 商户单号
     * @return 结果
     * */
    public PaymentOrder selectByOutTradeNo(String outTradeNo);

    /**
     * 查询支付订单列表
     *
     * @param paymentOrder 支付订单
     * @return 支付订单集合
     */
    public List<PaymentOrder> selectPaymentOrderList(PaymentOrder paymentOrder);

    /**
     * 查询body信息
     *
     * @return 结果
     * */
    public List<String> selectBody();

    /**
     * 新增支付订单
     *
     * @param paymentOrder 支付订单
     * @return 结果
     */
    public int insertPaymentOrder(PaymentOrder paymentOrder);

    /**
     * 修改支付订单
     *
     * @param paymentOrder 支付订单
     * @return 结果
     */
    public int updatePaymentOrder(PaymentOrder paymentOrder);

    /**
     * 根据订单标识修改记录数据
     *
     * @param paymentOrder 数据
     * @return 结果
     * */
    public int updateByOrderId(PaymentOrder paymentOrder);

    /**
     * 删除支付订单
     *
     * @param id 支付订单主键
     * @return 结果
     */
    public int deletePaymentOrderById(Long id);

    /**
     * 批量删除支付订单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePaymentOrderByIds(Long[] ids);
}
