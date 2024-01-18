package com.ruoyi.common.core.domain.entity.play;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value ="t_play_repeat_log")
public class PlayRepeatLog {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String playId;
}
