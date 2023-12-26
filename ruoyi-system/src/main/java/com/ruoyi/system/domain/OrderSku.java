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
@TableName(value = "t_order_sku")
@Data
public class OrderSku implements Serializable {

	/**
	 * 主键id
	 */
	@TableId
	private String orderSkuId;
	/**
	 * 订单ID
	 */
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
	 * 购买数量
	 */
	private Integer buyCount;

	/**
	 * sku id
	 */
	private Long skuId;

	/**
	 * 下单时间
	 */
	private Date createTime;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}