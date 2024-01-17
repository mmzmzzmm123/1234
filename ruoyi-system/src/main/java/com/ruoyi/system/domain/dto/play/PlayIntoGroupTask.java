package com.ruoyi.system.domain.dto.play;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_play_into_group_task")
public class PlayIntoGroupTask implements Serializable {

    @TableId
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("群链接")
    private String groupUrl;

    @ApiModelProperty("群名称")
    private String groupName;

    @ApiModelProperty("机器人ID")
    private String personId;

    @ApiModelProperty("重试ID")
    private String retryId;

    @ApiModelProperty("剧本ID")
    private String playId;

    @ApiModelProperty("是否可被设置管理员 0否 1可以")
    private Integer isAdmin;

    @ApiModelProperty("任务状态: 1:待处理 2.处理中 3:已完成 4 已失败")
    private Integer taskState;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private Date modifyTime;

    @ApiModelProperty("操作编码")
    private String code;

    @ApiModelProperty("商家ID")
    private String merchantId;

    @ApiModelProperty("失败原因")
    private String failCause;

    @ApiModelProperty("群ID")
    private String groupId;

    @ApiModelProperty("0 还能重试 1无法重试")
    private Integer isError;
}