package com.ruoyi.common.core.domain.entity.play;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.dto.play.ContentJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TableName(value ="t_play_message")
@Data
public class PlayMessage implements Serializable {
    /**
     * 自增主键
     */
    @ApiModelProperty(hidden = true)
    @TableId(type = IdType.AUTO)
    private Integer playMessageId;

    /**
     * 剧本ID
     */
    @ApiModelProperty(value = "剧本ID",hidden = true)
    private String playId;

    /**
     * 发言人昵称
     */
    @ApiModelProperty("发言人昵称")
    private String robotNickname;

    /**
     * 发言顺序
     */
    @ApiModelProperty("发言顺序,值越大越在后面发")
    private Integer messageSort;

    /** 消息主体json , 数据库字段，不暴露前端 */
    @ApiModelProperty(hidden = true)
    private String messageContent;

    @ApiModelProperty("消息主体json对象")
    @TableField(exist = false)
    private List<ContentJson> messageContentList;

    @ApiModelProperty("与上条消息间隔时间 单位秒")
    private Integer intervalTime;

    /**
     * 0正常 1删除
     */
    @ApiModelProperty(hidden = true)
    private Integer isDelete;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
    private Date modifyTime;

    /**
     * @发言人昵称 没有传空
     */
    @ApiModelProperty("@发言人昵称 没有传空")
    private String callRobotNickname;

    /**
     * 单条消息推送失败后状态：1.继续推送  2.终止推送
     */
    private Integer playErrorType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}