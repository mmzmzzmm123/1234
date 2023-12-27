package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Jing.Zhang
 */
@Data
@ApiModel("任务明细查询条件")
public class TaskDetailQueryData extends UtTouchQueryPage {

    @ApiModelProperty("任务Id")
    private String taskId;

    @ApiModelProperty("目标（手机号、用户号）")
    private String objName;

    @ApiModelProperty("1成功、0待回调、-1失败、10待发送、-20未开通、-30取消 -40暂停")
    private Integer status;

    @ApiModelProperty("私聊时间开始区间")
    private LocalDateTime sendTimeStart;

    @ApiModelProperty("私聊时间结束区间")
    private LocalDateTime sendTimeEnd;

    @ApiModelProperty("Tg手机号")
    private String tgPhone;

    @ApiModelProperty("Tg号")
    private String robotId;

    @ApiModelProperty("是否已读")
    private Boolean isRead;

    @ApiModelProperty("是否回复")
    private Boolean isReply;

    @ApiModelProperty("是否拉黑")
    private Boolean isBlock;

    @ApiModelProperty("是否自动回复")
    private Boolean isAutoReply;

    @ApiModelProperty("用户编号")
    private String userCode;

}
