package com.ruoyi.system.domain.vo.play;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : kratos
 * @create 2024/1/17/017 15:44
 * @Description :
 */
@ApiModel("查询任务账号明细-账号维度VO")
@Data
public class QueryRobotAccountDetailVO extends PageBaseDTO {
    @Excel(name = "机器人编号")
    @ApiModelProperty("机器人编号")
    private String robotSerialNo;

    @Excel(name = "手机号")
    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("国家代码")
    private String countryCode;

    @Excel(name = "国家")
    @ApiModelProperty("国家")
    private String countryName;

    @Excel(name = "在线状态", readConverterExp = "20=在线,10=离线")
    @ApiModelProperty("在线状态，20 在线 10 离线")
    private Integer heartbeatStatus;

    @Excel(name = "封号状态", readConverterExp = "10=未封号,20=疑似封号,30=封号离线")
    @ApiModelProperty("封号状态，10:未封号 20:疑似封号 30:封号离线")
    private Integer sealStatus;

    @Excel(name = "双向状态", readConverterExp = "0=未被限制,1=已被限制")
    @ApiModelProperty("双向状态，0-未被限制,1-已被限制")
    private Integer bidirectionalType;

    @ApiModelProperty("群ID")
    private String groupId;

    @Excel(name = "群编号")
    @ApiModelProperty("群编号")
    private String groupSerialNo;

}
