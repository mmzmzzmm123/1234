package com.ruoyi.system.openapi.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExtTgBatchRobotSimpInfoData {

    @ApiModelProperty(value = "机器人编号")
    private String robotSerialNo;
    @ApiModelProperty(value = "注册国家")
    private String registerCountry;
    @ApiModelProperty(value = "注册时间")
    private String registerTime;
    @ApiModelProperty(value = "注册IP")
    private String registerIp;
    @ApiModelProperty(value = "渠道名称")
    private String channel;
    @ApiModelProperty(value = "渠道编码")
    private String channelCode;
    @ApiModelProperty(value = "渠道地区")
    private String channelCountry;
    @ApiModelProperty(value = "注册app版本")
    private String tgAppVersion;
    @ApiModelProperty(value = "代理类型，长效或短效")
    private String proxyType;
    @ApiModelProperty(value = "设备编号")
    private String deviceNo;
    @ApiModelProperty(value = "注册机型")
    private String phoneModel;
    @ApiModelProperty(value = "设备机型")
    private String deviceModel;
    @ApiModelProperty(value = "协议类型：2 iPhone 4 Android 16云机 17工作机")
    private Integer protocolType;
    @ApiModelProperty(value = "注册设备网络类型")
    private String networkType;
    @ApiModelProperty(value = "注册手机卡国家")
    private String phoneBelongCountry;

    @ApiModelProperty(value = "账户")
    private String account;
    @ApiModelProperty(value = "入库时间")
    private String instockTime;
    @ApiModelProperty(value = "大版本")
    private String largeVersion;
    @ApiModelProperty(value = "登录IP")
    private String senderProxyIp;
    @ApiModelProperty(value = "登录端口")
    private String proxyPort;
    @ApiModelProperty(value = "操作编号")
    private String serialNo;
    @ApiModelProperty(value = "时间")
    private String serviceTime;
}
