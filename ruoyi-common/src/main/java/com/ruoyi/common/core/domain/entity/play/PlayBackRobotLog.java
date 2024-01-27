package com.ruoyi.common.core.domain.entity.play;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 剧本备用机器人号池表包装日志表
 */
@Data
@TableName("t_play_back_robot_log")
public class PlayBackRobotLog {
    @TableId
    private String id;

    /**
     * 操作码
     */
    private String opt;

    /**
     * 炒群任务id
     */
    private String playId;
    /**
     * 群ID
     */
    private String chatroomId;
    /**
     * 备用机器人Id
     */
    private String robotId;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * -1等待前置调度完成 0-请求中 1-成功 2-失败
     */
    private Integer status;
    /**
     * 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员
     */
    private Integer op;
    /**
     * 等待某个操作码执行完成才能开始调佣
     */
    private String waitOpt;
    /**
     * 重试次数
     */
    private Integer retryCount;
    /**
     * 重试最大次数也失败了，最终就是失败 0-未达到最大重试次数 1-已经达到最大次数
     */
    private Integer retryMaxFlag;
    /**
     * 回调附加的值
     */
    private String attchContent;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
