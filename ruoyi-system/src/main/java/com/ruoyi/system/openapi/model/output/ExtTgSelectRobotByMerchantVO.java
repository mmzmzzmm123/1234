package com.ruoyi.system.openapi.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ExtTgSelectRobotByMerchantVO {

    @ApiModelProperty("商家id")
    private String merchantId;
    @ApiModelProperty("机器人编号")
    private String robotSerialNo;
    @ApiModelProperty("机器人ID")
    private String robotId;
    @ApiModelProperty("用户号")
    private String userName;
    @ApiModelProperty("头像")
    private String headImgUrl;
    @ApiModelProperty("名字")
    private String firstName;
    @ApiModelProperty("姓氏")
    private String lastName;
    @ApiModelProperty("机器人类型10：平台号 20-托管号")
    private Integer type;
    @ApiModelProperty("机器人状态 10:正常 20:出库 30:退回 40:找回 50:禁止登录 60:迁移 70:轮登")
    private Integer status;
    @ApiModelProperty("封号状态 10:未封号 20:疑似封号 30:封号离线")
    private Integer sealStatus;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("修改时间")
    private Date updateTime;
    @ApiModelProperty("20 在线 10 离线")
    private Integer heartbeatStatus;
    @ApiModelProperty("手机号")
    private String account;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("绑定时间")
    private String bindTime;
    @ApiModelProperty("国家地区编号")
    private String countryCode;
    @ApiModelProperty("国籍")
    private String country;
    @ApiModelProperty("机器人密码base64")
    private String robotPwd;
    @ApiModelProperty("ip")
    private String ip;
}
