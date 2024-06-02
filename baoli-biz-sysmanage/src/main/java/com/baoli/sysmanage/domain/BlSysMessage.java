package com.baoli.sysmanage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 消息通知对象 bl_sys_message
 * 
 * @author niujs
 * @date 2024-04-10
 */
public class BlSysMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 消息标题 */
    @Excel(name = "消息标题")
    private String messageTitle;

    /** 消息类型 */
    @Excel(name = "消息类型")
    private String messageType;

    /** 接收者 */
    @Excel(name = "接收者")
    private Long receiverId;

    /** 发送者 */
    @Excel(name = "发送者")
    private Long senderId;

    /** 发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendTime;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String messageContent;

    /** 已读 */
    @Excel(name = "已读")
    private String isRead;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMessageTitle(String messageTitle) 
    {
        this.messageTitle = messageTitle;
    }

    public String getMessageTitle() 
    {
        return messageTitle;
    }
    public void setMessageType(String messageType) 
    {
        this.messageType = messageType;
    }

    public String getMessageType() 
    {
        return messageType;
    }
    public void setReceiverId(Long receiverId) 
    {
        this.receiverId = receiverId;
    }

    public Long getReceiverId() 
    {
        return receiverId;
    }
    public void setSenderId(Long senderId) 
    {
        this.senderId = senderId;
    }

    public Long getSenderId() 
    {
        return senderId;
    }
    public void setSendTime(Date sendTime) 
    {
        this.sendTime = sendTime;
    }

    public Date getSendTime() 
    {
        return sendTime;
    }
    public void setMessageContent(String messageContent) 
    {
        this.messageContent = messageContent;
    }

    public String getMessageContent() 
    {
        return messageContent;
    }
    public void setIsRead(String isRead) 
    {
        this.isRead = isRead;
    }

    public String getIsRead() 
    {
        return isRead;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("messageTitle", getMessageTitle())
            .append("messageType", getMessageType())
            .append("receiverId", getReceiverId())
            .append("senderId", getSenderId())
            .append("sendTime", getSendTime())
            .append("messageContent", getMessageContent())
            .append("isRead", getIsRead())
            .toString();
    }
}
