package com.ruoyi.common.core.domain.entity.robot;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("t_robot_info")
public class Robot {
    @TableId
    private String id;

    @ApiModelProperty(value = "机器人编号")
    private String robotSerialNo;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "名字")
    private String firstName;

    @ApiModelProperty(value = "姓氏")
    private String lastName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "国籍")
    private String country;

    @ApiModelProperty(value = "国家编码")
    private String countryCode;

    @ApiModelProperty(value = "头像")
    private String headImgUrl;

    @ApiModelProperty(value = "是否是群主,0-否,1-是")
    private Integer groupOwner;

    @ApiModelProperty(value = "机器人类型10-平台号 20-托管号")
    private Integer type;

    @ApiModelProperty(value = "协议类型：2-iPhone 4-Android 16-云机 17-工作机")
    private Integer protocolType;

    @ApiModelProperty(value = "app版本")
    private String tgAppVersion;

    @ApiModelProperty(value = "封号状态 10:未封号 20:疑似封号 30:封号离线")
    private Integer sealStatus;

    @ApiModelProperty(value = "20 在线 10 离线")
    private Integer heartbeatStatus;

    @ApiModelProperty(value = "代理类型")
    private String proxyType;

    @ApiModelProperty(value = "vpn登陆ip")
    private String vpnIp;

    @ApiModelProperty(value = "vpn登陆B段ip")
    private String vpnIpB;

    @ApiModelProperty(value = "vpn登陆C段ip")
    private String vpnIpC;

    @ApiModelProperty(value = "禁用启用状态,0-禁用,1-启用")
    private Integer enableType;

    @ApiModelProperty(value = "水军占用状态,0-未占用,1-已占用")
    private Integer occupyType;

    @ApiModelProperty(value = "今日带群数")
    private Integer groupToDayNum;

    @ApiModelProperty(value = "累计带群数")
    private Integer groupTotalNum;

    @ApiModelProperty(value = "双向状态,0-未被限制,1-已被限制")
    private Integer bidirectionalType;

    @ApiModelProperty(value = "双向状态-解除限制时间")
    private String bidirectionalUnfreezeTime;

    @ApiModelProperty(value = "添加时间")
    private String addTime;

    @ApiModelProperty(value = "封号时间")
    private String sealTime;

    @ApiModelProperty(value = "双向时间")
    private String bidirectionalTime;

    @ApiModelProperty("回收状态,0-未回收,1-已回收")
    private Integer recycleStatus;

    @ApiModelProperty("是否清除,0-未清除,1-已清除")
    private Integer deleteStatus;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "修改时间")
    private String updateTime;

}
