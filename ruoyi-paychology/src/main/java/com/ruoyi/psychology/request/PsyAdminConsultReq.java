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
public class PsyAdminConsultReq implements Serializable
{

    private static final long serialVersionUID = -8791334840022479201L;

    @ApiModelProperty(value = "添加时间")
    private String dateLimit;

    @ApiModelProperty(value = "咨询师姓名")
    private String userName;

    private String startTime; //开始时间

    private String endTime; //结束时间

    private String delFlag;

    private String status;

}
