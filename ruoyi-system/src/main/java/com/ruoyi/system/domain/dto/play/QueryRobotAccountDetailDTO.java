package com.ruoyi.system.domain.dto.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/17/017 15:42
 * @Description :
 */
@ApiModel("查询任务账号明细DTO")
@Data
public class QueryRobotAccountDetailDTO {

    @ApiModelProperty("剧本ID")
    @NotBlank(message = "剧本ID不能为空")
    private String playId;

    @ApiModelProperty("国家名称")
    private String countryName;

    @ApiModelProperty("账号编号")
    private String robotSerialNo;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("群编号")
    private String groupSerialNo;

    @ApiModelProperty("在线状态，20 在线 10 离线")
    private Integer heartbeatStatus;

    @ApiModelProperty("封号状态，10:未封号 20:疑似封号 30:封号离线")
    private Integer sealStatus;

    @ApiModelProperty("双向状态，0-未被限制,1-已被限制")
    private Integer bidirectionalType;

}
