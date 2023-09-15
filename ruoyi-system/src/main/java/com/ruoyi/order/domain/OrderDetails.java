package com.ruoyi.order.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单明细对象 bus_order_details
 *
 * @author Lam
 * @date 2023-09-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-订单明细对象")
public class OrderDetails extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 订单标识 */
    @Excel(name = "订单标识")
    @ApiModelProperty("订单标识")
    private Long orderId;

    /** 状态（-1已取消，0正常，1待支付，2退款中，3已退款） */
    @Excel(name = "状态", readConverterExp = "-=1已取消，0正常，1待支付，2退款中，3已退款")
    @ApiModelProperty("状态")
    private String state;

    /** 服务标识 */
    @Excel(name = "服务标识")
    @ApiModelProperty("服务标识")
    private Long serviceId;

    /** 服务项目标识 */
    @Excel(name = "服务项目标识")
    @ApiModelProperty("服务项目标识")
    private Long serviceItemId;

    /** 服务价格标识 */
    @Excel(name = "服务价格标识")
    @ApiModelProperty("服务价格标识")
    private Long serviceItemPriceId;

    /** 礼物标识 */
    @Excel(name = "礼物标识")
    @ApiModelProperty("礼物标识")
    private Long giftId;

    /** 明细标题 */
    @Excel(name = "明细标题")
    @ApiModelProperty("明细标题")
    private String detailsTitle;

    /** 数量 */
    @Excel(name = "数量")
    @ApiModelProperty("数量")
    private Long num;

    /** 总价 */
    @Excel(name = "总价")
    @ApiModelProperty("总价")
    private BigDecimal totalPrice;


}
