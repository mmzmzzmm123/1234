package com.ruoyi.system.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.app.OrderDetailResponse;
import com.ruoyi.common.core.domain.app.OrderListResponse;
import com.ruoyi.common.core.domain.app.OrderProduceRequest;
import com.ruoyi.common.core.domain.app.OrderRequest;

public interface OrderService {

	/**
	 * title: 下单(返回订单id)
	 * 
	 * @param request
	 * @return
	 */
	AjaxResult produce(OrderProduceRequest request);

	/***
	 * title: 订单详情
	 * 
	 * @param orderRequest
	 * @return
	 */
	OrderDetailResponse detail(OrderRequest orderRequest);

	/**
	 * 订单列表
	 * 
	 * @param orderRequest
	 * @return
	 */
	PageInfo<OrderListResponse> list(OrderRequest orderRequest);

}
