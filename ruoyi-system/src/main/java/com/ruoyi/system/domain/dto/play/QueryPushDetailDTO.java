package com.ruoyi.system.domain.dto.play;

import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/16/016 9:48
 * @Description :
 */
@Data
@ApiModel("查询推送详情DTO")
public class QueryPushDetailDTO extends PageBaseDTO {

    @ApiModelProperty("剧本ID")
    @NotBlank(message = "剧本ID不能为空")
    private String playId;

    @ApiModelProperty("群列表主键ID")
    private String id;

    @ApiModelProperty("时间类型 1-发送时间 2-生成时间")
    private Integer timeType = 1;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("推送状态 0:待发送 1:发送成功 2:发送失败 3-发送中，等待回调")
    private Integer sendState;

    @ApiModelProperty("机器人Id")
    private String robotId;

    @ApiModelProperty("机器人昵称")
    private String robotNickname;

    @ApiModelProperty("机器人账号")
    private String robotAcct;

    @ApiModelProperty("群ID")
    private String groupId;

    @ApiModelProperty("群编号")
    private String groupSerialNo;

    @ApiModelProperty("群名称")
    private String groupName;

    @ApiModelProperty("排序 0-倒序（默认） 1-顺序")
    private Integer sort;

    @ApiModelProperty("排序字段 1-创建时间（默认） 2-发送时间")
    private Integer sortField;


}
