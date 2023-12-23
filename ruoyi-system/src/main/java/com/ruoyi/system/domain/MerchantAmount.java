package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商家资金表
 * @TableName t_merchant_amount
 */
@TableName(value ="t_merchant_amount")
@Data
public class MerchantAmount implements Serializable {
    /**
     * 商家ID
     */
    @TableId
    private String merchantId;

    /**
     * 账户总金额
     */
    private Long totalAmount;

    /**
     * 可用余额
     */
    private Long availableAmount;

    /**
     * 锁定金额
     */
    private Long lockAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}