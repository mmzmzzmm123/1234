package com.ruoyi.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.staff.domain.StaffInfo;
import com.ruoyi.user.domain.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单信息对象 bus_order_info
 *
 * @author Lam
 * @date 2023-09-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-订单信息对象")
public class OrderInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderNo;

    /** 订单类型（0指定、1随机、2礼物、3打赏） */
    @Excel(name = "订单类型", readConverterExp = "0=指定、1随机、2礼物、3打赏")
    @ApiModelProperty("订单类型")
    private String orderType;

    /** 订单状态（-1取消订单，0已完成，1待支付，2待接单，3待服务，4服务中，999已退款） */
    @Excel(name = "订单状态", readConverterExp = "-=1取消订单，0已完成，1待支付，2待接单，3待服务，4服务中，999已退款")
    @ApiModelProperty("订单状态")
    private String orderState;

    /** 是否续单 */
    @Excel(name = "是否续单")
    @ApiModelProperty("是否续单")
    private String ifContinuous;

    /** 员工标识 */
    @Excel(name = "员工标识")
    @ApiModelProperty("员工标识")
    private Long staffUserId;

    /** 员工等级 */
    @Excel(name = "员工等级")
    @ApiModelProperty("员工等级")
    private Long staffLevel;

    /** 提成比例 */
    @Excel(name = "提成比例")
    @ApiModelProperty("提成比例")
    private BigDecimal commissionRatio;

    /** 客户标识 */
    @Excel(name = "客户标识")
    @ApiModelProperty("客户标识")
    private Long customUserId;

    /** 账号服务商 */
    @Excel(name = "账号服务商")
    @ApiModelProperty("账号服务商")
    private String accountServiceProvider;

    /** 客户账号 */
    @Excel(name = "客户账号")
    @ApiModelProperty("客户账号")
    private String customNum;

    @Excel(name = "选择店员性别")
    @ApiModelProperty("选择店员性别")
    private String chooseStaffSex;

    @Excel(name = "排除服务过的店员")
    @ApiModelProperty("排除服务过的店员")
    private String filterServedStaff;

    /** 订单金额 */
    @Excel(name = "订单金额")
    @ApiModelProperty("订单金额")
    private BigDecimal amount;

    /** 卡券标识 */
    @Excel(name = "卡券标识")
    @ApiModelProperty("卡券标识")
    private Long couponId;

    /** 优惠金额 */
    @Excel(name = "优惠金额")
    @ApiModelProperty("优惠金额")
    private BigDecimal discountAmount;

    /** 支付方式（0微信，1余额） */
    @Excel(name = "支付方式", readConverterExp = "0=微信，1余额")
    @ApiModelProperty("支付方式")
    private String payWay;

    /** 支付金额 */
    @Excel(name = "支付金额")
    @ApiModelProperty("支付金额")
    private BigDecimal payAmount;

    /** 自动过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "自动过期时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("自动过期时间")
    private Date autoExpireTime;

    /** 自动完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "自动完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("自动完成时间")
    private Date autoFinshTime;

    /** 接单时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "接单时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("接单时间")
    private Date orderReceivingTime;

    /** 开始服务时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始服务时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("开始服务时间")
    private Date orderServiceTime;

    /** 订单完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "订单完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("订单完成时间")
    private Date orderFinshTime;

    /** 订单取消时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "订单取消时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("订单取消时间")
    private Date orderCancelTime;

    /** 取消者 */
    @Excel(name = "取消者")
    @ApiModelProperty("取消者")
    private String canceller;

    /** 取消留言 */
    @Excel(name = "取消留言")
    @ApiModelProperty("取消留言")
    private String cancelRemark;

    @ApiModelProperty("过滤店员标识为空")
    private String filterStaffIdIsNull;

    @ApiModelProperty("订单详情数据")
    private List<OrderDetails> orderDetailsList;

    @ApiModelProperty("店员数据")
    private StaffInfo staffInfo;

    @ApiModelProperty("用户数据")
    private UserInfo userInfo;
}
