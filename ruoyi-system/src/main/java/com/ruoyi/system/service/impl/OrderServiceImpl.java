package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.app.*;
import com.ruoyi.common.core.domain.entity.MerchantInfo;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.common.core.domain.entity.order.Order;
import com.ruoyi.common.core.domain.entity.order.OrderRefund;
import com.ruoyi.common.core.domain.entity.order.OrderSku;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.Objects;
import com.ruoyi.common.utils.UrlValidator;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.*;
import com.ruoyi.system.components.TaskQuery.TaskAdapter;
import com.ruoyi.system.configure.RedisConfigure;
import com.ruoyi.system.domain.vo.AnalysisUploadPhoneResultVO;
import com.ruoyi.system.mapper.MerchantInfoMapper;
import com.ruoyi.system.mapper.OrderMapper;
import com.ruoyi.system.mapper.OrderRefundMapper;
import com.ruoyi.system.service.OrderService;
import com.ruoyi.system.service.OrderSkuService;
import com.ruoyi.system.service.business.UbpmPlusJoinChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

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

	@Autowired
	MerchantInfoMapper merchantInfoMapper;

	@Override
	@SuppressWarnings("unchecked")
	public AjaxResult produce(OrderProduceRequest request) {
		log.info("OrderServiceImpl.produce {}", JSON.toJSONString(request));
		if (request.getParams() == null || CollectionUtils.isEmpty(request.getParams().getGroupIds())) {
			return AjaxResult.error(ErrInfoConfig.getDynmic(11000, "params"));
		}
		if (!verifyGroupUrl(request.getParams().getGroupIds())) {
			return AjaxResult.error(ErrInfoConfig.getDynmic(11000, "群来源有不合规则数据"));
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
		// 下载excel文件
		if (!StringUtils.isEmpty(request.getParams().getExcelUrl())) {
			try {
//				ExcelUtil<PhoneExcelDTO> util = new ExcelUtil<PhoneExcelDTO>(PhoneExcelDTO.class);
//				List<PhoneExcelDTO> ids = util
//						.importExcel(UrlTools.getInputStreamByUrl(request.getParams().getExcelUrl()));
				final List<String> ids = FileUtils.getTextListByFilePath(request.getParams().getExcelUrl());
				if (!StringUtils.isEmpty(request.getParams().getTargetIds())) {
					if (CollectionUtils.isEmpty(ids)) {
						return AjaxResult.error(ErrInfoConfig.get(11010));
					}
					request.getParams().setTargetIds(ids);
				}
			} catch (Exception e) {
				log.error("文件格式错误 {}", request.getParams().getExcelUrl());
				return AjaxResult.error(ErrInfoConfig.get(11010));
			}
		}

		// 校验 商品
		final Product product = (Product) result.data();

		// 校验sku
		result = ProductTools.checkSkuList(request.getSkuList());
		if (result.isError()) {
			return result;
		}

		final List<ProductSku> skuList = (List<ProductSku>) result.data();

		// 校验手机号码归属国家是否和商品一致
		List<String> targetIds = request.getParams().getTargetIds();
		AnalysisUploadPhoneResultVO analysisResult =
				SpringUtils.getBean(UbpmPlusJoinChatRoomService.class).analysisPhoneNumbers(targetIds);

		// 手机号归属国家
		Map<String, Long> phoneOwnership = analysisResult.getPhoneOwnership();
		if (phoneOwnership.keySet().size() > 1) {
			return AjaxResult.error(ErrInfoConfig.get(11013));
		}

		// warning 后续可能存在多SKU
		ProductSku productSku = skuList.get(0);
		String ownership = phoneOwnership.keySet().stream().findFirst().orElse("未知国家");
		if (!ownership.contains(productSku.getCountyName())) {
			log.info("订单提交国家校验不通过 {} {}", ownership, productSku.getCountyName());
			return AjaxResult.error(ErrInfoConfig.get(11013));
		}

		// 计算价格
		long price = orderPriceHandler.handle(skuList, groupSet.size(), singlePullPersonCount);

		final String orderId = orderStorage(request, product, skuList, groupSet, price).getOrderId();
		return AjaxResult.success(orderId);
	}

	private boolean verifyGroupUrl(List<String> urlList) {
		for (String url : urlList) {
			if (!UrlValidator.validate(url)) {
				return false;
			}
		}
		return true;
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
		order.setUpdateTime(order.getCreateTime());
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

		List<OrderSkuVO> orderSkuVos = BeanUtil.copyToList(skuList, OrderSkuVO.class);
		response.setOrderSkuList(orderSkuVos);

		// 查询退款
		OrderRefund orderRefund = orderRefundMapper.selectOne(new QueryWrapper<OrderRefund>().lambda()
				.eq(OrderRefund::getOrderId, order.getOrderId()).last(" limit 1"));
		OrderRefundVO orderRefundVO = BeanUtil.copyProperties(orderRefund, OrderRefundVO.class);
		response.setOrderRefund(orderRefundVO);
		return response;
	}

	@Override
	public PageInfo<OrderListResponseVO> list(OrderRequest orderRequest) {
		PageInfo<OrderListResponseVO> pageInfo = new PageInfo<>();
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
		Map<String, List<TaskAdapter>> taskMap = new HashMap<>();
		try {
			List<TaskAdapter> taskAdapters = TaskQuery.newQuery(0).listByOrder(orderIds);
			taskMap = ListTools.group(taskAdapters, f -> f.getOrderId());
		} catch (Exception e) {
		}

		// 商家名称
		Map<String, List<MerchantInfo>> merchantMap = new HashMap<>();
		try {
			List<MerchantInfo> merchants = merchantInfoMapper
					.selectBatchIds(new HashSet<>(ListTools.extract(orderList, f -> f.getMerchantId())));
			merchantMap = ListTools.group(merchants, f -> f.getMerchantId());
		} catch (Exception e) {
		}

		List<OrderListResponseVO> responses = new ArrayList<>();
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


			// 商家名称
			if (!CollectionUtils.isEmpty(merchantMap.get(order.getMerchantId()))) {
				res.setMerchantName(merchantMap.get(order.getMerchantId()).get(0).getMerchantName());
			}
			// 任务ID
			if (!CollectionUtils.isEmpty(taskMap.get(order.getOrderId()))) {
				res.setTaskId(taskMap.get(order.getOrderId()).get(0).getTaskId());
			}
			OrderListResponseVO response = BeanUtil.copyProperties(res, OrderListResponseVO.class);
			responses.add(response);
		}
		pageInfo.setList(responses);
		return pageInfo;
	}


	@SuppressWarnings("deprecation")
	@Override
	public void refreshOrderStatus() {
		log.info("refreshOrderStatus");
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
				log.info("订单状态刷新完成 {}", order);
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
					log.info("结算完成 {}", order);
				}
				log.info("结算完成");
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
		log.info("cancel_order {}", JSON.toJSONString(request));
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
	public R<SubmitResponse> submit(String orderId) {
		log.info("submit_order {}", orderId);
		// 查询任务
		Order order = orderMapper.selectById(orderId);
		// 订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款
		if (order == null || (order.getOrderStatus().intValue() != 0 && order.getOrderStatus().intValue() != 1)) {
			return R.fail(ErrInfoConfig.get(11006));
		}
		SubmitResponse response = new SubmitResponse();
		response.setOrderId(orderId);
		response.setParam(JSON.parseObject(order.getParams(), OrderProduceRequest.Params.class));

		List<TaskAdapter> taskAdapters = TaskQuery.newQuery(0).listByOrder(Arrays.asList(orderId));
		Objects.notNullDone(taskAdapters, s -> response.setTaskName(s.get(0).getTaskName()));

		// 查询商品
		try {
			ProductSku sku = ProductTools.listSku(Arrays.asList(order.getProductId())).get(0);
			response.setCountyCode(sku.getCountyCode());
			response.setCountyName(sku.getCountyName());
		} catch (Exception e) {
		}
		return R.ok(response);
	}

}
