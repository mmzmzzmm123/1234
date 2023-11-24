package com.ruoyi.psychology.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * *
 * @author ruoyi
 * @date 2023-06-26
 */
@Data
public class PsyAdminBillReq implements Serializable
{

    private static final long serialVersionUID = -8791334840022479201L;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "咨询师")
    private Long consultId;

    @ApiModelProperty(value = "账单")
    private Long billId;

    @ApiModelProperty(value = "开始时间")
    private String startTime; //开始时间

    @ApiModelProperty(value = "结束时间")
    private String endTime; //结束时间

}
