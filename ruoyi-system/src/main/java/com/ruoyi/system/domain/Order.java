package com.ruoyi.system.domain;

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
@TableName(value = "t_order")
@Data
public class Order implements Serializable {
	/**
	 * 订单ID
	 */
	@TableId
	private String orderId;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 商品id
	 */
	private Long productId;

	/**
	 * 总价格
	 */
	private Long price;

	/**
	 * 订单状态 0-已下单 1-已支付 2-已完成
	 */
	private Integer orderStatus;

	/**
	 * 提交json参数
	 */
	private String params;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 下单时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}