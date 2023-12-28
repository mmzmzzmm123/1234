package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.app.CancelOrderRequest;
import com.ruoyi.common.core.domain.app.OrderDetailResponse;
import com.ruoyi.common.core.domain.app.OrderListResponse;
import com.ruoyi.common.core.domain.app.OrderProduceRequest;
import com.ruoyi.common.core.domain.app.OrderRequest;
import com.ruoyi.common.core.domain.app.SubmitResponse;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.common.core.domain.entity.order.Order;
import com.ruoyi.common.core.domain.entity.order.OrderRefund;
import com.ruoyi.common.core.domain.entity.order.OrderSku;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.Objects;
import com.ruoyi.system.components.OrderPriceHandler;
import com.ruoyi.system.components.OrderTools;
import com.ruoyi.system.components.ProductTools;
import com.ruoyi.system.components.UserTools;
import com.ruoyi.system.configure.RedisConfigure;
import com.ruoyi.system.mapper.OrderMapper;
import com.ruoyi.system.mapper.OrderRefundMapper;
import com.ruoyi.system.service.OrderService;
import com.ruoyi.system.service.OrderSkuService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService, InitializingBean {

	private final OrderPriceHandler orderPriceHandler = new OrderPriceHandler.MultiAssemblyOrderPriceHandler();

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	OrderSkuService orderSkuService;

	@Autowired
	OrderRefundMapper orderRefundMapper;

	@Autowired
	RedisLock redisLock;

	@Autowired
	RedisCache redisCache;

	@Autowired
	RedisConfigure redisConfigure;

	@Override
	@SuppressWarnings("unchecked")
	public AjaxResult produce(OrderProduceRequest request) {
		log.info("OrderServiceImpl.produce {}", request);
		if (request.getParams() == null || CollectionUtils.isEmpty(request.getParams().getGroupIds())) {
			return AjaxResult.error(ErrInfoConfig.getDynmic(11000, "params"));
		}
		// 校验商家
		if (UserTools.checkMerchantInfo(request.getLoginUser()).isError()) {
			return UserTools.checkMerchantInfo(request.getLoginUser());
		}
		// 群列表
		Set<String> groupSet = new HashSet<>(request.getParams().getGroupIds());
		// 单群拉人数
		final int singlePullPersonCount = request.getParams().getSingleGroupPerson();
		if (singlePullPersonCount <= 0) {
			return AjaxResult.error(ErrInfoConfig.getDynmic(11000, "singleGroupPerson"));
		}
		AjaxResult result = ProductTools.checkNormal(request.getProductId());
		if (result.isError()) {
			return result;
		}
		// 校验 商品
		final Product product = (Product) result.data();
		// 校验sku
		result = ProductTools.checkSkuList(request.getSkuList());
		if (result.isError()) {
			return result;
		}
		final List<ProductSku> skuList = (List<ProductSku>) result.data();
		// 计算价格
		long price = orderPriceHandler.handle(skuList, groupSet.size(), singlePullPersonCount);

		final String orderId = orderStorage(request, product, skuList, groupSet, price).getOrderId();
		return AjaxResult.success(orderId);
	}

	@Transactional
	public Order orderStorage(OrderProduceRequest request, Product product, List<ProductSku> skuList,
			Set<String> groupSet, long price) {
		final String orderId = OrderTools.orderId();
		// 冻结余额
		String frozenId = OrderTools.doFrozen(price, request.getLoginUser().getMerchantInfo().getMerchantId(), orderId);
		// 插入订单
		Order order = new Order();
		order.setOrderId(orderId);
		order.setCreateTime(new Date());
		order.setFrozenId(frozenId);
		order.setModifyTime(order.getCreateTime());
		// 订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款
		order.setOrderStatus(0);
		order.setParams(JSON.toJSONString(request.getParams()));
		order.setPrice(price);
		order.setProductId(product.getProductId());
		order.setRemark("");
		order.setUserId(request.getLoginUser().getUserId());
		order.setOrderType(product.getCategoryId());
		order.setMerchantId(request.getLoginUser().getMerchantInfo().getMerchantId());
		orderMapper.insert(order);

		// 插入订单sku明细
		List<OrderSku> orderSkus = new ArrayList<>();
		for (ProductSku sku : skuList) {
			OrderSku orderSku = new OrderSku();
			orderSku.setOrderId(orderId);
			orderSku.setBuyCount(1);
			orderSku.setCreateTime(order.getCreateTime());
			orderSku.setOrderSkuId(Ids.getId());
			orderSku.setPrice(sku.getPrice());
			orderSku.setProductId(product.getProductId());
			orderSku.setSkuId(sku.getId());
			orderSku.setUserId(request.getLoginUser().getUserId());
			orderSkus.add(orderSku);
		}
		orderSkuService.saveBatch(orderSkus);
		return order;
	}

	@Override
	public OrderDetailResponse detail(OrderRequest orderRequest) {
		final OrderDetailResponse response = new OrderDetailResponse();
		Order order = orderMapper.selectById(orderRequest.getOrderId());
		if (order == null) {
			return response;
		}
		response.setOrder(order);
		// 查询详细
		List<OrderSku> skuList = orderSkuService.getBaseMapper()
				.selectList(new QueryWrapper<OrderSku>().lambda().eq(OrderSku::getOrderId, order.getOrderId()));
		response.setOrderSkuList(skuList);
		// 查询退款
		OrderRefund orderRefund = orderRefundMapper.selectOne(new QueryWrapper<OrderRefund>().lambda()
				.eq(OrderRefund::getOrderId, order.getOrderId()).last(" limit 1"));
		response.setOrderRefund(orderRefund);
		return response;
	}

	@Override
	public PageInfo<OrderListResponse> list(OrderRequest orderRequest) {
		PageInfo<OrderListResponse> pageInfo = new PageInfo<>();
		int total = Objects.wrapNull(orderMapper.listCount(orderRequest), 0);
		pageInfo.setTotal(total);
		if (total <= 0) {
			return pageInfo;
		}
		final List<Order> orderList = orderMapper.list(orderRequest);

		final List<Long> productIds = ListTools.extract(orderList, f -> f.getProductId());
		final List<String> orderIds = ListTools.extract(orderList, f -> f.getOrderId());
		// 批量查询商品
		List<Product> products = ProductTools.list(productIds);
		Map<Long, List<Product>> productMap = ListTools.group(products, f -> f.getProductId());

		// 规格
		List<ProductSku> skuList = ProductTools.listSku(productIds);
		Map<Long, List<ProductSku>> skuMap = ListTools.group(skuList, f -> f.getProductId());

		// 查询退款
		List<OrderRefund> refundList = OrderTools.listRefund(orderIds);
		Map<String, List<OrderRefund>> refundMap = ListTools.group(refundList, f -> f.getOrderId());

		// 查询任务
		OrderTools.listTask(orderIds);

		List<OrderListResponse> responses = new ArrayList<>();
		for (Order order : orderList) {
			OrderListResponse res = new OrderListResponse();
			BeanUtils.copyProperties(order, res);

			// 商品名称
			if (!CollectionUtils.isEmpty(productMap.get(order.getProductId()))) {
				res.setProductName(productMap.get(order.getProductId()).get(0).getName());
			}
			// 规格
			if (!CollectionUtils.isEmpty(skuMap.get(order.getProductId()))) {
				res.setSkuNameList(Arrays.asList(ProductTools.skuName(skuMap.get(order.getProductId()).get(0))));
			}
			// 退款
			if (!CollectionUtils.isEmpty(refundMap.get(order.getOrderId()))) {
				// 退款金额
				res.setRefundPrice(
						Objects.wrapNull(refundMap.get(order.getOrderId()).get(0).getActualRefundPrice(), 0L));
			}
			res.setActualPrice(res.getPrice() - res.getRefundPrice());
			responses.add(res);
		}
		pageInfo.setList(responses);
		return pageInfo;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void refreshOrderStatus() {
		// 刷新 订单 状态
		try {
			// 保证 只有一个执行
			Object lockVal = redisCache.getCacheObject("ruoyi-admin:refreshOrderStatus:lock");
			if (lockVal != null || !StringUtils.isEmpty(lockVal)) {
				return;
			}
			redisCache.setCacheObject("ruoyi-admin:refreshOrderStatus:lock", "1", 60 * 60, TimeUnit.SECONDS);
			// 查询未完成，未取消的订单
			List<Order> orderList = OrderTools.listIncomplete();
			for (Order order : orderList) {
				// 处理单个订单
				try {
					OrderTools.handleOrderStatus(order);
				} catch (Exception e) {
					log.error("refreshOrderStatus失败 {}", order, e);
				}
			}
			// 计算 退款
			Objects.notNullDone(OrderTools.listComplete(), items -> {
				for (Order order : items) {
					// 是否达到 退款的条件
					if (order.getFinishTime() == null) {
						// 还没有完成
						continue;
					}
					// 完成时间 还没超过指定的 分钟 ， 也不退款
					if (new Date().getTime() - order.getFinishTime().getTime() < redisConfigure.getRefundTimeOutSec()
							* 1000) {
						continue;
					}
					// 到了超时时间，结算用户的订单
					OrderTools.settlementUserMonery(order, "定时结算");
					// 修改订单为已经退款 // 订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款
					orderMapper.updateStatus(order.getOrderId(), 4);
				}
			});
		} finally {
			redisCache.deleteObject("ruoyi-admin:refreshOrderStatus:lock");
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		redisCache.deleteObject("ruoyi-admin:refreshOrderStatus:lock");
	}

	@Override
	public void cancel(CancelOrderRequest request) {
		if (CollectionUtils.isEmpty(request.getOrderIds())) {
			return;
		}
		List<Order> orders = orderMapper.selectBatchIds(request.getOrderIds());
		if (CollectionUtils.isEmpty(orders)) {
			return;
		}
		for (Order order : orders) {
			// 订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款
			if (order.getOrderStatus().intValue() != 0 && order.getOrderStatus().intValue() != 1) {
				continue;
			}
			// 订单结算
			OrderTools.settlementUserMonery(order, "订单手动取消");
			// 修改为 已取消
			orderMapper.updateCancel(order.getOrderId(), request.getCause());
		}
	}

	@Override
	public SubmitResponse submit(String orderId) {
		// 查询任务
		Order order = orderMapper.selectById(orderId);
		// 订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款
		if (order == null || (order.getOrderStatus().intValue() != 0 && order.getOrderStatus().intValue() != 1)) {
			throw new ServiceException(ErrInfoConfig.get(11006));
		}
		SubmitResponse response = new SubmitResponse();
		response.setOrderId(orderId);
		response.setParam(JSON.parseObject(order.getParams(), OrderProduceRequest.Params.class));
		response.setTaskName("");
		return response;
	}

}