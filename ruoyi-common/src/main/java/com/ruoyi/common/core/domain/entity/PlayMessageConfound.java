package com.ruoyi.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_play_message_confound")
public class PlayMessageConfound {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 关联剧本消息ID
     */
    private Long playMessageId;

    /**
     * 剧本ID
     */
    private String playId;

    /**
     * 群数量
     */
    private Integer groupNum;

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
     * 混淆状态 0-混淆中 1-混淆完成
     */
    private Integer state;
}
