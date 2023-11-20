package com.ruoyi.office.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * mqtt消息发送接收对象 t_mqtt_msg
 * 
 * @author ruoyi
 * @date 2023-11-20
 */
public class TMqttMsg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private Long id;

    /** 消息类型 */
    @Excel(name = "消息类型")
    private String msgType;

    /** 订阅主题 */
    @Excel(name = "订阅主题")
    private String topic;

    /** QOS */
    @Excel(name = "QOS")
    private String qos;

    /** 消息 */
    @Excel(name = "消息")
    private String message;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String clientId;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMsgType(String msgType) 
    {
        this.msgType = msgType;
    }

    public String getMsgType() 
    {
        return msgType;
    }
    public void setTopic(String topic) 
    {
        this.topic = topic;
    }

    public String getTopic() 
    {
        return topic;
    }
    public void setMessage(String message) 
    {
        this.message = message;
    }

    public String getMessage() 
    {
        return message;
    }
    public void setClientId(String clientId) 
    {
        this.clientId = clientId;
    }

    public void setQos(String qos) {
        this.qos = qos;
    }
    public String getQos() {
        return qos;
    }

    public String getClientId() 
    {
        return clientId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("msgType", getMsgType())
            .append("topic", getTopic())
            .append("message", getMessage())
            .append("clientId", getClientId())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }

}
