package com.ruoyi.system.callback.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Called50005009DTO {

    @ApiModelProperty(value = "机器人编号")
    private String robotSerialNo;

    @ApiModelProperty(value = "用户号")
    private String userName;

    @ApiModelProperty(value = "头像")
    private String headImgUrl;

    @ApiModelProperty(value = "名字")
    private String firstName;

    @ApiModelProperty(value = "姓氏")
    private String lastName;

    @ApiModelProperty(value = "机器人类型10：平台号")
    private Integer type;

    @ApiModelProperty(value = "机器人状态 10:正常 20:出库 30:退回 40:找回 50:禁止登录 60:迁移 70:轮登")
    private Integer status;

    @ApiModelProperty(value = "封号状态 10:未封号 20:疑似封号 30:封号离线")
    private Integer sealStatus;

    @ApiModelProperty(value = "20 在线 10 离线")
    private Integer heartbeatStatus;

    @ApiModelProperty(value = "手机号")
    private String account;

    @ApiModelProperty("国家地区编号")
    private String countryCode;

}
