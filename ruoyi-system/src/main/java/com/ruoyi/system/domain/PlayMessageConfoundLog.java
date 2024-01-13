package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 剧本消息混淆日志表
 * @TableName t_play_message_confound_log
 */
@TableName(value ="t_play_message_confound_log")
@Data
public class PlayMessageConfoundLog implements Serializable {
    /**
     * 主键Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 消息混淆ID
     */
    private Integer messageConfoundId;

    /**
     * 状态 0-请求中 1-请求成功 2-请求失败 3-重试中
     */
    @TableField(value = "`state`")
    private Integer state;

    /**
     * 执行次数
     */
    private Integer executeNum;

    /**
     * 操作编码
     */
    private String optSerialNo;

    /**
     * 返回内容
     */
    private String resultContent;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 失败原因
     */
    private String failMessage;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}