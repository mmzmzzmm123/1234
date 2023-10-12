package com.ruoyi.api.order.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.api.staff.model.vo.ApiStaffInfoVo;
import com.ruoyi.api.user.model.vo.ApiUserVo;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/11 14:01
 */
@ApiModel("api-订单信息")
@Data
@Accessors(chain = true)
public class ApiOrderInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标识")
    private Long id;

    @ApiModelProperty("订单详情")
    private List<ApiOrderDetailsVo> orderDetailsVos;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("订单类型")
    private String orderType;

    @ApiModelProperty("订单状态")
    private String orderState;

    @ApiModelProperty("是否续单")
    private String ifContinuous;

    @ApiModelProperty("员工标识")
    private Long staffUserId;

    @ApiModelProperty("员工信息")
    private ApiStaffInfoVo apiStaffInfoVo;

    @ApiModelProperty("员工等级")
    private Long staffLevel;

    @ApiModelProperty("提成比例")
    private BigDecimal commissionRatio;

    @ApiModelProperty("客户标识")
    private Long customUserId;

    @ApiModelProperty("用户信息")
    private ApiUserVo apiUserVo;

    @ApiModelProperty("账号服务商")
    private String accountServiceProvider;

    @ApiModelProperty("客户账号")
    private String customNum;

    @ApiModelProperty("选择店员性别")
    private String chooseStaffSex;

    @ApiModelProperty("排除服务过的店员")
    private String filterServedStaff;

    @ApiModelProperty("订单金额")
    private BigDecimal amount;

    @ApiModelProperty("支付方式, 0=微信，1余额")
    private String payWay;

    @ApiModelProperty("支付金额")
    private BigDecimal payAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("自动过期时间")
    private Date autoExpireTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("自动完成时间")
    private Date autoFinshTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("接单时间")
    private Date orderReceivingTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("开始服务时间")
    private Date orderServiceTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("订单完成时间")
    private Date orderFinshTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("订单取消时间")
    private Date orderCancelTime;

    @ApiModelProperty("取消者")
    private String canceller;

    @ApiModelProperty("取消留言")
    private String cancelRemark;

    @ApiModelProperty("备注")
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;
}
