package com.ruoyi.system.domain.dto.play;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@TableName("t_play_group_member_log")
@Data
public class PlayGroupMemberLog {

    @TableId
    private String id;

    private String groupId;

    private String robotId;

    private String optSerNo;

    private Integer state;

    private Date createTime;

    private String playId;

    @ApiModelProperty(value = "群编号 - 开平群编号")
    private String groupSerialNo;
}
