package com.ruoyi.system.service;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.app.*;

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
	PageInfo<OrderListResponseVO> list(OrderRequest orderRequest);

	/***
	 * job 刷新订单状态
	 */
	void refreshOrderStatus();

	/***
	 * 批量取消订单
	 * 
	 * @param orderIds
	 */
	void cancel(CancelOrderRequest request);

	/***
	 * 提交下单拉群
	 * 
	 * @param orderId
	 */
	R<SubmitResponse> submit(String orderId);

	/**
	 * 根据更新时间查询订单列表
	 *
	 * @param orderRequest
	 * @return
	 */
	PageInfo<OrderListResponseVO> getListByUpdateTime(SelectOrderDTO orderRequest);
}
