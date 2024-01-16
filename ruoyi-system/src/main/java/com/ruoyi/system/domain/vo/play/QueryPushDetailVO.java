package com.ruoyi.system.domain.vo.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/16/016 9:49
 * @Description :
 */
@Data
@ApiModel("查询推送详情VO")
public class QueryPushDetailVO {
    @ApiModelProperty("群主键ID")
    private String groupId;
    @ApiModelProperty("群编号")
    private String groupSerialNo;
    @ApiModelProperty("群名称")
    private String groupName;
    @ApiModelProperty("发言人")
    private String spokesmanNickname;
    @ApiModelProperty("机器人Id")
    private String robotId;
    @ApiModelProperty("机器人昵称")
    private String robotNickname;
    @ApiModelProperty("机器人头像")
    private String robotImgUrl;
    @ApiModelProperty("机器人账号")
    private String robotAcct;
    @ApiModelProperty("消息主体")
    private String messageContent;
    @ApiModelProperty("发言顺序")
    private String messageSort;
    @ApiModelProperty("消息推送状态:0:待发送 1:发送成功 2:发送失败 3-发送中，等待回调")
    private String sendState;
    @ApiModelProperty("发送时间")
    private String sendTime;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("推送失败原因")
    private String pushFailReason;


}
