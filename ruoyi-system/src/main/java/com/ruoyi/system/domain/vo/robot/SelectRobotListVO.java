package com.ruoyi.system.domain.vo.robot;

import com.alibaba.fastjson2.annotation.JSONField;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel("SelectRobotListVO")
@Data
public class SelectRobotListVO{

    @ApiModelProperty(value = "机器人id")
    private String id;

    @Excel(name = "机器人编号")
    @ApiModelProperty(value = "机器人编号")
    private String robotSerialNo;

    @Excel(name = "用户名")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @Excel(name = "名称")
    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "名字")
    private String firstName;

    @ApiModelProperty(value = "姓氏")
    private String lastName;

    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机号")
    private String phone;

    @Excel(name = "国籍")
    @ApiModelProperty(value = "国籍")
    private String country;

    @ApiModelProperty(value = "国家编码")
    private String countryCode;

    @Excel(name = "头像")
    @ApiModelProperty(value = "头像")
    private String headImgUrl;

    @Excel(name = "是否群主号", readConverterExp = "0=否,1=是")
    @ApiModelProperty(value = "是否是群主号,0-否,1-是")
    private Integer groupOwner;

    @Excel(name = "账号类型", readConverterExp = "10=平台号,20=托管号")
    @ApiModelProperty(value = "机器人类型10-平台号 20-托管号")
    private Integer type;

    @Excel(name = "协议类型", readConverterExp = "2=iPhone,4=Android,16=云机,17=工作机")
    @ApiModelProperty(value = "协议类型：2-iPhone 4-Android 16-云机 17-工作机")
    private Integer protocolType;

    @Excel(name = "APP版本")
    @ApiModelProperty(value = "app版本")
    private String tgAppVersion;

    @Excel(name = "封号状态", readConverterExp = "10=未封号,20=疑似封号,30=封号离线")
    @ApiModelProperty(value = "封号状态 10:未封号 20:疑似封号 30:封号离线")
    private Integer sealStatus;

    @Excel(name = "在线状态", readConverterExp = "20=在线,10=离线")
    @ApiModelProperty(value = "在线状态 20 在线 10 离线")
    private Integer heartbeatStatus;

    @Excel(name = "代理类型")
    @ApiModelProperty(value = "代理类型")
    private String proxyType;

    @Excel(name = "vpn登陆ip")
    @ApiModelProperty(value = "vpn登陆ip")
    private String vpnIp;

    @Excel(name = "禁用/启用状态", readConverterExp = "0=禁用,1=启用")
    @ApiModelProperty(value = "禁用启用状态,0-禁用,1-启用")
    private Integer enableType;

    @Excel(name = "水军占用状态", readConverterExp = "0=未占用,1=已占用")
    @ApiModelProperty(value = "水军占用状态,0-未占用,1-已占用")
    private Integer occupyType;

    @Excel(name = "双向状态-是否限制", readConverterExp = "0=未被限制,1=已被限制")
    @ApiModelProperty(value = "双向状态,0-未被限制,1-已被限制")
    private Integer bidirectionalType;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "双向状态-解除限制时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "双向状态-解除限制时间")
    private LocalDateTime bidirectionalUnfreezeTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "添加时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "添加时间")
    private LocalDateTime addTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "封号时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "封号时间")
    private LocalDateTime sealTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "双向时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "双向时间")
    private LocalDateTime bidirectionalTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @Excel(name = "带群数量-今日")
    @ApiModelProperty(value = "今日带群数")
    private Integer groupToDayNum;

    @Excel(name = "带群数量-累计")
    @ApiModelProperty(value = "累计带群数")
    private Integer groupTotalNum;


}
