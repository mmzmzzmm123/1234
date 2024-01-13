package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 剧本消息混淆表
 * @TableName t_play_message_confound
 */
@TableName(value ="t_play_message_confound")
@Data
public class PlayMessageConfound implements Serializable {
    /**
     * 主键Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 剧本消息ID
     */
    private Integer playMessageId;

    /**
     * 剧本ID
     */
    private Integer playId;

    /**
     * 群数量
     */
    private Integer groupNum;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 混淆内容
     */
    private String confoundContent;

    /**
     * 消息类型 2001 文字 2002 图片 2003 语音 2004 视频 2005 链接
     */
    private Integer momentTypeId;

    /**
     * 混淆状态 0-混淆中 1-混淆完成 2-混淆失败
     */
    @TableField(value = "`state`")
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}