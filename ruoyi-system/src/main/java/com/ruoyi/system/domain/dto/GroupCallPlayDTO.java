package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class GroupCallPlayDTO implements Serializable {
    private static final long serialVersionUID = -6720475873307726186L;

    @ApiModelProperty(value = "群唯一标识")
    private String groupIdent;

    @ApiModelProperty(value = "群状态: 0私密 1公开")
    private Integer status;

    @ApiModelProperty(value = "群名称")
    private String groupName;

    @ApiModelProperty(value = "群url")
    private String groupUrl;

    @ApiModelProperty(value = "群成员数量")
    private Integer memberCount;

    @ApiModelProperty(value = "用户自行进群数")
    private Integer userJoin;

    @ApiModelProperty(value = "管理员拉进群数")
    private Integer adminHandleJoin;

    @ApiModelProperty(value = "用户自行退群数")
    private Integer userQuit;

    @ApiModelProperty(value = "管理员操作退群数")
    private Integer adminHandleQuit;

    @ApiModelProperty(value = "拉手号数量")
    private Integer shake;

    @ApiModelProperty(value = "水军号数量")
    private Integer robot;
}
