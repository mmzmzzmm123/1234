package com.ruoyi.system.domain.dto.robot;

import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "SelectRobotListDTO")
public class SelectRobotListDTO extends PageBaseDTO {
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束始时间")
    private String endTime;
    @ApiModelProperty(value = "1-创建时间,2-封号时间,3-双向时间")
    private Integer timeType;
    @ApiModelProperty(value = "机器人类型10-平台号 20-托管号")
    private Integer type;
    @ApiModelProperty(value = "协议类型：2-iPhone 4-Android 16-云机 17-工作机")
    private Integer protocolType;
    @ApiModelProperty(value = "app版本")
    private String tgAppVersion;
    @ApiModelProperty(value = "20 在线 10 离线")
    private Integer heartbeatStatus;
    @ApiModelProperty(value = "封号状态 10:未封号 20:疑似封号 30:封号离线")
    private Integer sealStatus;
    @ApiModelProperty(value = "双向状态,0-未被限制,1-已被限制")
    private Integer bidirectionalType;
    @ApiModelProperty(value = "国籍")
    private String country;
    @ApiModelProperty(value = "水军占用状态,0-未占用,1-已占用")
    private Integer occupyType;
    @ApiModelProperty(value = "禁用启用状态,0-禁用,1-启用")
    private Integer enableType;
    @ApiModelProperty(value = "是否是群主,0-否,1-是")
    private Integer groupOwner;
    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "编号")
    private List<String> robotSerialNos;
    @ApiModelProperty(value = "有无用户名,0-无,1-有")
    private Integer isHasUserName;

    @ApiModelProperty(value = "代理类型 长效;短效;未知;")
    private String proxyType;

    @ApiModelProperty(value = "筛选群类型：1累计带群 2今日入群 3累计入群")
    private Integer groupType;
    @ApiModelProperty(value = "筛选群类型符号：1大于 2小于")
    private Integer groupOperator;
    @ApiModelProperty(value = "筛选群数值")
    private Integer groupVal;
}
