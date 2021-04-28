package com.stdiet.custom.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageNoticeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 消息属性，0 公共消息 1 私有消息 */
    private Integer messageProperty;

    /** 消息对应客户ID (公共消息时该字段为0) */
    private Long messageCustomer;

    /** 消息类型 */
    private Integer messageType;

    //消息类型名称
    private String messageTypeName;

    /** 消息标题 */
    private String messageTitle;

    /** 消息内容 */
    private String messageContent;

    /** 是否已读 0未读 1已读 */
    private Integer readType;

    /** 当前消息对应关键参数，多个参数可保存json字符串 */
    private String messageKey;

    /** 创建时间 **/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
}
