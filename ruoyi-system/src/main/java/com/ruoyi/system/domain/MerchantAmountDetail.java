package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商家资金明细表
 * @TableName t_merchant_amount_detail
 */
@TableName(value ="t_merchant_amount_detail")
@Data
public class MerchantAmountDetail implements Serializable {
    /**
     * 主键ID
     */
    @TableId
    private String detailId;

    /**
     * 商家ID
     */
    private String merchantId;

    /**
     * 操作类型 1-划拨 2-消费 3-退款 4-扣款 5-回收
     */
    private Integer operationType;

    /**
     * 资金变动前
     */
    private Long changeBefore;

    /**
     * 变动金额
     */
    private Long changeAmount;

    /**
     * 资金变动后
     */
    private Long changeAfter;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 描述
     */
    private String describe;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 资金类型 1-支出 2-收入
     */
	private Integer amountType;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}