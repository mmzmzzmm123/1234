package com.ruoyi.common.core.domain.entity.play;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_play_robot_pack")
public class PlayRobotPack {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 炒群任务id
     */
    private String playId;

    /**
     * 发言顺序
     */
    private Integer messageSort;

    /**
     * 头像
     */
    private String pic;

    /**
     * 名字
     */
    private String name;

    /**
     * 姓氏
     */
    private String surname;

    /**
     * 是否管理员：0-否 1-是
     */
    private Integer isAdmin;
}
