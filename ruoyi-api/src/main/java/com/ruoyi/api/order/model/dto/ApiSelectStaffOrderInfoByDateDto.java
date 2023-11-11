package com.ruoyi.api.order.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/21 23:26
 */
@ApiModel("api-根据时间段查询店员订单基本数据")
@Data
@Accessors(chain = true)
public class ApiSelectStaffOrderInfoByDateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("查询开始时间")
    private String beginCreateTime;

    @ApiModelProperty("查询结束时间")
    private String endCreateTime;

    @ApiModelProperty("店员用户标识")
    private Long staffUserId;
}
