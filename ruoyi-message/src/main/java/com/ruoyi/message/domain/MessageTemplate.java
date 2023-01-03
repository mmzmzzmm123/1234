package com.ruoyi.message.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 消息模板
 *
 * @Author lixin
 * @Date 2022/12/16 17:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("message_template")
public class MessageTemplate extends BaseEntity implements Serializable{

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long msgTempId;

    /** 模板唯一编码 */
    @Excel(name = "模板唯一编码")
    @NotNull
    @Size(min = 1, max = 50, message = "模板唯一编码不可为空")
    private String msgTempPcode;

    /** 模板类型 1.站内信 */
    @Excel(name = "模板类型 1.站内信")
    @NotNull
    @Min(value = 1, message = "模板类型只可选择(1站类信)")
    @Max(value = 1, message = "模板类型只可选择(1站类信)")
    private Integer msgTempType;

    /** 模板标题 */
    @Excel(name = "模板标题")
    @NotNull
    @Size(min = 1, max = 100, message = "模板标题不可为空")
    private String msgTempTitle;

    /** 模板内容 */
    @Excel(name = "模板内容")
    @NotNull
    private String msgTempContent;


    public void setMsgTempId(Long msgTempId)
    {
        this.msgTempId = msgTempId;
    }

    public Long getMsgTempId()
    {
        return msgTempId;
    }
    public void setMsgTempPcode(String msgTempPcode)
    {
        this.msgTempPcode = msgTempPcode;
    }

    public String getMsgTempPcode()
    {
        return msgTempPcode;
    }
    public void setMsgTempType(Integer msgTempType)
    {
        this.msgTempType = msgTempType;
    }

    public Integer getMsgTempType()
    {
        return msgTempType;
    }
    public void setMsgTempTitle(String msgTempTitle)
    {
        this.msgTempTitle = msgTempTitle;
    }

    public String getMsgTempTitle()
    {
        return msgTempTitle;
    }
    public void setMsgTempContent(String msgTempContent)
    {
        this.msgTempContent = msgTempContent;
    }

    public String getMsgTempContent()
    {
        return msgTempContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("msgTempId", getMsgTempId())
                .append("msgTempPcode", getMsgTempPcode())
                .append("msgTempType", getMsgTempType())
                .append("msgTempTitle", getMsgTempTitle())
                .append("msgTempContent", getMsgTempContent())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
