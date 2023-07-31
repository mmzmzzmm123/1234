package com.ruoyi.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 心理咨询订单支付信息对象 psy_consult_order_serve
 *
 * @author ruoyi
 * @date 2023-06-30
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_order_serve")
public class PsyConsultOrderServe extends BasePlusEntity implements Serializable {

    private static final long serialVersionUID = 5126385079031623695L;

    /** 订单 */
    @Excel(name = "订单")
    private Long orderId;

    /** 服务 */
    @Excel(name = "服务")
    private Long serveId;
    /** 咨询形式 语音咨询、视频咨询、面对面咨询 */
//    @Excel(name = "咨询形式 语音咨询、视频咨询、面对面咨询")
    private Integer mode;

    @Excel(name = "咨询形式")
    private String modeName;

    /** 服务类型 单次咨询 套餐咨询 */
//    @Excel(name = "服务类型 单次咨询 套餐咨询")
    private Integer type;

    @Excel(name = "服务类型")
    private String typeName;

    /** 服务名称 */
    @Excel(name = "服务名称")
    private String name;

    /** 服务介绍 */
    @Excel(name = "服务介绍")
    private String info;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 时长 */
    @Excel(name = "时长")
    private Integer time;

    /** 数量 */
    @Excel(name = "数量")
    private Integer num;

    /** 限购 0-不限制 1-限制 */
    private Integer bound;

    @Excel(name = "限购")
    private String boundName;

    /** 有效期 */
    @Excel(name = "有效期")
    private Date end;

}
