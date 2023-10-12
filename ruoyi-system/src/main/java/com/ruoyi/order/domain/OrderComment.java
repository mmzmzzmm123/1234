package com.ruoyi.order.domain;

import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单评论管理对象 bus_order_comment
 *
 * @author Lam
 * @date 2023-10-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-订单评论管理对象")
public class OrderComment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 订单标识 */
    @Excel(name = "订单标识")
    @ApiModelProperty("订单标识")
    private Long orderId;

    /** 评论内容 */
    @Excel(name = "评论内容")
    @ApiModelProperty("评论内容")
    private String comment;

    /** 是否展示 */
    @Excel(name = "是否展示")
    @ApiModelProperty("是否展示")
    private String ifShow;


}
