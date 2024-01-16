package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GroupCallPlayDTO implements Serializable {
    private static final long serialVersionUID = -6720475873307726186L;

    @ApiModelProperty(value = "群唯一标识")
    private String groupIdent;

    @ApiModelProperty(value = "群成员数量")
    private Integer memberCount;

    @ApiModelProperty(value = "进群数")
    private Integer joinQuantity;

    @ApiModelProperty(value = "用户自行进群数")
    private Integer userJoin;

    @ApiModelProperty(value = "邀请进群数")
    private Integer adminHandleJoin;

    @ApiModelProperty(value = "退群数")
    private Integer leaveQuantity;

    @ApiModelProperty(value = "用户自行退群数")
    private Integer userQuit;

    @ApiModelProperty(value = "管理员操作退群数")
    private Integer adminHandleQuit;

    @ApiModelProperty(value = "拉手号数量")
    private Integer shake;

    @ApiModelProperty(value = "水军号数量")
    private Integer robot;
}
