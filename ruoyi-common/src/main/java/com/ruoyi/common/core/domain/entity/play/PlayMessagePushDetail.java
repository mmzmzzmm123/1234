package com.ruoyi.common.core.domain.entity.play;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value ="t_play_message_push_detail")
@Data
public class PlayMessagePushDetail implements Serializable {
    /**
     * 主键Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 剧本消息推送Id
     */
    private Integer playMsgPushId;

    /**
     * 发言人昵称
     */
    private String spokesmanNickname;

    /**
     * 机器人Id
     */
    private String robotId;

    /**
     * 机器人昵称
     */
    private String robotNickname;

    /**
     * 机器人头像
     */
    private String robotImgUrl;

    /**
     * 机器人账号
     */
    private String robotAcct;

    /**
     * 是否需要@所有人  0-不需要  1-需要
     */
    private Integer callAll;

    /**
     * 与上条消息间隔时间 单位秒
     */
    private Integer intervalTime;

    /**
     * 单条消息推送失败后状态：1.继续推送  2.终止推送
     */
    private Integer playErrorType;

    /**
     * 消息主体
     */
    private String messageContent;

    /**
     * 发言顺序
     */
    private Integer messageSort;

    /**
     * 消息推送状态:0:待发送 1:发送成功 2:发送失败 3-发送中，等待回调
     */
    private Integer sendState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 0正常 1删除
     */
    private Integer isDelete;


    /**
     * 推送失败原因
     */
    private String pushFailReason;

    /**
     * 操作编码
     */
    private String optSerialNo;

    /**
     * 备用号(,拼接)
     */
    private String spareRobot;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}