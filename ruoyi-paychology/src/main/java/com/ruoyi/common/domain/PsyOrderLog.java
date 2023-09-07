package com.ruoyi.common.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单操作记录对象 psy_order_log
 *
 * @author ruoyi
 * @date 2023-09-06
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_order_log")
public class PsyOrderLog implements Serializable
{

    private static final long serialVersionUID = 1808629250616557292L;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String oid;

    /** 订单类型 */
    @Excel(name = "订单类型")
    private String orderType;

    /** 操作类型 */
    @Excel(name = "操作类型")
    private String changeType;

    /** 操作备注 */
    @Excel(name = "操作备注")
    private String changeMessage;

    /** 操作人 */
    @Excel(name = "操作人")
    private String createBy;

    /** 操作时间 */
    @Excel(name = "操作时间")
    private Date createTime;

}