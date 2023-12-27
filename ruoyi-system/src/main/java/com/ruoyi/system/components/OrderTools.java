package com.ruoyi.system.components;

import java.util.Arrays;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.order.Order;
import com.ruoyi.common.core.domain.entity.order.OrderRefund;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.dto.AmountConsumptionDTO;
import com.ruoyi.system.domain.dto.ApplyAmountFrozenDTO;
import com.ruoyi.system.mapper.OrderMapper;
import com.ruoyi.system.mapper.OrderRefundMapper;
import com.ruoyi.system.service.MerchantAmountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderTools {

	public static void listTask(List<String> orderIds) {

	}

	public static int getSuccessCountOfTaskDetail(String orderId) {
		// 获取 任务 详情成功的 个数
		return 0;
	}

	public static String orderId() {
		return Ids.getId().toUpperCase();
	}

	public static List<OrderRefund> listRefund(List<String> orderIds) {
		final OrderRefundMapper mapper = SpringUtils.getBean(OrderRefundMapper.class);
		return mapper.selectList(new QueryWrapper<OrderRefund>().lambda().in(OrderRefund::getOrderId, orderIds));
	}

	/***
	 * 冻结客户余额
	 * 
	 * @param price
	 * @param merchantId
	 * @param orderId
	 * @return
	 */
	public static String doFrozen(long price, String merchantId, String orderId) {
		final MerchantAmountService service = SpringUtils.getBean(MerchantAmountService.class);
		ApplyAmountFrozenDTO dto = new ApplyAmountFrozenDTO();
		dto.setAmount(price);
		dto.setMerchantId(merchantId);
		dto.setOrderId(orderId);
		try {
			String frozenId = service.applyAmountFrozen(dto);
			log.info("MerchantAmountService.applyAmountFrozen {} {}", dto, frozenId);
			return frozenId;
		} catch (Throwable e) {
			String trace = Ids.getId();
			log.error("MerchantAmountService.applyAmountFrozen_error {} {}", trace, dto, e);
			throw new ServiceException("冻结用户资金失败 trace: " + trace);
		}
	}

	public static List<Order> listIncomplete() {
		final OrderMapper mapper = SpringUtils.getBean(OrderMapper.class);
		// 订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款
		return mapper.selectList(new QueryWrapper<Order>().lambda().in(Order::getOrderStatus, Arrays.asList(0, 1)));
	}

	public static List<Order> listComplete() {
		final OrderMapper mapper = SpringUtils.getBean(OrderMapper.class);
		// 订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款
		return mapper.selectList(new QueryWrapper<Order>().lambda().eq(Order::getOrderStatus, 2));
	}

	public static void handleOrderStatus(Order order) {
		final OrderMapper mapper = SpringUtils.getBean(OrderMapper.class);
		// 订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款

		// 查询任务的状态

		// 如果任务是 进行中， 设置订单为 进行中
		mapper.updateStatus(order.getOrderId(), 1);
		log.info("OrderMapper.updateStatus {} {}", order, 1);

		// 任务是已经完成 , 修改订单完成
		mapper.updateFinish(order.getOrderId());
		log.info("OrderMapper.updateFinish {}", order);

		// 订单完成 这里不退计算 退款， 因为看可能回调有延迟，需要延迟计算 退款
	}

	/***
	 * 结算用户订单
	 * 
	 * @param orders
	 */
	public static void settlementUserMonery(Order order) {
		// 根据订单 查询 实际完成了 多少任务详情
		int successCount = getSuccessCountOfTaskDetail(order.getOrderId());
		// sku单价
		long skuPrice = ProductTools.skuPrice(order.getProductId());
		// 实际扣除的 钱 = 成功详情数 * sku单价
		long payPrice = successCount * skuPrice;
		final MerchantAmountService service = SpringUtils.getBean(MerchantAmountService.class);
		AmountConsumptionDTO dto = new AmountConsumptionDTO();
		dto.setAmount(payPrice);
		dto.setFrozenId(order.getFrozenId());
		service.amountConsumption(dto);
		log.info("用户结算 {}", dto);
	}

}
