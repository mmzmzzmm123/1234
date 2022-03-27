package com.jlt.csa.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 沟通记录对象 csa_communicate_record
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public class CommunicateRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 沟通日志记录id */
    private Long id;

    /** 会员 */
    @Excel(name = "会员")
    private Long memberId;

    /** 菜地id */
    @Excel(name = "菜地id")
    private Long gardenId;

    /** 发布人 */
    @Excel(name = "发布人")
    private Long recorder;

    /** 种植任务id */
    @Excel(name = "种植任务id")
    private Long taskId;

    /** 沟通类型 */
    @Excel(name = "沟通类型")
    private String commType;

    /** 沟通内容 */
    @Excel(name = "沟通内容")
    private String content;

    /** 处理结果 */
    @Excel(name = "处理结果")
    private String result;

    /** 是否处理 */
    @Excel(name = "是否处理")
    private String isCompleted;

    /** 沟通时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "沟通时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date commTime;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date doTime;

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
    public void setRecorder(Long recorder) 
    {
        this.recorder = recorder;
    }

    public Long getRecorder() 
    {
        return recorder;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setCommType(String commType) 
    {
        this.commType = commType;
    }

    public String getCommType() 
    {
        return commType;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setResult(String result) 
    {
        this.result = result;
    }

    public String getResult() 
    {
        return result;
    }
    public void setIsCompleted(String isCompleted) 
    {
        this.isCompleted = isCompleted;
    }

    public String getIsCompleted() 
    {
        return isCompleted;
    }
    public void setCommTime(Date commTime) 
    {
        this.commTime = commTime;
    }

    public Date getCommTime() 
    {
        return commTime;
    }
    public void setDoTime(Date doTime) 
    {
        this.doTime = doTime;
    }

    public Date getDoTime() 
    {
        return doTime;
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
            .append("recorder", getRecorder())
            .append("taskId", getTaskId())
            .append("commType", getCommType())
            .append("content", getContent())
            .append("result", getResult())
            .append("isCompleted", getIsCompleted())
            .append("commTime", getCommTime())
            .append("doTime", getDoTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
