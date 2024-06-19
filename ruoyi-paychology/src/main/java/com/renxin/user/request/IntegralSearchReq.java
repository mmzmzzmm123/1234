package com.renxin.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 咨询订单对象 psy_consult_order
 * 
 * @author renxin
 * @date 2023-06-26
 */
@Data
public class IntegralSearchReq implements Serializable
{

    private static final long serialVersionUID = -2347512854358039980L;

    @ApiModelProperty(value = "添加时间")
    private String dateLimit;

    @ApiModelProperty(value = "搜索关键字")
    private String keywords;

    @ApiModelProperty(value = "用户id")
    private Integer uid;

    @ApiModelProperty(value = "收入,支出")
    private Integer type;

    private String startTime; //开始时间

    private String endTime; //结束时间

    private Integer delFlag;

    private Integer status;

}
