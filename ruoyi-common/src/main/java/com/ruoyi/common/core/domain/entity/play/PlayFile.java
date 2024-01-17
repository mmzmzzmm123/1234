package com.ruoyi.common.core.domain.entity.play;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_play_file")
public class PlayFile {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String fileId;

    private Integer contentNo;

    private String content;
}
