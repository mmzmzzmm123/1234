package com.ruoyi.common.core.domain.entity.play;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value ="t_play_robot_message")
public class PlayRobotMessage {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 剧本id
     */
    private String playId;

    /**
     * 消息主体json
     */
    private String messageContent;

    /**
     * 创建时间
     */
    private Date createTime;
}
