package com.ruoyi.course.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("课程订单")
public class OrderVO {
    /** ID */
    private Long id;

    /** 订单编号 */
    @ApiModelProperty("订单编号")
    private String orderId;

    /** 订单状态（0-创建 1-完成 2-关闭） */
    @ApiModelProperty("订单状态")
    private Integer status;

    /** 用户编号 */
    @ApiModelProperty("用户编号")
    private String userId;

    /** 应付金额 */
    @ApiModelProperty("应付金额")
    private BigDecimal amount;

    /** 支付ID */
    @ApiModelProperty("支付ID")
    private String payId;

    /** 课程编号 */
    @ApiModelProperty("课程编号")
    private String courseId;

    /** 订单支付状态 */
    @ApiModelProperty("订单支付状态")
    private Integer payStatus;

    /** 支付金额 */
    @ApiModelProperty("支付金额")
    private BigDecimal payAmount;
}
