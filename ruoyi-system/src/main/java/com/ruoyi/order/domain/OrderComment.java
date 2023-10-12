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
 * @date 2023-10-12
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

    /** 店员标识 */
    @Excel(name = "店员标识")
    @ApiModelProperty("店员标识")
    private Long staffUserId;

    /** 用户标识 */
    @Excel(name = "用户标识")
    @ApiModelProperty("用户标识")
    private Long userId;

    /** 用户头像 */
    @Excel(name = "用户头像")
    @ApiModelProperty("用户头像")
    private String userAvatar;

    /** 用户名称 */
    @Excel(name = "用户名称")
    @ApiModelProperty("用户名称")
    private String userName;

    /** 评论内容 */
    @Excel(name = "评论内容")
    @ApiModelProperty("评论内容")
    private String comment;

    /** 服务内容 */
    @Excel(name = "服务内容")
    @ApiModelProperty("服务内容")
    private String serviceContent;

    /** 是否展示 */
    @Excel(name = "是否展示")
    @ApiModelProperty("是否展示")
    private String ifShow;


}
