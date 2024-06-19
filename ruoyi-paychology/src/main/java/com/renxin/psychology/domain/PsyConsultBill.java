package com.renxin.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 咨询订单对象 psy_consult_bill
 *
 * @author renxin
 * @date 2023-06-26
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_bill")
public class PsyConsultBill extends BasePlusEntity implements Serializable
{

    private static final long serialVersionUID = 6244619717195229983L;

    /** 账单时间 */
    @Excel(name = "账单时间")
    private String billTime;

    /** 结算次数 */
    @Excel(name = "结算次数")
    @TableField(exist = false)
    private Integer num;

    /** 订单笔数 */
    @Excel(name = "订单笔数")
    @TableField(exist = false)
    private Integer orderNum;

    /** 结算咨询师 */
    @Excel(name = "结算咨询师")
    @TableField(exist = false)
    private Integer consultNum;

    /** 分期结算款项 */
    @Excel(name = "分期结算款项")
    @TableField(exist = false)
    private BigDecimal total;

    /** 分期结算佣金 */
    @Excel(name = "分期结算佣金")
    @TableField(exist = false)
    private BigDecimal pay;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

}
