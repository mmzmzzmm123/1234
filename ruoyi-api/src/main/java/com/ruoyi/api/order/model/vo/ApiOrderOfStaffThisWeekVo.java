package com.ruoyi.api.order.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/18 22:35
 */
@ApiModel("api-获取店员本周订单数据")
@Data
@Accessors(chain = true)
public class ApiOrderOfStaffThisWeekVo implements Serializable {

    @ApiModelProperty("订单总数")
    private int orderNum;

    @ApiModelProperty("已结总单数")
    private int settledNum;

    @ApiModelProperty("未结总单数")
    private int unSettledNum;

    @ApiModelProperty("取消总单数")
    private int cancelNum;

    @ApiModelProperty("超时单数")
    private int overTimeNum;

    @ApiModelProperty("续单率")
    private BigDecimal renewalRate;

    @ApiModelProperty("待订单完结佣金")
    private BigDecimal waitOrderFinishCommission;

    @ApiModelProperty("有效的订单总额")
    private BigDecimal effectiveTotalAmount;

    @ApiModelProperty("订单总额")
    private BigDecimal orderTotalAmount;

    @ApiModelProperty("订单数据列表")
    private List<ApiOrderInfoVo> orderInfoVoList;
}
