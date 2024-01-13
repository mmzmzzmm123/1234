package com.ruoyi.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_play_info")
public class Play {
    @TableId
    private String id;

    /**
     * 名称
     */
    private String name;
}
