package com.ruoyi.common.core.domain.entity.play;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 剧本备用机器人号池表
 */
@Data
@Builder
@TableName("t_play_back_robot")
public class PlayBackRobot implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 炒群任务id
     */
    private String playId;
    /**
     * 群ID
     */
    private String groupId;
    /**
     * 备用机器人Id
     */
    private String robotId;
    /**
     * 对应发言人昵称
     */
    private String spokesmanNickname;
    /**
     * 暂停的发言顺序
     */
    private Integer messageSort;
    /**
     * 是否包装完成 -1未包装,0包装中,1已包装,2包装失败
     */
    private Integer isFinish;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
