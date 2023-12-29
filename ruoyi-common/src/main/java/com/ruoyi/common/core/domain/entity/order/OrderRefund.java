package com.ruoyi.common.core.domain.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商家资金明细表
 * 
 * @TableName t_merchant_amount_detail
 */
@TableName(value = "t_order_refund")
@Data
public class OrderRefund implements Serializable {

	/**
	 * 主键id
	 */
	@TableId
	private String refundId;
	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 商家id
	 */
	private String merchantId;

	/**
	 * 计算退款 金额
	 */
	private Long calculateRefundPrice;

	/**
	 * 实际退款金额
	 */
	private Long actualRefundPrice;

	private String remark;

	/**
	 * 下单时间
	 */
	private Date createTime;
	
	/**
	 * 任务名称
	 */
	private String taskId;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}