package com.jlt.csa.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 菜地日志对象 csa_garden_log
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public class GardenLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 菜地日志id */
    private Long id;

    /** 会员 */
    @Excel(name = "会员")
    private Long memberId;

    /** 菜地 */
    @Excel(name = "菜地")
    private Long gardenId;

    /** 种植任务 */
    @Excel(name = "种植任务")
    private Long taskId;

    /** 日志类型 */
    @Excel(name = "日志类型")
    private String logType;

    /** 日志内容 */
    @Excel(name = "日志内容")
    private String content;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    /** 发布人 */
    @Excel(name = "发布人")
    private Long publisher;

    /** 发布状态 */
    @Excel(name = "发布状态")
    private String publishStatus;

    /** 删除标志 */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }
    public void setGardenId(Long gardenId) 
    {
        this.gardenId = gardenId;
    }

    public Long getGardenId() 
    {
        return gardenId;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setLogType(String logType) 
    {
        this.logType = logType;
    }

    public String getLogType() 
    {
        return logType;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setPublishTime(Date publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() 
    {
        return publishTime;
    }
    public void setPublisher(Long publisher) 
    {
        this.publisher = publisher;
    }

    public Long getPublisher() 
    {
        return publisher;
    }
    public void setPublishStatus(String publishStatus) 
    {
        this.publishStatus = publishStatus;
    }

    public String getPublishStatus() 
    {
        return publishStatus;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("memberId", getMemberId())
            .append("gardenId", getGardenId())
            .append("taskId", getTaskId())
            .append("logType", getLogType())
            .append("content", getContent())
            .append("publishTime", getPublishTime())
            .append("publisher", getPublisher())
            .append("publishStatus", getPublishStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
