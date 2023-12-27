package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商家资金冻结明细表
 * @TableName t_merchant_amount_frozen_detail
 */
@TableName(value ="t_merchant_amount_frozen_detail")
@Data
public class MerchantAmountFrozenDetail implements Serializable {
    /**
     * 主键ID
     */
    @TableId
    private String frozenId;

    /**
     * 商家ID
     */
    private String merchantId;

    /**
     * 资金变动前
     */
    private Long changeBefore;

    /**
     * 冻结金额
     */
    private Long frozenAmount;

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
    @TableField(value = "`describe`")
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
     * 冻结状态 1-冻结中 2-已解冻
     */
    private Integer frozenState;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}