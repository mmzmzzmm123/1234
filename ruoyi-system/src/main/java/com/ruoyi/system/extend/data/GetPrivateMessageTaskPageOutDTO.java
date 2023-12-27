package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetPrivateMessageTaskPageOutDTO {
    @ApiModelProperty("任务Id")
    private String taskId;
    @ApiModelProperty("任务名称")
    private String name;
    @ApiModelProperty("执行状态：0待执行、1执行中、2执行完成,-1已取消、-2已暂停")
    private Integer executeStatus;
    @ApiModelProperty("包任务对象详情")
    private List<GetPrivateTaskObjectDetail> taskObjectDetails;
    @ApiModelProperty("完成数")
    private Integer completeCount;
    @ApiModelProperty("总数")
    private Integer totalCount;
    @ApiModelProperty("私信中数量")
    private Integer priavateMessagingCount;
    @ApiModelProperty("私聊成功数")
    private Integer privateMessageSuccessCount;
    @ApiModelProperty("私聊失败数")
    private Integer privateMessageFailCount;
    @ApiModelProperty("取消数量")
    private Integer cancelCount;
    @ApiModelProperty("未开通数")
    private Integer notOpenCount;
    @ApiModelProperty("私聊已读数")
    private Integer privateMessageReadCount;
    @ApiModelProperty("私聊回复数")
    private Integer privateMessageReplyCount;
    @ApiModelProperty("自动回复数")
    private Integer autoReplyCount;
    @ApiModelProperty("执行时间")
    private LocalDateTime executeTime;
    @ApiModelProperty("素材信息集合")
    private List<GetPrivateMaterialGroup> materialGroup;
    @ApiModelProperty("创建人Id")
    private String createrId;
    @ApiModelProperty("创建人")
    private String creater;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("执行方式：1定时执行、2立即执行")
    private Integer executeMode;
    @ApiModelProperty("是否开启小尾巴")
    private Integer isOpenTail;
    @ApiModelProperty("号量")
    private Integer tgCount;
    @ApiModelProperty("人群包id")
    private Long contactPackageTaskId;
    @ApiModelProperty("展示信息")
    private String showMessage;
    @ApiModelProperty("是否触发取号上限 1：风控间隔中 2：触发上限")
    private Integer isTriggerRobotType;
    @ApiModelProperty("结算状态（0未结算、1已结算）")
    private Integer settlementStatus;
    @ApiModelProperty("Desc:规则速度等级 Default: Nullable:True")
    private Integer nLevelId;
    @ApiModelProperty("是否开启自动回复")
    private Integer isOpenAutoReply;
    @ApiModelProperty("自动回复话术分组Id")
    private List<String> autoReplyMaterialGroupIds;
    @ApiModelProperty("自动回复话术分组Id")
    private List<Integer> autoReplyMaterialTypes;
    @ApiModelProperty("自动回复延迟秒数")
    private Integer autoReplyDelaySeconds;
    @ApiModelProperty("素材类型")
    private List<Integer> materialTypes;

    @ApiModelProperty("是否开启混淆")
    private Boolean openDiscrete;

    @ApiModelProperty("停止时间")
    private LocalDateTime updateTime;

}
