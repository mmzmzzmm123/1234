package com.ruoyi.web.controller.business;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.app.*;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.system.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/23/023 10:25
 * @Description : 商家管理
 */
@Api("订单相关")
@RestController
@RequestMapping("order")
@Slf4j
public class OrderController extends BaseController {

	@Resource
	private OrderService orderService;

	@RepeatSubmit(interval = 1000, message = "请求过于频繁")
	@ApiOperation("下单(返回订单id)")
	@PostMapping("produce")
	public R<String> produce(@RequestBody OrderProduceRequest dto) {
		LoginUser loginUser = getLoginUser();
		if (ObjectUtils.isEmpty(loginUser.getMerchantInfo()) || loginUser.getMerchantInfo().getMerchantType() != 0) {
			return R.fail(ErrInfoConfig.getDynmic(11011));
		}
		if (dto.getTaskName().length() > 255) {
			return R.fail(ErrInfoConfig.getDynmic(11000, "任务名称不能超过255字"));
		}

		dto.setLoginUser(loginUser);
		try {
			AjaxResult r = orderService.produce(dto);
			if (r.isSuccess()) {
				return R.ok((String) r.data());
			}
			return R.fail(-1, r.msg() + "");
		}catch (ServiceException e) {
			return R.fail(e.getMessage());
		}catch (Exception e) {
			String id = Ids.getId();
			log.error("OrderController.produce {} {}", id, dto, e);
			return R.fail(ErrInfoConfig.getDynmic(10000, id));
		}
	}

	@ApiOperation("通过订单id查询订单详情")
	@PostMapping("detail")
	public R<OrderDetailResponse> detail(@RequestBody OrderRequest dto) {
		try {
			OrderDetailResponse res = orderService.detail(dto);
			return R.ok(res);
		} catch (Exception e) {
			String id = Ids.getId();
			log.error("OrderController.detail {} {}", id, dto, e);
			return R.fail(ErrInfoConfig.getDynmic(10000, id));
		}
	}

	@ApiOperation("订单列表")
	@PostMapping("list")
	public R<PageInfo<OrderListResponseVO>> list(@RequestBody OrderRequest dto) {
		dto.setMerchantType(getMerchantType());
		dto.setMerchantId(getMerchantId());
		try {
			PageInfo<OrderListResponseVO> res = orderService.list(dto);
			return R.ok(res);
		} catch (Exception e) {
			String id = Ids.getId();
			log.error("OrderController.list {} {}", id, dto, e);
			return R.fail(ErrInfoConfig.getDynmic(10000, id));
		}
	}

	@ApiOperation("批量取消订单")
	@PostMapping("cancel")
	public R<Void> cancel(@RequestBody CancelOrderRequest dto) {
		try {
			orderService.cancel(dto);
			return R.ok();
		} catch (Exception e) {
			String id = Ids.getId();
			log.error("OrderController.cancel {} {}", id, dto, e);
			return R.fail(ErrInfoConfig.getDynmic(10000, id));
		}
	}

	@ApiOperation("提交下单拉群(只需传orderId参数)")
	@PostMapping("submit")
	public R<SubmitResponse> submit(@RequestBody OrderRequest dto) {
		try {
			return orderService.submit(dto.getOrderId());
		} catch (Exception e) {
			String id = Ids.getId();
			log.error("OrderController.submit {} {}", id, dto, e);
			return R.fail(ErrInfoConfig.getDynmic(10000, StrUtil.format("{} {}", id, e.getMessage())));
		}
	}

	@ApiOperation("job执行")
	@PostMapping("refreshOrderStatus")
	public void refreshOrderStatus() {
		try {
			orderService.refreshOrderStatus();
		} catch (Exception e) {
			log.error("OrderController.refreshOrderStatus", e);
		}
	}

	@ApiOperation("根据更新时间查询订单列表")
	@PostMapping("/new/list")
	public R<PageInfo<OrderListResponseVO>> getListByUpdateTime(@RequestBody SelectOrderDTO dto) {
		dto.setMerchantType(getMerchantType());
		dto.setMerchantId(getMerchantId());
		try {
			return R.ok(orderService.getListByUpdateTime(dto));
		} catch (Exception e) {
			String id = Ids.getId();
			log.error("OrderController.listByUpdate {} {}", id, dto, e);
			return R.fail(ErrInfoConfig.getDynmic(10000, id));
		}
	}
}
