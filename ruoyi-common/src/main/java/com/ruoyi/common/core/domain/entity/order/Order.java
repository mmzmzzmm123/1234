package com.ruoyi.common.core.domain.entity.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 订单类型 0-拉人入群
	 */
	private Integer orderType ;
	
	/**
	 * 商户id
	 */
	private String merchantId;
	
	/**
	 * 冻结单
	 */
    private String frozenId;

	/**
	 * 商品id
	 */
	private Long productId;

	/**
	 * 总价格
	 */
	private Long price;

	/**
	 * 订单状态 0-等待处理 1-进行中 2-已完成 3-已取消 4-已退款
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
	 * 订单完成时间
	 */
	private Date finishTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}