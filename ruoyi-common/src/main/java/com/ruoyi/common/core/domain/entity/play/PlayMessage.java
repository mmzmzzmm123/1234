package com.ruoyi.common.core.domain.entity.play;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
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
    private Integer playId;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Data
    @ApiModel("Content消息主体")
    public static class ContentJson implements Serializable {
        private static final long serialVersionUID = -6434841971083561503L;
        @ApiModelProperty(value = "uuid",hidden = true)
        private String uuid;
        @ApiModelProperty("消息内容")
        private String sMateContent;
        @ApiModelProperty("消息类型: 2001 文字 2002 图片 2003 语音(只支持amr格式) 2004 视频 2005 链接 2006 好友名片 2010 文件 2013 小程序 2016 音乐 201姓名")
        private Integer momentTypeId;
        @ApiModelProperty("图片消息url ")
        private String sMateImgUrl;
        @ApiModelProperty("语音时长")
        private Integer sMateBVLen;
        @ApiModelProperty("语音链接")
        private String sMateAwrUrl;
        private String sMateVUrl ;
        private String sMateId ;
        private String sMateTitle ;
        private String title ;
    }
}