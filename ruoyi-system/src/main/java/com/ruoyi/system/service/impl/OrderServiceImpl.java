package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.app.OrderDetailResponse;
import com.ruoyi.common.core.domain.app.OrderListResponse;
import com.ruoyi.common.core.domain.app.OrderProduceRequest;
import com.ruoyi.common.core.domain.app.OrderRequest;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.common.core.domain.entity.order.Order;
import com.ruoyi.common.core.domain.entity.order.OrderRefund;
import com.ruoyi.common.core.domain.entity.order.OrderSku;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.Objects;
import com.ruoyi.system.components.OrderPriceHandler;
import com.ruoyi.system.components.OrderTools;
import com.ruoyi.system.components.ProductTools;
import com.ruoyi.system.components.UserTools;
import com.ruoyi.system.mapper.OrderMapper;
import com.ruoyi.system.mapper.OrderRefundMapper;
import com.ruoyi.system.service.OrderService;
import com.ruoyi.system.service.OrderSkuService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	private final OrderPriceHandler orderPriceHandler = new OrderPriceHandler.MultiAssemblyOrderPriceHandler();

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	OrderSkuService orderSkuService;

	@Autowired
	OrderRefundMapper orderRefundMapper;

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
		String frozenId = OrderTools.frozen(price, request.getLoginUser().getMerchantInfo().getMerchantId(), orderId);
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
			orderSku.setPrice(orderPriceHandler.handle(Arrays.asList(sku), groupSet.size(),
					request.getParams().getSingleGroupPerson()));
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
				res.setRefundPrice(Objects.wrapNull(refundMap.get(order.getOrderId()).get(0).getRefundPrice(), 0L));
			}
			res.setActualPrice(res.getPrice() - res.getRefundPrice());
			responses.add(res);
		}
		pageInfo.setList(responses);
		return pageInfo;
	}

	@Override
	public void refreshOrderStatus() {

	}

}
