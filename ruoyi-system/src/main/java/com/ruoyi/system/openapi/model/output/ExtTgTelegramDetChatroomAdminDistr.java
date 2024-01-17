package com.ruoyi.system.openapi.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ExtTgTelegramDetChatroomAdminDistr {


    @ApiModelProperty("群主编号")
    private String adminSerialNo;
    @ApiModelProperty("群主id")
    private String adminId;
    @ApiModelProperty("群成员数")
    private Integer accountNum;
    @ApiModelProperty("成员封号数")
    private Integer accountForbiddenNum;
    @ApiModelProperty("成员封号率")
    private String accountRate;
    @ApiModelProperty("群数")
    private Integer groupNum;
    @ApiModelProperty("封群数")
    private Integer groupForbiddenNum;
    @ApiModelProperty("封群率")
    private String groupRate;
    @ApiModelProperty("创建时间")
    private Date createTime;
}
