package com.ruoyi.message.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 站内信消息对象 message_site_records
 *
 * @author lixin
 * @date 2022-12-24
 */

@Data
@TableName("message_site_records")
public class MessageSiteRecords implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 站内信流水id */
    private Long msgSiteId;

    /** 发送者用户ID */
    @Excel(name = "发送者用户ID")
    private Long sendUserId;

    /** 发送者用户名称 */
    @Excel(name = "发送者用户名称")
    private String sendUserName;

    /** 接收者用户ID */
    @Excel(name = "接收者用户ID")
    private Long toUserId;

    /** 接受者用户名称 */
    @Excel(name = "接受者用户名称")
    private String toUserName;

    /** 站内信标题 */
    @Excel(name = "站内信标题")
    private String msgSiteTitle;

    /** 站内信内容 */
    @Excel(name = "站内信内容")
    private String msgSiteContent;

    /** 是否已读 */
    @Excel(name = "是否已读")
    private String hasRead;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public void setMsgSiteId(Long msgSiteId)
    {
        this.msgSiteId = msgSiteId;
    }

    public Long getMsgSiteId()
    {
        return msgSiteId;
    }
    public void setSendUserId(Long sendUserId)
    {
        this.sendUserId = sendUserId;
    }

    public Long getSendUserId()
    {
        return sendUserId;
    }
    public void setSendUserName(String sendUserName)
    {
        this.sendUserName = sendUserName;
    }

    public String getSendUserName()
    {
        return sendUserName;
    }
    public void setToUserId(Long toUserId)
    {
        this.toUserId = toUserId;
    }

    public Long getToUserId()
    {
        return toUserId;
    }
    public void setToUserName(String toUserName)
    {
        this.toUserName = toUserName;
    }

    public String getToUserName()
    {
        return toUserName;
    }
    public void setMsgSiteTitle(String msgSiteTitle)
    {
        this.msgSiteTitle = msgSiteTitle;
    }

    public String getMsgSiteTitle()
    {
        return msgSiteTitle;
    }
    public void setMsgSiteContent(String msgSiteContent)
    {
        this.msgSiteContent = msgSiteContent;
    }

    public String getMsgSiteContent()
    {
        return msgSiteContent;
    }

    public void setHasRead(String hasRead)
    {
        this.hasRead = hasRead;
    }

    public String getHasRead()
    {
        return hasRead;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("msgSiteId", getMsgSiteId())
            .append("sendUserId", getSendUserId())
            .append("sendUserName", getSendUserName())
            .append("toUserId", getToUserId())
            .append("toUserName", getToUserName())
            .append("msgSiteTitle", getMsgSiteTitle())
            .append("msgSiteContent", getMsgSiteContent())
            .append("hasRead",getHasRead())
            .append("createTime", getCreateTime())
            .toString();
    }
}
