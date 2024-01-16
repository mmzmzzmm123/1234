package com.ruoyi.system.domain.dto.play;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@TableName("t_play_modifier_group_log")
@Data
public class PlayModifierGroupLog {

    private String id;

    private String groupId;

    private String robotId;

    private String optSerNo;

    private Integer state;

    private Date createTime;

    private String playId;

    @ApiModelProperty(value = "群编号 - 开平群编号")
    private String groupSerialNo;

    @ApiModelProperty(value = "群邀请链接")
    private String groupInviteLink;
}
