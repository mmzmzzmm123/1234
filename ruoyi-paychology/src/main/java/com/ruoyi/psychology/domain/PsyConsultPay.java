package com.ruoyi.psychology.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 心理咨询订单支付信息对象 psy_consult_pay
 * 
 * @author ruoyi
 * @date 2023-06-30
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_pay")
public class PsyConsultPay extends BasePlusEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private Long orderId;

    /** 订单号 */
    @Excel(name = "订单号")
    private String outTradeNo;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal amount;

    /** 支付ID */
    @Excel(name = "支付ID")
    private String payId;

    /** 支付方式  默认1-微信  2 支付宝 3  银行卡  4 其他 */
    @Excel(name = "支付方式  默认1-微信  2 支付宝 3  银行卡  4 其他")
    private Integer payType;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 支付状态(1-待支付,2-已支付，3-取消支付) */
    @Excel(name = "支付状态(1-待支付,2-已支付，3-取消支付)")
    private String status;

}
