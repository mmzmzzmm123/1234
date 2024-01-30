package com.ruoyi.system.components;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.order.Order;
import com.ruoyi.common.core.domain.entity.order.OrderRefund;
import com.ruoyi.common.core.domain.entity.order.OrderSku;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.TaskQuery.TaskAdapter;
import com.ruoyi.system.domain.dto.AmountConsumptionDTO;
import com.ruoyi.system.domain.dto.ApplyAmountFrozenDTO;
import com.ruoyi.system.extend.UtTouchJoinRoomClient;
import com.ruoyi.system.extend.UtTouchResult;
import com.ruoyi.system.extend.data.GetChatRoomJoinTaskDetailInfoListInput;
import com.ruoyi.system.extend.data.GetChatRoomJoinTaskDetailInfoListOutput;
import com.ruoyi.system.extend.data.GetChatRoomJoinTaskDetailInput;
import com.ruoyi.system.extend.data.GetChatRoomJoinTaskDetailOutput;
import com.ruoyi.system.extend.data.GetChatRoomJoinTaskPageInput;
import com.ruoyi.system.extend.data.UtTouchPage;
import com.ruoyi.system.mapper.OrderMapper;
import com.ruoyi.system.mapper.OrderRefundMapper;
import com.ruoyi.system.mapper.OrderSkuMapper;
import com.ruoyi.system.service.MerchantAmountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderTools {

	public static int getSuccessCountOfTaskDetail(Order order) {
		// 获取 任务 详情成功的 个数
		TaskQuery taskQuery = TaskQuery.newQuery(order.getOrderType());
		List<TaskAdapter> taskAdapters = taskQuery.listByOrder(Arrays.asList(order.getOrderId()));
		if (CollectionUtils.isEmpty(taskAdapters)) {
			return 0;
		}
		return taskQuery.getSuccessCountOfTaskDetail(taskAdapters.get(0).getTaskId());
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
			throw new ServiceException(e.getMessage());
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
		return mapper.selectList(new QueryWrapper<Order>().lambda().eq(Order::getOrderStatus, 2).isNotNull(Order::getFinishTime));
	}

	public static void handleOrderStatus(Order order) {
		final OrderMapper mapper = SpringUtils.getBean(OrderMapper.class);
		List<TaskAdapter> taskAdapters = TaskQuery.newQuery(order.getOrderType()).listByOrder(Arrays.asList(order.getOrderId()));
		if (CollectionUtils.isEmpty(taskAdapters)) {
			return;
		}
		String taskId = taskAdapters.get(0).getTaskId();
		// 查询任务的状态 -1待执行 0-进行中 1-已完成 2-已取消
		int taskStatus = TaskQuery.newQuery(order.getOrderType()).getStatus(taskId);
		if (taskStatus == 0) {
			// 如果任务是 进行中， 设置订单为 进行中
			mapper.updateStatus(order.getOrderId(), 1);
			log.info("OrderMapper.updateStatus {} {}", order, 1);
		}
		if (taskStatus == 1) {
			// 任务是已经完成 , 修改订单完成
			mapper.updateFinish(order.getOrderId());
			log.info("OrderMapper.updateFinish {}", order);
			// 订单完成 这里不退计算 退款， 因为看可能回调有延迟，需要延迟计算 退款
		}
		if (taskStatus == 2) {
			// 任务取消了 , 结算
			settlementUserMonery(order, "任务主动取消");
			mapper.updateCancel(order.getOrderId(), "任务主动取消");
		}
	}

	public static void taskGenerationFailed(String orderId) {
		if(StrUtil.isEmpty(orderId)){
			return;
		}
		Order order = SpringUtils.getBean(OrderMapper.class).selectById(orderId);
		if(order==null){
			return;
		}

		settlementUserMonery(order, "任务生成失败");
		SpringUtils.getBean(OrderMapper.class).updateCancel(order.getOrderId(), "任务生成失败");

	}



	public static long skuPrice(String orderId) {
		final OrderSkuMapper mapper = SpringUtils.getBean(OrderSkuMapper.class);
		OrderSku sku = mapper
				.selectOne(new QueryWrapper<OrderSku>().lambda().eq(OrderSku::getOrderId, orderId).last(" limit  1 "));
		if (sku == null) {
			return 0;
		}
		return sku.getPrice();
	}

	/***
	 * 结算用户订单
	 * 
	 * @param orders
	 */
	public static void settlementUserMonery(Order order, String remark) {
		// 根据订单 查询 实际完成了 多少任务详情
		int successCount = getSuccessCountOfTaskDetail(order);
		// sku单价
		long skuPrice = skuPrice(order.getOrderId());
		// 实际扣除的钱 = 成功详情数 * sku单价
		long payPrice = successCount * skuPrice;
		final MerchantAmountService service = SpringUtils.getBean(MerchantAmountService.class);
		AmountConsumptionDTO dto = new AmountConsumptionDTO();
		dto.setAmount(payPrice);
		dto.setFrozenId(order.getFrozenId());
		// 实际退款
		long actualRefundPrice = 0;
		try {
			actualRefundPrice = service.amountConsumption(dto);
			log.info("用户结算 {} {} {}", dto, actualRefundPrice, order);
		} catch (Exception e) {
			log.error("MerchantAmountService.amountConsumption {}", dto, e);
		}
		// 插入退款
		OrderRefund orderRefund = new OrderRefund();
		orderRefund.setActualRefundPrice(actualRefundPrice);
		orderRefund.setCalculateRefundPrice(order.getPrice() - payPrice);
		orderRefund.setCreateTime(new Date());
		orderRefund.setMerchantId(order.getMerchantId());
		orderRefund.setOrderId(order.getOrderId());
		orderRefund.setUserId(order.getUserId());
		orderRefund.setRemark(remark);
		try {
			orderRefund.setTaskId(TaskQuery.newQuery(order.getOrderType()).listByOrder(Arrays.asList(order.getOrderId())).get(0).getTaskId());
		} catch (Exception e) {
		}
		SpringUtils.getBean(OrderRefundMapper.class).insert(orderRefund);
		log.info("用户结算完成 {} {}", order, orderRefund);
	}

}
